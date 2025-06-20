package mx.ipn.escom.Recomendaciones.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import mx.ipn.escom.Recomendaciones.auth.entity.Usuario;
import mx.ipn.escom.Recomendaciones.auth.service.UsuarioService;
import mx.ipn.escom.Recomendaciones.auth.entity.Rol;
import mx.ipn.escom.Recomendaciones.auth.repository.RolRepository;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import java.util.HashSet;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")  // Agregar esta línea
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolRepository rolRepository;

    @GetMapping("")
    public String adminPanel(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        model.addAttribute("todosRoles", rolRepository.findAll());
        return "admin";
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public Usuario getUser(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute Usuario usuario, @RequestParam(value = "roleNames", required = false) List<Long> roleIds) {
        Set<Rol> roles = new HashSet<>();
        if (roleIds != null) {
            roles = roleIds.stream()
                .map(roleId -> rolRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + roleId)))
                .collect(Collectors.toSet());
        }
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return "redirect:/admin";
    }

    @DeleteMapping("/deleteUser/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            usuarioService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}