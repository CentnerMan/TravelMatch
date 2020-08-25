/**
 * @author Ostrovskiy Dmitriy
 * @created 11.07.2020
 * FileDto
 * @version v1.0
 */

package ru.travelmatch.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.travelmatch.base.entities.FileUpload;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileUploadResponceDto implements Serializable {

    private static final long serialVersionUID = -90000043L;

    private Long id;
    private String fileName;
    private String fileType;
    private String created;
//    private List<String> urlFile;
    private String urlFile;

    public FileUploadResponceDto(FileUpload fileUpload) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.YYYY");
        this.id = fileUpload.getId();
        this.fileName = fileUpload.getFileName();
        this.fileType = fileUpload.getFileType();
        this.created = formatter.format(new Date());
//        this.urlFile = getUrls(fileUpload);
        this.urlFile = fileUpload.getUploadDir();
    }

//    private List<String> getUrls(FileUpload fileUpload) {
//        List<String> urlFile = new ArrayList<>();
//        urlFile.add(fileUpload.getUploadUrl().getSmUrl());
//        urlFile.add(fileUpload.getUploadUrl().getMidUrl());
//        urlFile.add(fileUpload.getUploadUrl().getXlUrl());
//        urlFile.add(fileUpload.getUploadUrl().getOriginUrl());
//        return urlFile;
//    }
}
