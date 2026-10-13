package co.edu.unimagdalena.turismo.identity;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByCorreo(String correo);
  Optional<Usuario> findByNumDoc(String numDoc);
}
