package br.com.vital.controle_servico.auth.service;

import br.com.vital.controle_servico.auth.dto.AuthUser;
import br.com.vital.controle_servico.common.exception.UnauthorizedException;
import br.com.vital.controle_servico.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserAuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Request authenticate for user {}", username);
        var user = userRepository.findByUsername(username)
                .orElseThrow(UnauthorizedException::new);
        return new AuthUser(user);
    }

}
