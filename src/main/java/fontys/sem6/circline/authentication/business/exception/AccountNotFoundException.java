package fontys.sem6.circline.authentication.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountNotFoundException extends ResponseStatusException {
    public AccountNotFoundException(){
        super(HttpStatus.BAD_REQUEST, "INVALID_USERNAME");
    }
}
