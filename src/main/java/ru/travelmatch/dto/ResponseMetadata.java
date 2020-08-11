/**
 * @author Ostrovskiy Dmitriy
 * @created 04.08.2020
 * ResponseMetadata
 * @version v1.0
 */

package ru.travelmatch.dto;

import lombok.Data;

@Data
public class ResponseMetadata {

    private int status;
    private String message;
    private Object data;
}
