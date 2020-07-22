/**
 * @author Ostrovskiy Dmitriy
 * @created 19.06.2020
 * DataNotFoundException
 * @version v1.0
 */

package ru.travelmatch.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
