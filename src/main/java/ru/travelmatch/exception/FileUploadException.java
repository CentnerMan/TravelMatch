/**
 * @author Ostrovskiy Dmitriy
 * @created 02.08.2020
 * FileStorageException
 * @version v1.0
 */

package ru.travelmatch.exception;

public class FileUploadException extends AbstractException {

    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
