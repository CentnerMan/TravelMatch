/**
 * @author Ostrovskiy Dmitriy
 * @created 02.08.2020
 * FileUploadServiceImpl
 * @version v1.0
 */

package ru.travelmatch.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.travelmatch.base.entities.FileUpload;
import ru.travelmatch.base.repo.FileUploadRepository;
import ru.travelmatch.exception.FileUploadException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadServiceImpl implements FileUploadService {

    private final Path fileStorageLocation;

    // DocumentStoragePropertiesRepo docStorageRepo;
    @Autowired
    FileUploadRepository fileUploadRepo;

    @Autowired
    public FileUploadServiceImpl(FileUpload fileUpload) {
        this.fileStorageLocation = Paths.get(fileUpload.getUploadUrl().getOriginUrl())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);

        } catch (Exception ex) {
            throw new FileUploadException("Could not create the directory where the uploaded files will be stored.", ex);

        }
    }

    @Override
    public String storeFile(MultipartFile file, Long userId, String fileType) {
        // Normalize file name
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = "";
        try {
            // Check if the file's name contains invalid characters
            if (originalFileName.contains("..")) {
                throw new FileUploadException("Sorry! Filename contains invalid path sequence " + originalFileName);
            }
            String fileExtension = "";
            try {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            } catch (Exception e) {
                fileExtension = "";
            }
            fileName = userId + "_" + fileType + fileExtension;
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            FileUpload fileUpload = fileUploadRepo.checkFileByUserId(userId, fileType);
            if (fileUpload != null) {
                fileUpload.setFileFormat(file.getContentType());
                fileUpload.setFileName(fileName);
                fileUploadRepo.save(fileUpload);
            } else {
                FileUpload newFile = new FileUpload();
                newFile.setUserId(userId);
                newFile.setFileFormat(file.getContentType());
                newFile.setFileName(fileName);
                newFile.setFileType(fileType);
                fileUploadRepo.save(newFile);
            }
            return fileName;
        } catch (IOException ex) {
            throw new FileUploadException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        Resource resource = null;
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException | FileNotFoundException ex) {
            ex.printStackTrace();
            new FileNotFoundException("File not found " + fileName);
        }
        return  resource;
    }

    @Override
    public String getFileName(Long userId, String fileType) {
        return fileUploadRepo.getUploadFilePath(userId, fileType);
    }
}
