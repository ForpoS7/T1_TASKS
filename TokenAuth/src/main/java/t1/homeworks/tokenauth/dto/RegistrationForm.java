package t1.homeworks.tokenauth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegistrationForm {
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 3, max = 20, message = "Имя пользователя должно содержать от 3 до 20 символов")
    private String username;

    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Неверный формат адреса электронной почты")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, max = 50, message = "Пароль должен содержать от 8 до 50 символов")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Пароль должен содержать как минимум одну заглавную букву," +
                    " одну строчную букву, одну цифру и один специальный символ"
    )
    private String password;

    @NotBlank(message = "Пароль не может быть пустым")
    private String confirmPassword;
}
