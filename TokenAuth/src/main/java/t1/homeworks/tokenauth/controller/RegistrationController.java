package t1.homeworks.tokenauth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import t1.homeworks.tokenauth.dto.ApiResponse;
import t1.homeworks.tokenauth.dto.RegistrationForm;
import t1.homeworks.tokenauth.service.RegistrationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/registration")
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> registration(@RequestBody @Valid RegistrationForm registrationForm) {
        registrationService.createUser(registrationForm);
        return ResponseEntity.ok(
                new ApiResponse<>("Успешная регистрация пользователя", null)
        );
    }
}
