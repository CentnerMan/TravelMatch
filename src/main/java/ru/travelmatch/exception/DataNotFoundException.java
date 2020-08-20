/**
 * @author Ostrovskiy Dmitriy
 * @created 19.06.2020
 * DataNotFoundException
 * @version v1.0
 */

package ru.travelmatch.exception;

public class DataNotFoundException extends AbstractException {

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
