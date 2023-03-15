package robbegarm.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GCPFileUploadException extends RuntimeException{
    public GCPFileUploadException(String message) {
        super(message);
    }
}
