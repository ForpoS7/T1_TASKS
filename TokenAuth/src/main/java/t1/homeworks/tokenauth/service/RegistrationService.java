package t1.homeworks.tokenauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import t1.homeworks.tokenauth.dto.RegistrationForm;
import t1.homeworks.tokenauth.entity.User;
import t1.homeworks.tokenauth.exception.PasswordMismatchException;
import t1.homeworks.tokenauth.exception.UserAlreadyExistException;
import t1.homeworks.tokenauth.mapper.UserMapper;
import t1.homeworks.tokenauth.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void createUser(RegistrationForm registrationForm) {
        userRepository.findByUsername(registrationForm.getUsername())
                .ifPresent(user -> {
                    throw new UserAlreadyExistException("Такой пользователь уже существует");
                });
        userRepository.findByEmail(registrationForm.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistException("Такой пользователь уже существует");
                });
        if (!registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            throw new PasswordMismatchException("Пароли не совпадают");
        }

        String hashPassword = passwordEncoder.encode(registrationForm.getPassword());
        User user = userMapper.mapToUser(registrationForm, hashPassword);
        userRepository.save(user);
    }
}
