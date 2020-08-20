/**
 * @author Ostrovskiy Dmitriy
 * @created 20.08.2020
 * FieldValidationError
 * @version v1.0
 */

package ru.travelmatch.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FieldValidationError {

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public FieldValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
