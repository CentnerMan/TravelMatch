/**
 * @author Ostrovskiy Dmitriy
 * @created 11.07.2020
 * FileDto
 * @version v1.0
 */

package ru.travelmatch.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.travelmatch.base.entities.FileUpload;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Data
@RequiredArgsConstructor
public class FileUploadResponceDto {

    private Long id;
    private String fileName;
    private String fileType;
    private String created;
    private List<String> urlFile;

    public FileUploadResponceDto(FileUpload fileUpload) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.YYYY");
        this.id = fileUpload.getId();
        this.fileName = fileUpload.getFileName();
        this.fileType = fileUpload.getFileType();
        this.created = formatter.format(fileUpload.getCreated().toString());
        this.urlFile = getUrls(fileUpload);
    }

    private List<String> getUrls(FileUpload fileUpload) {
        List<String> urlFile = new ArrayList<>();
        urlFile.add(fileUpload.getUploadUrl().getSmUrl());
        urlFile.add(fileUpload.getUploadUrl().getMidUrl());
        urlFile.add(fileUpload.getUploadUrl().getXlUrl());
        urlFile.add(fileUpload.getUploadUrl().getOriginUrl());
        return urlFile;
    }
}
