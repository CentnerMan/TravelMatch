/**
 * @author Ostrovskiy Dmitriy
 * @created 02.08.2020
 * FileUploadService
 * @version v1.0
 */

package ru.travelmatch.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    String storeFile(MultipartFile file, Long userId, String fileType);
    Resource loadFileAsResource(String fileName);
    String getFileName(Long userId, String fileType);


}
