package mx.ipn.escom.Recomendaciones.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import mx.ipn.escom.Recomendaciones.auth.entity.Usuario;
import mx.ipn.escom.Recomendaciones.auth.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import mx.ipn.escom.Recomendaciones.auth.entity.Rol;
import mx.ipn.escom.Recomendaciones.auth.repository.RolRepository;
import java.util.Collections;

@Service
public class UsuarioService {
    
    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Mantener el método existente
    public void registrarUsuario(Usuario usuario) {
        // Codificar la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        // Buscar y asignar el rol USER por defecto
        Rol rolUsuario = rolRepository.findByNombre("ROLE_USER")
            .orElseThrow(() -> new RuntimeException("Error: Rol de usuario no encontrado."));
        usuario.setRoles(Collections.singleton(rolUsuario));
        
        // Guardar el usuario
        usuarioRepository.save(usuario);
    }

    // Agregar nuevos métodos para el panel de administración
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            // Nuevo usuario
            if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                throw new RuntimeException("La contraseña es requerida para nuevos usuarios");
            }
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            // Usuario existente
            Usuario existingUser = findById(usuario.getId());
            if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                usuario.setPassword(existingUser.getPassword());
            } else {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
            // Mantener los roles existentes si no se proporcionan nuevos
            if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
                usuario.setRoles(existingUser.getRoles());
            }
        }
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        Usuario usuario = findById(id);
        if (usuario.getRoles().stream()
                .anyMatch(rol -> rol.getNombre().equals("ROLE_ADMIN"))) {
            long adminCount = usuarioRepository.findAll().stream()
                    .filter(u -> u.getRoles().stream()
                            .anyMatch(r -> r.getNombre().equals("ROLE_ADMIN")))
                    .count();
            if (adminCount <= 1) {
                throw new RuntimeException("No se puede eliminar el último administrador");
            }
        }
        usuarioRepository.deleteById(id);
    }
}