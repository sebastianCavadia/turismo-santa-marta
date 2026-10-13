package co.edu.unimagdalena.turismo.identity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

// RF001-015 v3: cuenta por tipo + estados + bloqueo + validación PRE + último acceso.
@Entity
@Table(name = "usuario", schema = "identitys")
public class Usuario {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;
  public String nombre;
  @Column(unique = true)
  public String correo;
  @Column(name = "password_hash")
  public String passwordHash;
  public String rol = "TUR"; // TUR, PRE, ADM, GES
  @Column(name = "tipo_cuenta")
  public String tipoCuenta = "TURISTA"; // TURISTA, HOTEL, RESTAURANTE, AGENCIA
  public String estado = "ACTIVO"; // ACTIVO, INACTIVO, BLOQUEADO (RF006)
  public Boolean activo = true;
  public Boolean validado = true; // PRE pendiente hasta validación ADM (RF095)
  // Turista (RF002)
  @Column(name = "tipo_doc")
  public String tipoDoc;
  @Column(name = "num_doc")
  public String numDoc;
  public String nacionalidad;
  @Column(name = "pais_residencia")
  public String paisResidencia;
  @Column(name = "fecha_nac")
  public LocalDate fechaNac;
  public Boolean discapacidad = false;
  // Prestador (RF003)
  public String nit;
  public String rnt;
  public String ubicacion;
  @Column(name = "idiomas_guia")
  public String idiomasGuia;
  public String telefono;
  public String idioma = "es"; // RF011
  public Integer intentos = 0; // RF005
  @Column(name = "bloqueado_hasta")
  public LocalDateTime bloqueadoHasta;
  @Column(name = "ultimo_acceso")
  public LocalDateTime ultimoAcceso; // RF013
  @Column(name = "area_responsable")
  public String areaResponsable; // RF099
}
