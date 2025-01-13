package fontys.sem6.circline.authentication.business.impl;

import fontys.sem6.circline.authentication.business.AccessTokenEncoder;
import fontys.sem6.circline.authentication.business.LoginUseCase;
import fontys.sem6.circline.authentication.business.exception.AccountNotFoundException;
import fontys.sem6.circline.authentication.business.exception.InvalidCredentialsException;
import fontys.sem6.circline.authentication.business.exception.InvalidEmailFormatException;
import fontys.sem6.circline.authentication.domain.AccessToken;
import fontys.sem6.circline.authentication.domain.LoginRequest;
import fontys.sem6.circline.authentication.domain.LoginResponse;
import fontys.sem6.circline.authentication.persistence.AccountRepository;
import fontys.sem6.circline.authentication.persistence.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        if (!isValidEmail(loginRequest.getEmail())) {
            throw new InvalidEmailFormatException();
        }
        AccountEntity account = accountRepository.findByEmail(loginRequest.getEmail());
        if (account == null) {
            throw new AccountNotFoundException();
        }


        if (!matchesPassword(loginRequest.getPassword(), account.getPassword())) {
            throw new InvalidCredentialsException();
        }


        String accessToken = generateAccessToken(account);
        return LoginResponse.builder().accessToken(accessToken).build();
    }
    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);

    }
    private String generateAccessToken(AccountEntity account) {
//        Long userId = account.getEmail() != null ? account.getId() : null;


        return accessTokenEncoder.encode(
                AccessToken.builder()
                        .subject(account.getEmail())
                        .roles(account.getRole())
                        .userId(account.getUserId())
                        .build());
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }
}
