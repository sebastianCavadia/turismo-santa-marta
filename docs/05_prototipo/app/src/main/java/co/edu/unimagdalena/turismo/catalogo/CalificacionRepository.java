package co.edu.unimagdalena.turismo.catalogo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
  List<Calificacion> findByServicioIdOrderByIdDesc(Long servicioId);
  List<Calificacion> findByServicioIdAndEstrellasOrderByIdDesc(Long servicioId, Integer estrellas);
  boolean existsByReservaId(Long reservaId);

  @Query("SELECT COALESCE(AVG(c.estrellas), 0), COUNT(c) FROM Calificacion c WHERE c.servicioId = :servicioId")
  List<Object[]> promedioYTotal(Long servicioId);
}
