package robbegarm.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice // So it can be accessible from other controllers (controllers will share the methods)
@RestController
public class CustomizedResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidLoginException.class)
    public final ResponseEntity<Object> handleInvalidLoginExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse( 401, new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public final ResponseEntity<Object> PostNotFoundException(PostNotFoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(404, new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongRequestException.class)
    public final ResponseEntity<Object> WrongRequestException(WrongRequestException ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(400, new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GCPFileUploadException.class)
    public final ResponseEntity<Object> GCPFileUploadException(GCPFileUploadException ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(500, new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileWriteException.class)
    public final ResponseEntity<Object> FileWriteException(FileWriteException ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(500, new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidTypeException.class)
    public final ResponseEntity<Object> InvalidTypeException(InvalidTypeException ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(400, new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(500, new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
