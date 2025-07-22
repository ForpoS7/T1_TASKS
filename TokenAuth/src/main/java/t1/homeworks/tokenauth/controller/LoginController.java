package t1.homeworks.tokenauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import t1.homeworks.tokenauth.dto.ApiResponse;
import t1.homeworks.tokenauth.dto.LoginForm;
import t1.homeworks.tokenauth.dto.RefreshTokenRequest;
import t1.homeworks.tokenauth.service.LoginService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody LoginForm loginForm) {
        ApiResponse<Map<String, String>> authTokens = loginService.login(loginForm);
        return ResponseEntity.ok(authTokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Map<String, String>>> refreshTokens(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        ApiResponse<Map<String, String>> apiResponse = loginService.refreshTokens(refreshToken);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/refresh/revoke")
    public ResponseEntity<ApiResponse<String>> revokeToken(@AuthenticationPrincipal UserDetails userDetails) {
        ApiResponse<String> apiResponse = loginService.revokeToken(userDetails.getUsername());
        return ResponseEntity.ok(apiResponse);
    }
}
