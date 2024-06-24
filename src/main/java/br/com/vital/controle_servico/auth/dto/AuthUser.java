package br.com.vital.controle_servico.auth.dto;

import br.com.vital.controle_servico.users.domain.Role;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.List;

@Getter
public class AuthUser extends User {

    private final List<Role> roles;

    public AuthUser(br.com.vital.controle_servico.users.domain.User user) {
        super(user.getUsername(), user.getPassword(), Collections.emptyList());
        this.roles = user.getRoles();
    }
}
