package fontys.sem6.circline.authentication.business.impl;

import fontys.sem6.circline.authentication.business.AccessTokenEncoder;
import fontys.sem6.circline.authentication.business.exception.AccountNotFoundException;
import fontys.sem6.circline.authentication.business.exception.InvalidCredentialsException;
import fontys.sem6.circline.authentication.domain.LoginRequest;
import fontys.sem6.circline.authentication.domain.LoginResponse;
import fontys.sem6.circline.authentication.persistence.AccountRepository;
import fontys.sem6.circline.authentication.persistence.entity.AccountEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseImplTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccessTokenEncoder accessTokenEncoder;
    @InjectMocks
    private LoginUseCaseImpl loginUseCase;
   @Test
    void login_shouldReturnAccessToken(){
        LoginRequest request = LoginRequest.builder()
                .email("Sally@gmail.com")
                .password("123456")
                .build();
        AccountEntity accountEntity = AccountEntity.builder()
                .id(1L)
                .email(request.getEmail())
                .password("encodedPassword")
                .build();

        when(accountRepository.findByEmail(request.getEmail())).thenReturn(accountEntity);
        when(passwordEncoder.matches(request.getPassword(),accountEntity.getPassword())).thenReturn(true);
        when(accessTokenEncoder.encode(any())).thenReturn("accessToken");
        LoginResponse expected = LoginResponse.builder().accessToken("accessToken").build();
        LoginResponse actual = loginUseCase.login(request);
        assertEquals(expected,actual);
        verify(accountRepository).findByEmail(request.getEmail());

        verify(passwordEncoder).matches(request.getPassword(), accountEntity.getPassword());
        verify(accessTokenEncoder).encode(any());
    }
    @Test
    void login_shouldThrowCredentialsException_becauseUserIsNull(){
        LoginRequest request = LoginRequest.builder()
                .email("Sally@gmail.com")
                .password("123456")
                .build();
        when(accountRepository.findByEmail(request.getEmail())).thenReturn(null);
        assertThrows(AccountNotFoundException.class,()->loginUseCase.login(request));
    }
    @Test
    void login_shouldThrowException_becauseDidNotMatchPasssword(){
        LoginRequest request = LoginRequest.builder()
                .email("Sally@gmail.com")
                .password("123456")
                .build();
        AccountEntity accountEntity = AccountEntity.builder()
                .id(1L)
                .email(request.getEmail())
                .password("encodedPassword")
                .build();

        when(accountRepository.findByEmail(request.getEmail())).thenReturn(accountEntity);
        when(passwordEncoder.matches(request.getPassword(),accountEntity.getPassword())).thenReturn(false);
        assertThrows(InvalidCredentialsException.class,()->loginUseCase.login(request));
    }


}