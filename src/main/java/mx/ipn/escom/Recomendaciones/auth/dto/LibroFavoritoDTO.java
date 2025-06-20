package mx.ipn.escom.Recomendaciones.auth.dto;

import java.time.LocalDateTime;

public class LibroFavoritoDTO {
    private Long id;
    private String libroId;
    private String titulo;
    private String autor;
    private String imagenUrl;
    private LocalDateTime fechaAgregado;

    // Constructor
    public LibroFavoritoDTO(Long id, String libroId, String titulo, String autor, String imagenUrl, LocalDateTime fechaAgregado) {
        this.id = id;
        this.libroId = libroId;
        this.titulo = titulo;
        this.autor = autor;
        this.imagenUrl = imagenUrl;
        this.fechaAgregado = fechaAgregado;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getLibroId() {
        return libroId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setLibroId(String libroId) {
        this.libroId = libroId;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }
}
