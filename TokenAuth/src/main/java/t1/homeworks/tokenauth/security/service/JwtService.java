package t1.homeworks.tokenauth.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import t1.homeworks.tokenauth.entity.RefreshToken;
import t1.homeworks.tokenauth.exception.InvalidRefreshTokenException;
import t1.homeworks.tokenauth.repository.RefreshTokenRepository;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JwtService {
    @Value("${jwt.access.expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;
    private final RefreshTokenRepository tokenRepository;

    public String generateRefreshToken(String username) {
        return generateToken(username, refreshExpiration);
    }

    public String generateAccessToken(String username) {
        return generateToken(username, accessExpiration);
    }

    private String generateToken(String username, long expiration) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(algorithm);
    }

    public String getUsername(String token) {
        return Optional.of(jwtVerifier.verify(token))
                .map(Payload::getSubject)
                .orElse(null);
    }

    public void revokeRefreshToken(String username) {
        if (!tokenRepository.existsById(username)) {
            throw new InvalidRefreshTokenException("Refresh Token не найден для пользователя: " + username);
        }
        tokenRepository.deleteById(username);
    }

    public void equalsTokens(String username, String sentToken) {
        RefreshToken storedToken = tokenRepository.findById(username)
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh Token не найден для пользователя: " + username));

        if (!sentToken.equals(storedToken.getToken())) {
            throw new InvalidRefreshTokenException("Недействительный Refresh Token для пользователя: " + username);
        }
    }

    public void saveRefreshToken(String username, String refreshToken) {
        RefreshToken token = new RefreshToken()
                .setUsername(username)
                .setToken(refreshToken)
                .setTimeToLiveSeconds(604800L);
        tokenRepository.save(token);
    }
}
