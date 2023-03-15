package robbegarm.blogapi.exception;

import java.util.Date;

public class ExceptionResponse {
    //status code
    //timestamp
    //message
    //detail

    private int httpStatusCode;
    private Date timestamp;
    private String message;
    private String details;

    public ExceptionResponse(int httpStatusCode, Date timestamp, String message, String details) {
        this.httpStatusCode = httpStatusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public int getHttpStatusCode(){return  httpStatusCode;}

    public void setHttpStatusCode() {this.httpStatusCode = httpStatusCode;}

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
