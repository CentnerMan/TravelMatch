/**
 * @author Ostrovskiy Dmitriy
 * @created 19.06.2020
 * DataErrorResponse
 * @version v1.0
 */

package ru.travelmatch.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.*;

@Setter
@Getter
//@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataErrorResponse {

    private Map<String, Object> status;
    private String message;
    private String debugMessage;
    private Date date;

    private List<FieldValidationError> fieldValidationErrors;

    DataErrorResponse() {
        this.date = new Date();
    }

    DataErrorResponse(HttpStatus status) {
        this.status = setStatus(status);
        this.date = new Date();
    }

    DataErrorResponse(HttpStatus status, Throwable ex) {
        this.date = new Date();
        this.status = setStatus(status);
        this.message = "Unexpected error";
        this.setDebugMessage(ex.getLocalizedMessage());
    }

    DataErrorResponse(HttpStatus status, String message, Throwable ex) {
        this.date = new Date();
        this.status = setStatus(status);
        this.message = message;
        this.setDebugMessage(ex.getLocalizedMessage());
    }

    void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(error -> {
            FieldValidationError subError = new FieldValidationError();
            subError.setField(error.getField());
            subError.setMessage(error.getDefaultMessage());
            subError.setRejectedValue(error.getRejectedValue());
            subError.setObject(error.getObjectName());
            this.addSubError(subError);
        });
    }

    private void addSubError(FieldValidationError subError) {
        if (fieldValidationErrors == null) {
            fieldValidationErrors = new ArrayList<>();
        }
        fieldValidationErrors.add(subError);
    }

    public Map<String, Object> setStatus(HttpStatus status) {
        Map<String, Object> statusMap = new HashMap<>();
        statusMap.put("value", status.value());
        statusMap.put("reasonPhrase", status.getReasonPhrase());
        statusMap.put("name", status.name());
        statusMap.put("ordinal", status.ordinal());
    return statusMap;
    }

    @Override
    public String toString() {
        return "\n" + "DataErrorResponse{" + "\n" +
                "status=" + status + "," + "\n" +
                "message='" + message + "," + "\n" +
                "debugMessage='" + debugMessage + "," + "\n" +
                "date=" + date + "," + "\n" +
                "fieldValidationErrors=" + fieldValidationErrors +
                '}';
    }
}
