package br.com.lucas.reserve_adim.controller;

import br.com.lucas.reserve_adim.controllers.AuthenticationController;
import br.com.lucas.reserve_adim.domain.user.AuthenticationDTO;
import br.com.lucas.reserve_adim.domain.user.LoginResponseDTO;
import br.com.lucas.reserve_adim.domain.user.RegisterDTO;
import br.com.lucas.reserve_adim.domain.user.UserRole;
import br.com.lucas.reserve_adim.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authService;

    @InjectMocks
    private AuthenticationController controller;

    @Test
    public void testLogin_Success() {
        AuthenticationDTO data = new AuthenticationDTO("user@example.com", "senha123");
        String fakeToken = "jwt-token";

        when(authService.login(data)).thenReturn(fakeToken);

        ResponseEntity response = controller.login(data);
        LoginResponseDTO body = (LoginResponseDTO) response.getBody();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(body);
        assertEquals(fakeToken, body.token());
    }

    @Test
    public void testLogin_Failure() {
        AuthenticationDTO data = new AuthenticationDTO("user@example.com", "senhaErrada");

        when(authService.login(data)).thenThrow(new RuntimeException("Credenciais inválidas"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controller.login(data);
        });

        assertEquals("Credenciais inválidas", exception.getMessage());
    }

    @Test
    public void testRegister_Success_Admin() {
        RegisterDTO data = new RegisterDTO("Admin User", "admin@example.com", "senhaSegura", UserRole.ADMIN);

        doNothing().when(authService).register(data);

        ResponseEntity response = controller.register(data);

        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testRegister_Success_Client() {
        RegisterDTO data = new RegisterDTO("Client User", "client@example.com", "senha123", UserRole.USER);

        doNothing().when(authService).register(data);

        ResponseEntity response = controller.register(data);

        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testRegister_Failure() {
        RegisterDTO data = new RegisterDTO("User", "user@example.com", "senha123", UserRole.USER);

        doThrow(RuntimeException.class).when(authService).register(data);

        ResponseEntity response = controller.register(data);

        assertEquals(400, response.getStatusCodeValue());
    }
}