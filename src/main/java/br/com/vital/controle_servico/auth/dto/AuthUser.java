package br.com.vital.controle_servico.auth.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class AuthUser extends User {

    public AuthUser(br.com.vital.controle_servico.users.domain.User user) {
        super(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    private static Collection<GrantedAuthority> getAuthorities(br.com.vital.controle_servico.users.domain.User user) {
        return user.getRoles()
                .stream().
                flatMap(role -> role.getPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority(permission.getName().toUpperCase()))
                .collect(Collectors.toSet());
    }
}
