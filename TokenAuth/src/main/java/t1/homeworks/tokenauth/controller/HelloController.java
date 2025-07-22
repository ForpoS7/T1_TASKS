package t1.homeworks.tokenauth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import t1.homeworks.tokenauth.dto.ApiResponse;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {
    @PreAuthorize("hasRole('GUEST')")
    @GetMapping("/guest")
    public ApiResponse<String> helloGuest() {
        return new ApiResponse<>("Доступ разрешен для гостя", "Hello Guest");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ApiResponse<String> helloAdmin() {
        return new ApiResponse<>("Доступ разрешен для администратора", "Hello Admin");
    }

    @PreAuthorize("hasRole('PREMIUM_USER')")
    @GetMapping("/premium")
    public ApiResponse<String> helloPremium() {
        return new ApiResponse<>("Доступ разрешен для премиум-пользователя", "Hello Premium User");
    }
}
