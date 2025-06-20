package mx.ipn.escom.Recomendaciones.auth.controller;

import mx.ipn.escom.Recomendaciones.auth.entity.Usuario;
import mx.ipn.escom.Recomendaciones.auth.repository.UsuarioRepository;
import mx.ipn.escom.Recomendaciones.auth.entity.LibroFavorito;
import mx.ipn.escom.Recomendaciones.auth.repository.LibroFavoritoRepository;
import mx.ipn.escom.Recomendaciones.auth.dto.LibroFavoritoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/libros")
public class LibrosFavoritosController {

    @Autowired
    private LibroFavoritoRepository libroFavoritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/favoritos")
    public ResponseEntity<List<LibroFavoritoDTO>> obtenerFavoritos() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioRepository.findByNombre(auth.getName());

            if (usuario == null) {
                return ResponseEntity.status(401).build();
            }

            List<LibroFavorito> favoritos = libroFavoritoRepository.findByUsuarioId(usuario.getId());
            List<LibroFavoritoDTO> favoritosDTO = favoritos.stream()
                .map(f -> new LibroFavoritoDTO(
                    f.getId(),
                    f.getLibroId(),
                    f.getTitulo(),
                    f.getAutor(),
                    f.getImagenUrl(),
                    f.getFechaAgregado()
                ))
                .collect(Collectors.toList());
            return ResponseEntity.ok(favoritosDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }    @PostMapping("/favoritos")
    public ResponseEntity<?> agregarFavorito(@RequestBody Map<String, String> libro) {
        try {
            // Log de los datos recibidos
            System.out.println("Datos recibidos del frontend:");
            System.out.println("LibroId: " + libro.get("libroId"));
            System.out.println("Título: " + libro.get("titulo"));
            System.out.println("Autor: " + libro.get("autor"));
            System.out.println("ImagenUrl: " + libro.get("imagenUrl"));
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioRepository.findByNombre(auth.getName());

            if (usuario == null) {
                return ResponseEntity.status(401).body(Map.of("message", "Usuario no autenticado"));
            }

            String libroId = libro.get("libroId");
            if (libroId == null || libroId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "ID del libro no válido"));
            }

            // Verificar si el libro ya está en favoritos
            LibroFavorito existente = libroFavoritoRepository.findByLibroIdAndUsuarioId(libroId, usuario.getId());

            if (existente != null) {
                // Si existe, lo eliminamos (toggle)
                libroFavoritoRepository.delete(existente);
                return ResponseEntity.ok().body(Map.of("status", "removed"));
            }            // Si no existe, creamos uno nuevo
            LibroFavorito libroFavorito = new LibroFavorito();
            libroFavorito.setUsuario(usuario);
            
            // Procesar y limpiar el ID del libro
            libroId = libroId.trim();
            if (!libroId.startsWith("/works/")) {
                libroId = "/works/" + libroId;
            }
            libroFavorito.setLibroId(libroId);

            // Validar y limpiar los datos antes de guardar
            String titulo = libro.getOrDefault("titulo", "").trim();
            String autor = libro.getOrDefault("autor", "").trim();
            String imagenUrl = libro.getOrDefault("imagenUrl", "").trim();

            // Asignar valores por defecto si están vacíos
            libroFavorito.setTitulo(titulo.isEmpty() ? "Sin título" : titulo);
            libroFavorito.setAutor(autor.isEmpty() ? "Autor desconocido" : autor);
            
            // Asegurarnos de que la URL de la imagen sea válida
            if (imagenUrl.isEmpty()) {
                libroFavorito.setImagenUrl("https://openlibrary.org/images/icons/avatar_book-sm.png");
            } else {
                // Asegurarse de que la URL de la imagen sea absoluta
                if (!imagenUrl.startsWith("http")) {
                    imagenUrl = "https://covers.openlibrary.org" + imagenUrl;
                }
                libroFavorito.setImagenUrl(imagenUrl);
            }            LibroFavorito savedBook = libroFavoritoRepository.save(libroFavorito);
            return ResponseEntity.ok().body(Map.of(
                "status", "added",
                "id", savedBook.getId(),
                "libro", Map.of(
                    "id", savedBook.getLibroId(),
                    "titulo", savedBook.getTitulo(),
                    "autor", savedBook.getAutor(),
                    "imagenUrl", savedBook.getImagenUrl()
                )
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Error interno del servidor"));
        }
    }

    @DeleteMapping("/favoritos/{id}")
    public ResponseEntity<?> eliminarFavorito(@PathVariable Long id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioRepository.findByNombre(auth.getName());

            if (usuario == null) {
                return ResponseEntity.status(401).body(Map.of("message", "Usuario no autenticado"));
            }

            LibroFavorito favorito = libroFavoritoRepository.findById(id)
                .filter(libro -> libro.getUsuario().getId().equals(usuario.getId()))
                .orElse(null);

            if (favorito == null) {
                return ResponseEntity.status(404).body(Map.of("message", "Libro no encontrado en favoritos"));
            }

            libroFavoritoRepository.delete(favorito);
            return ResponseEntity.ok().body(Map.of("message", "Libro eliminado de favoritos"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Error interno del servidor"));
        }
    }
}
