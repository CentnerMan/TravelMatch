/**
 * @author Ostrovskiy Dmitriy
 * @created 20.08.2020
 * EntityNotFoundException
 * @version v1.0
 */

package ru.travelmatch.exception;

public class EntityNotFoundException extends Exception{

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Exception ex) {
        super(message,  ex);
    }

}
