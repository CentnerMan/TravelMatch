/**
 * @author Ostrovskiy Dmitriy
 * @created 19.06.2020
 * DataErrorResponse
 * @version v1.0
 */

package ru.travelmatch.exception;

public class DataErrorResponse {
    private int status;
    private String message;
    private long timestamp;

    public DataErrorResponse() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
