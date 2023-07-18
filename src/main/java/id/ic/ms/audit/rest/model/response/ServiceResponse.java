package id.ic.ms.audit.rest.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class ServiceResponse {

    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;

    public ServiceResponse(HttpStatus status) {
        this.code = status.value();
        this.message = status.getReasonPhrase();
    }

    public ServiceResponse(HttpStatus status, String message) {
        this.code = status.value();
        this.message = message;
    }

    public ServiceResponse() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
