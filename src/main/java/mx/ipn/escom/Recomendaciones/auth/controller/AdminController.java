package mx.ipn.escom.Recomendaciones.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import mx.ipn.escom.Recomendaciones.auth.entity.Usuario;
import mx.ipn.escom.Recomendaciones.auth.service.UsuarioService;
import mx.ipn.escom.Recomendaciones.auth.entity.Rol;
import mx.ipn.escom.Recomendaciones.auth.repository.RolRepository;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolRepository rolRepository;

    @GetMapping("")
    public String adminPanel(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "admin";
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public Usuario getUser(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute Usuario usuario, @RequestParam(value = "roles", required = false) Set<String> roleNames) {
        if (roleNames != null) {
            Set<Rol> roles = roleNames.stream()
                .map(roleName -> rolRepository.findByNombre(roleName)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleName)))
                .collect(Collectors.toSet());
            usuario.setRoles(roles);
        }
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