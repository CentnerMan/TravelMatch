/**
 * @author Ostrovskiy Dmitriy
 * @created 19.08.2020
 * DataBadRequest
 * @version v1.0
 */

package ru.travelmatch.exception;

public class DataBadRequest extends AbstractException {

    public DataBadRequest(String message) {
        super(message);
    }

    public DataBadRequest(String message, Throwable cause) {
        super(message, cause);
    }
}
