package br.com.lucas.reserve_adim.services;

import br.com.lucas.reserve_adim.domain.user.AppUser;
import br.com.lucas.reserve_adim.domain.user.AuthenticationDTO;
import br.com.lucas.reserve_adim.domain.user.RegisterDTO;
import br.com.lucas.reserve_adim.repositories.UserRepository;
import br.com.lucas.reserve_adim.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String login(AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken((AppUser) auth.getPrincipal());
    }

    public void register(RegisterDTO data) {
        if (repository.findByEmail(data.email()) != null) throw new RuntimeException("Usuario ja existe");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        AppUser newUser = new AppUser(data.email(), data.name(), encryptedPassword, data.role());
        repository.save(newUser);
    }



}
