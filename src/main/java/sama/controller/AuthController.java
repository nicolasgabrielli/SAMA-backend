package sama.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sama.dto.AuthRequestDTO;
import sama.dto.AuthResponseDTO;
import sama.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Maneja las solicitudes POST para iniciar sesión.
     *
     * @param authRequestDTO Objeto que contiene las credenciales de autenticación.
     * @return ResponseEntity con el DTO de respuesta de autenticación.
     */
    @PostMapping
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(authService.login(authRequestDTO));
    }
}