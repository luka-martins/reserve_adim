package br.com.lucas.reserve_adim.repositories;

import br.com.lucas.reserve_adim.domain.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<AppUser,String> {
    UserDetails findByLogin(String login);
}
