/**
 * @author Ostrovskiy Dmitriy
 * @created 02.08.2020
 * FileUploadService
 * @version v1.0
 */

package ru.travelmatch.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.travelmatch.base.entities.FileUpload;

import java.util.List;

public interface FileUploadService {

    String storeFile(MultipartFile file, Long userId);
    Resource loadFileAsResource(String fileName);
    boolean deleteFile(Long id);
    String getFileName(Long userId, String fileType);

    FileUpload findById(Long id);
    FileUpload findByFileName(String fileName);
    List<FileUpload> findAll();
    List<FileUpload> findAllByUserId(Long id);

}
