package robbegarm.blogapi.auth;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({AccessDeniedException.class})
    public final ResponseEntity<Object> handleUserNotFoundException(EntityNotFoundException ex, WebRequest request){
        return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public final ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request){
        return new ResponseEntity<>("401: Bad Credentials!", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({HttpClientErrorException.BadRequest.class})
    public final ResponseEntity<Object> handleBadRequestException(HttpClientErrorException.BadRequest ex, WebRequest request){
        return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
    }
}
