package br.com.lucas.reserve_adim.controller;

import br.com.lucas.reserve_adim.controllers.AuthenticationController;
import br.com.lucas.reserve_adim.domain.user.*;
import br.com.lucas.reserve_adim.repositories.UserRepository;
import br.com.lucas.reserve_adim.security.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private UserRepository repository;

    @Mock
    private TokenService service;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void testLoginSuccess(){

        AuthenticationDTO data = new AuthenticationDTO("user", "pass");
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(data.email(), data.password());

        AppUser appUser = new AppUser("user", "user", "pass", UserRole.USER);
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(token)).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(appUser);
        when(service.generateToken(appUser)).thenReturn("mocked-token");

        // Act
        ResponseEntity response = authenticationController.login(data);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("mocked-token", ((LoginResponseDTO) response.getBody()).token());

    }

    @Test
    void testRegisterSuccess() {
        // Arrange
        RegisterDTO data = new RegisterDTO("newuser", "New User", "pass123", UserRole.USER);
        when(repository.findByEmail("newuser")).thenReturn(null);

        // Act
        ResponseEntity response = authenticationController.register(data);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repository, times(1)).save(any(AppUser.class));
    }

    @Test
    void testRegisterUserAlreadyExists() {
        // Arrange
        RegisterDTO data = new RegisterDTO("existing", "Existing", "pass123", UserRole.USER);
        when(repository.findByEmail("existing")).thenReturn(new AppUser());

        // Act
        ResponseEntity response = authenticationController.register(data);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(repository, never()).save(any());
    }


}
