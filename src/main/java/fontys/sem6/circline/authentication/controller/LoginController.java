package fontys.sem6.circline.authentication.controller;

import fontys.sem6.circline.authentication.business.LoginUseCase;
import fontys.sem6.circline.authentication.domain.LoginRequest;
import fontys.sem6.circline.authentication.domain.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200","https://frontendapp-service.azurewebsites.net"})
public class LoginController {
    private final LoginUseCase loginUseCase;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = loginUseCase.login(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }
}