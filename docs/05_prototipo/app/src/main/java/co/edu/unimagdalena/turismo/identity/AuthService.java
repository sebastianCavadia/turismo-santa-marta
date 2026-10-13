package co.edu.unimagdalena.turismo.identity;

import co.edu.unimagdalena.turismo.reservapago.Auditoria;
import co.edu.unimagdalena.turismo.reservapago.AuditoriaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

// RF001-015: registro por tipo, login con bloqueo, validación ADM. Token mock opaco (C-2: JWT).
@Service
public class AuthService {
  private final UsuarioRepository usuarios;
  private final AuditoriaRepository auditoria;
  private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

  public AuthService(UsuarioRepository u, AuditoriaRepository a) { this.usuarios = u; this.auditoria = a; }

  public record RegistroReq(String tipoCuenta, String nombre, String correo, String password,
    String tipoDoc, String numDoc, String nacionalidad, String paisResidencia, String fechaNac,
    Boolean discapacidad, String nit, String rnt, String ubicacion, String idiomasGuia, String telefono) {}

  @Transactional
  public Map<String, Object> registro(RegistroReq req) {
    if (req.correo() == null || req.correo().isBlank() || req.password() == null || req.password().isBlank())
      throw new IllegalArgumentException("Correo y contraseña obligatorios");
    if (usuarios.findByCorreo(req.correo().trim().toLowerCase()).isPresent())
      throw new IllegalStateException("Correo ya registrado");
    String tipo = (req.tipoCuenta() == null ? "TURISTA" : req.tipoCuenta().trim().toUpperCase());
    Usuario u = new Usuario();
    u.tipoCuenta = tipo;
    u.nombre = req.nombre();
    u.correo = req.correo().trim().toLowerCase();
    u.passwordHash = bcrypt.encode(req.password());
    u.telefono = req.telefono();
    if (tipo.equals("TURISTA")) {
      u.rol = "TUR";
      if (req.numDoc() != null && !req.numDoc().isBlank()
          && usuarios.findByNumDoc(req.numDoc().trim()).isPresent())
        throw new IllegalStateException("Documento ya registrado (RF002)");
      u.tipoDoc = req.tipoDoc(); u.numDoc = req.numDoc() == null ? null : req.numDoc().trim();
      u.nacionalidad = req.nacionalidad(); u.paisResidencia = req.paisResidencia();
      if (req.fechaNac() != null && !req.fechaNac().isBlank()) u.fechaNac = java.time.LocalDate.parse(req.fechaNac());
      u.discapacidad = Boolean.TRUE.equals(req.discapacidad());
      u.validado = true;
    } else {
      u.rol = "PRE";
      u.nit = req.nit(); u.rnt = req.rnt(); u.ubicacion = req.ubicacion();
      u.idiomasGuia = req.idiomasGuia();
      u.validado = false; // RF095: pendiente hasta validación ADM
    }
    u.estado = "ACTIVO";
    u = usuarios.save(u);
    bitacora(u.id, null, "ACTIVO:" + u.rol);
    return Map.of("usuario", view(u), "token", token(u),
      "nota", u.validado ? "Cuenta activa" : "Pendiente de validación ADM (RF095), tus ofertas no serán públicas (RF085)");
  }

  // Sin @Transactional: los intentos fallidos deben commitear aunque el login falle (RF005).
  public Map<String, Object> login(String correo, String password) {
    Usuario u = usuarios.findByCorreo(correo == null ? "" : correo.trim().toLowerCase())
      .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));
    if (u.bloqueadoHasta != null && u.bloqueadoHasta.isAfter(LocalDateTime.now()))
      throw new IllegalStateException("Cuenta bloqueada temporalmente, intenta en 5 minutos (RF005)");
    if (!"ACTIVO".equals(u.estado) || Boolean.FALSE.equals(u.activo))
      throw new IllegalStateException("Cuenta " + u.estado + ", contacta a la administración (RF006)");
    if (u.passwordHash == null || !bcrypt.matches(password == null ? "" : password, u.passwordHash)) {
      u.intentos = (u.intentos == null ? 0 : u.intentos) + 1;
      if (u.intentos >= 5) {
        u.bloqueadoHasta = LocalDateTime.now().plusMinutes(5);
        usuarios.save(u);
        throw new IllegalStateException("5 intentos fallidos: bloqueada 5 minutos (RF005)");
      }
      usuarios.save(u);
      throw new IllegalArgumentException("Credenciales inválidas (" + u.intentos + "/5)");
    }
    u.intentos = 0; u.bloqueadoHasta = null;
    u.ultimoAcceso = LocalDateTime.now(); // RF013
    u = usuarios.save(u);
    return Map.of("usuario", view(u), "token", token(u));
  }

  @Transactional
  public Usuario validarPrestador(Long prestadorId) {
    Usuario u = usuarios.findById(prestadorId).orElseThrow(() -> new IllegalArgumentException("Usuario no existe"));
    u.validado = true;
    u = usuarios.save(u);
    bitacora(u.id, "PENDIENTE", "VALIDADO");
    return u;
  }

  public List<Usuario> pendientes() {
    return usuarios.findAll().stream().filter(u -> "PRE".equals(u.rol) && Boolean.FALSE.equals(u.validado)).toList();
  }

  private static String token(Usuario u) {
    return "mock-" + u.id + "-" + UUID.randomUUID().toString().substring(0, 8);
  }

  private static Map<String, Object> view(Usuario u) {
    Map<String, Object> m = new LinkedHashMap<>();
    m.put("id", u.id); m.put("nombre", u.nombre); m.put("correo", u.correo);
    m.put("rol", u.rol); m.put("tipoCuenta", u.tipoCuenta); m.put("estado", u.estado);
    m.put("validado", u.validado); m.put("idioma", u.idioma);
    return m;
  }

  private void bitacora(Long id, String antes, String despues) {
    Auditoria a = new Auditoria();
    a.entidad = "usuario"; a.entidadId = id; a.accion = "admin-cuenta";
    a.estadoAntes = antes; a.estadoDespues = despues;
    auditoria.save(a);
  }
}
