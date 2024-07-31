package sama.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sama.dto.UsuarioDTO;
import sama.entity.Usuario;
import sama.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario save(UsuarioDTO usuario) {
        Usuario nuevoUsuario = Usuario.builder()
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .contrasenia(passwordEncoder.encode(usuario.getContrasenia()))
                .rol(usuario.getRol())
                .empresas(usuario.getEmpresas())
                .build();
        return usuarioRepository.save(nuevoUsuario);
    }

    public int deleteById(String id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return 1;
        }
        return 0;
    }

    public Usuario obtenerUsuarioPorId(String id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario update(UsuarioDTO usuarioDTO) {
        Usuario updatedUser = usuarioRepository.findById(usuarioDTO.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        updatedUser.setNombre(Optional.ofNullable(usuarioDTO.getNombre()).orElse(updatedUser.getNombre()));
        updatedUser.setCorreo(Optional.ofNullable(usuarioDTO.getCorreo()).orElse(updatedUser.getCorreo()));
        updatedUser.setContrasenia(Optional.ofNullable(usuarioDTO.getContrasenia()).map(passwordEncoder::encode).orElse(updatedUser.getContrasenia()));
        updatedUser.setRol(Optional.ofNullable(usuarioDTO.getRol()).orElse(updatedUser.getRol()));
        updatedUser.setEmpresas(Optional.ofNullable(usuarioDTO.getEmpresas()).orElse(updatedUser.getEmpresas()));

        return usuarioRepository.save(updatedUser);
    }
}