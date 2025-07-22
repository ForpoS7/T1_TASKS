package t1.homeworks.tokenauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import t1.homeworks.tokenauth.dto.ApiResponse;
import t1.homeworks.tokenauth.dto.LoginForm;
import t1.homeworks.tokenauth.exception.UserNotFoundException;
import t1.homeworks.tokenauth.repository.UserRepository;
import t1.homeworks.tokenauth.security.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public ApiResponse<Map<String, String>> login(LoginForm loginForm) {
        String username = loginForm.getUsername();
        userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Такой пользователь не найден"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, loginForm.getPassword())
        );

        return formingResponse(username);
    }

    public ApiResponse<Map<String, String>> refreshTokens(String refreshToken) {
        String username = jwtService.getUsername(refreshToken);
        userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Такой пользователь не найден"));
        jwtService.equalsTokens(username, refreshToken);

        return formingResponse(username);
    }

    public ApiResponse<String> revokeToken(String username) {
        jwtService.revokeRefreshToken(username);
        return new ApiResponse<>("Токен успешно отозван", null);
    }

    private ApiResponse<Map<String, String>> formingResponse(String username) {
        String accessToken = jwtService.generateAccessToken(username);
        String refreshToken = jwtService.generateRefreshToken(username);

        jwtService.saveRefreshToken(username, refreshToken);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return new ApiResponse<>("Успешный вход", tokens);
    }
}
