package fontys.sem6.circline.authentication.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidEmailFormatException extends ResponseStatusException {
    public InvalidEmailFormatException(){
        super(HttpStatus.BAD_REQUEST, "The provided email format is invalid.");
    }
}
