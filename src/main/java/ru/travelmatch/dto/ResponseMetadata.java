/**
 * @author Ostrovskiy Dmitriy
 * @created 04.08.2020
 * ResponseMetadata
 * @version v1.0
 */

package ru.travelmatch.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
public class ResponseMetadata implements Serializable {

    private static final long serialVersionUID = -90000093L;

    private int status;
    private String message;
    private Object data;
}
