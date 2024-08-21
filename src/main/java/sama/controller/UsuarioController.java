package sama.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sama.dto.UsuarioDTO;
import sama.entity.Usuario;
import sama.service.UsuarioService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable String id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PutMapping()
    public ResponseEntity<String> updateUser(@RequestBody UsuarioDTO usuario) {
        Usuario updatedUser = usuarioService.update(usuario);
        if (updatedUser == null) {
            return ResponseEntity.status(500).body("Error al actualizar usuario");
        }
        return ResponseEntity.ok("Usuario actualizado");
    }

    @PostMapping()
    public ResponseEntity<String> saveUser(@RequestBody UsuarioDTO usuario) {
        int response = usuarioService.save(usuario);
        if (response == 1) {
            return ResponseEntity.status(500).body("Usuario ya existe");
        }
        return ResponseEntity.ok("Usuario guardado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        int response = usuarioService.deleteById(id);
        if (response == 1) {
            return ResponseEntity.ok("Usuario eliminado");
        }
        return ResponseEntity.status(500).body("Error al eliminar usuario");
    }
}