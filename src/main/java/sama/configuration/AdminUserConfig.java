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
            String adminEmail = "admin@tuapp.com";
            if (usuarioRepository.findByCorreo(adminEmail).isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombre("Admin");
                admin.setCorreo(adminEmail);
                admin.setContrasenia(passwordEncoder.encode("admin123"));
                admin.setRol("Administrador");
                admin.setEmpresas(List.of());
                usuarioRepository.save(admin);
                System.out.println("Usuario administrador creado: " + adminEmail);
            } else {
                System.out.println("Usuario administrador ya existe.");
            }
        };
    }
}