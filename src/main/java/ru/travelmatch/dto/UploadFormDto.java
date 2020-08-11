/**
 * @author Ostrovskiy Dmitriy
 * @created 04.08.2020
 * UploadFormDto
 * @version v1.0
 */

package ru.travelmatch.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadFormDto {

    private String description;

    private String action;

    private MultipartFile[] files;

}
