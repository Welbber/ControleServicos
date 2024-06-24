package br.com.vital.controle_servico.auth.service;

import br.com.vital.controle_servico.auth.dto.AuthRequestDTO;
import br.com.vital.controle_servico.common.exception.UnauthorizedException;
import br.com.vital.controle_servico.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public String registerUser(String secretKey, String rawPassword) {
        return passwordEncoder.encode(rawPassword);
        // Salvar o usuário no banco de dados com a senha codificada

        // userRepository.save(user); // Assumindo que você tem um repositório para salvar o usuário
    }

    public boolean authenticate(AuthRequestDTO requestDTO) {
        log.info("Request authenticate for user {}", requestDTO.username());
        var user = userRepository.findByUsername(requestDTO.username())
                .orElseThrow(UnauthorizedException::new);

        if (!passwordEncoder.matches(requestDTO.password(), user.getPassword())) {
            throw new UnauthorizedException();
        }
        return true;

    }
}
