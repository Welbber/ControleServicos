package br.com.vital.controle_servico.auth.service;

import br.com.vital.controle_servico.auth.dto.TokenResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final JwtService jwtService;

    public TokenResponseDTO authenticate(Authentication authentication) {
        return new TokenResponseDTO(jwtService.generateToken(authentication));
    }

    public TokenResponseDTO refreshToken(Authentication authentication) {
        return new TokenResponseDTO(jwtService.generateToken(authentication));
    }
}
