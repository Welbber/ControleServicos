package br.com.vital.controle_servico.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant expiring = now.plusSeconds(900L);// 15min

        String email = authentication.getName();
        String tenantId = null;

        if (authentication.getPrincipal() instanceof br.com.vital.controle_servico.auth.dto.AuthUser authUser) {
            email = authUser.getEmail();
            tenantId = authUser.getTenantId();
        } else if (authentication.getPrincipal() instanceof br.com.vital.controle_servico.auth.service.UserAuthenticated userAuthenticated) {
            email = userAuthenticated.getEmail();
            tenantId = userAuthenticated.getTenantId();
        }

        var claimsBuilder = JwtClaimsSet.builder()
                .issuer("controle-frontend")
                .subject(authentication.getName())
                .issuedAt(now)
                .expiresAt(expiring)
                .claim("email", email)
                .claim("permissions", authentication.getAuthorities());

        if (tenantId != null) {
            claimsBuilder.claim("tenantId", tenantId);
        }

        var claims = claimsBuilder.build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String extractUsername(String token) {
        return decoder.decode(token).getSubject();
    }

    public Instant extractExpiration(String token) {
        return decoder.decode(token).getExpiresAt();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private String extractClaim(String token, String claimName) {
        return claimsFromToken(token).get(claimName).toString();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).isBefore(Instant.now());
    }

    private Map<String, Object> claimsFromToken(String token) {
        return decoder.decode(token).getClaims();
    }
}
