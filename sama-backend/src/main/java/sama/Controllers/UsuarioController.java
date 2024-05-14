package sama.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sama.Entities.Usuario;
import sama.Services.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

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
    public ResponseEntity<String> updateUser(@RequestBody Usuario usuario) {
        Usuario updatedUser = usuarioService.save(usuario);
        if (updatedUser == null) {
            return ResponseEntity.status(500).body("Error al actualizar usuario");
        }
        return ResponseEntity.ok("Usuario actualizado");
    }

    @PostMapping()
    public ResponseEntity<String> saveUser(@RequestBody Usuario usuario) {
        Usuario savedUser = usuarioService.save(usuario);
        if (savedUser == null) {
            return ResponseEntity.status(500).body("Error al guardar usuario");
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