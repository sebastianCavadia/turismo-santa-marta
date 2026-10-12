package co.edu.unimagdalena.turismo.reservapago;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
  List<Reserva> findByTuristaIdOrderByIdDesc(Long turistaId);
  List<Reserva> findByEstado(String estado);
}
