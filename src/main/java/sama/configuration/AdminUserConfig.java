package sama.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import sama.entity.Usuario;
import sama.repository.UsuarioRepository;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AdminUserConfig {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initAdminUser() {
        return args -> {
            if (!usuarioRepository.existsByRol("0")) {
                Usuario admin = new Usuario();
                admin.setNombre("Default Admin");
                admin.setCorreo("admin@sama.com");
                admin.setContrasenia(passwordEncoder.encode("admin123"));
                admin.setRol("0");
                admin.setEmpresas(List.of());
                usuarioRepository.save(admin);
                System.out.println("Administrador default creado");
            } else {
                System.out.println("Ya existe al menos un usuario administrador");
            }
        };
    }
}