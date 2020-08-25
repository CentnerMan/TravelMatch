/**
 * @author Ostrovskiy Dmitriy
 * @created 19.08.2020
 * AbstractException
 * @version v1.0
 */

package ru.travelmatch.exception;

public abstract class AbstractException extends RuntimeException {

    public AbstractException(String message) {
        super(message);
    }

    public AbstractException(String message, Throwable cause) {
        super(message, cause);
    }
}
