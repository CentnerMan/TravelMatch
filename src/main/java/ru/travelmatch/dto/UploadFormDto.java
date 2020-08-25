/**
 * @author Ostrovskiy Dmitriy
 * @created 04.08.2020
 * UploadFormDto
 * @version v1.0
 */

package ru.travelmatch.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
public class UploadFormDto implements Serializable {

    private static final long serialVersionUID = -90000089L;

    private String description;

    private String action;

    private MultipartFile[] files;

}
