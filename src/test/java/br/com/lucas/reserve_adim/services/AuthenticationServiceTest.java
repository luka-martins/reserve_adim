package br.com.lucas.reserve_adim.services;

import br.com.lucas.reserve_adim.domain.user.AppUser;
import br.com.lucas.reserve_adim.domain.user.AuthenticationDTO;
import br.com.lucas.reserve_adim.domain.user.RegisterDTO;
import br.com.lucas.reserve_adim.domain.user.UserRole;
import br.com.lucas.reserve_adim.repositories.UserRepository;
import br.com.lucas.reserve_adim.security.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private UserRepository repository;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService service;

    @Test
    public void testLogin_Success() {
        AuthenticationDTO dto = new AuthenticationDTO("email@dominio.com", "senha123");
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password());

        AppUser user = new AppUser(dto.email(), "Lucas", "senhaCriptografada", UserRole.ADMIN);
        Authentication auth = mock(Authentication.class);

        when(authenticationManager.authenticate(token)).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn("fake-jwt-token");

        String result = service.login(dto);
        assertEquals("fake-jwt-token", result);
    }

    @Test
    public void testRegister_Success() {
        RegisterDTO dto = new RegisterDTO("Lucas", "email@dominio.com", "senha123", UserRole.USER);

        when(repository.findByEmail(dto.email())).thenReturn(null);
        when(repository.save(any(AppUser.class))).thenAnswer(inv -> inv.getArgument(0));

        assertDoesNotThrow(() -> service.register(dto));
        verify(repository).save(any(AppUser.class));
    }

    @Test
    public void testRegister_Failure_UserExists() {
        RegisterDTO dto = new RegisterDTO("Lucas", "email@dominio.com", "senha123", UserRole.USER);
        AppUser existing = new AppUser(dto.email(), dto.name(), "senhaCriptografada", dto.role());

        when(repository.findByEmail(dto.email())).thenReturn(existing);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.register(dto);
        });

        assertEquals("Usuario ja existe", exception.getMessage());
    }

}
