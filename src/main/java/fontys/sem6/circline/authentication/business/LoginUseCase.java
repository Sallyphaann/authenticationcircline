package fontys.sem6.circline.authentication.business;

import fontys.sem6.circline.authentication.domain.LoginRequest;
import fontys.sem6.circline.authentication.domain.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest loginRequest);
}
