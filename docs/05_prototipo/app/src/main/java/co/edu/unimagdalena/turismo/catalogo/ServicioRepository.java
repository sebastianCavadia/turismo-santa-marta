package co.edu.unimagdalena.turismo.catalogo;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
  @Query(value = """
    SELECT * FROM catalogo.servicio s WHERE s.habilitado = TRUE AND s.validado = TRUE
    AND (CAST(:q AS TEXT) IS NULL OR s.nombre ILIKE CONCAT('%',CAST(:q AS TEXT),'%'))
    AND (CAST(:categoria AS TEXT) IS NULL OR s.categoria = CAST(:categoria AS TEXT))
    AND (CAST(:zona AS TEXT) IS NULL OR s.zona = CAST(:zona AS TEXT))
    AND (CAST(:tipo AS TEXT) IS NULL OR s.tipo = CAST(:tipo AS TEXT))
    AND (:maxPrecio IS NULL OR s.precio_cop <= :maxPrecio)
    """, countQuery = """
    SELECT count(*) FROM catalogo.servicio s WHERE s.habilitado = TRUE AND s.validado = TRUE
    AND (CAST(:q AS TEXT) IS NULL OR s.nombre ILIKE CONCAT('%',CAST(:q AS TEXT),'%'))
    AND (CAST(:categoria AS TEXT) IS NULL OR s.categoria = CAST(:categoria AS TEXT))
    AND (CAST(:zona AS TEXT) IS NULL OR s.zona = CAST(:zona AS TEXT))
    AND (CAST(:tipo AS TEXT) IS NULL OR s.tipo = CAST(:tipo AS TEXT))
    AND (:maxPrecio IS NULL OR s.precio_cop <= :maxPrecio)
    """, nativeQuery = true)
  Page<Servicio> buscar(@Param("q") String q, @Param("categoria") String categoria,
    @Param("zona") String zona, @Param("tipo") String tipo,
    @Param("maxPrecio") Long maxPrecio, Pageable pageable);
}
