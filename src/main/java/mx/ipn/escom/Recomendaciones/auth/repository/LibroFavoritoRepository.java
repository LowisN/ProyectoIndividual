package mx.ipn.escom.Recomendaciones.auth.repository;

import mx.ipn.escom.Recomendaciones.auth.entity.LibroFavorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroFavoritoRepository extends JpaRepository<LibroFavorito, Long> {
    List<LibroFavorito> findByUsuarioId(Long usuarioId);
    LibroFavorito findByLibroIdAndUsuarioId(String libroId, Long usuarioId);
}
