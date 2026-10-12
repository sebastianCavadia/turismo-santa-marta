package co.edu.unimagdalena.turismo.catalogo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {
  Optional<Disponibilidad> findByServicioIdAndFecha(Long servicioId, LocalDate fecha);
}
