/**
 * @author Ostrovskiy Dmitriy
 * @created 02.08.2020
 * FileUploadServiceImpl
 * @version v1.0
 */

package ru.travelmatch.services;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.travelmatch.base.entities.FileUpload;
import ru.travelmatch.base.repo.FileUploadRepository;
import ru.travelmatch.base.repo.UserRepository;
import ru.travelmatch.dto.FileUploadResponceDto;
import ru.travelmatch.exception.EntityNotFoundException;
import ru.travelmatch.exception.FileUploadException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final Path fileStorageLocation;
    private String pathUser;
    private Principal principal;
    private UserRepository userRepository;
    private FileUploadRepository fileUploadRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setFileUploadRepository(FileUploadRepository fileUploadRepository) {
        this.fileUploadRepository = fileUploadRepository;
    }

    public FileUploadServiceImpl() {
        if (principal == null) {
            pathUser = "download";
        } else {
            pathUser = userRepository.findUserByUsername(principal.getName()).get().getUsername();
        }
        this.fileStorageLocation = Paths.get(pathUser)
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileUploadException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    public FileUploadResponceDto save(MultipartFile file) throws IOException {
        FileUpload doc = new FileUpload();
        doc.setFileName(file.getOriginalFilename());
        fileUploadRepository.save(doc);
        FileUploadResponceDto metadata = new FileUploadResponceDto();
        return metadata;
    }

    @Override
    public FileUpload findById(Long id) {
        return fileUploadRepository.findById(id).orElse(null);
    }

    @Override
    public FileUpload findByFileName(String fileName) {
        return fileUploadRepository.findByFileName(fileName).orElse(null);
    }

    @Override
    public List<FileUpload> findAll() {
        List<FileUpload> allFiles = fileUploadRepository.findAll();
        return (List<FileUpload>)allFiles;
    }

    @Override
    public List<FileUpload> findAllByUserId(Long id) {
        List<FileUpload> allFiles = fileUploadRepository.findAllByUserId(id);
        return allFiles.isEmpty()? null: allFiles;
    }

    @Override
    public String storeFile(MultipartFile file, Long userId) {
        // Normalize file name
        String fileName = "";
        try {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileType = file.getContentType();

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
            fileName = userId + "_" + originalFileName;
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            FileUpload fileUpload = fileUploadRepository.checkFileByUserId(userId, fileName);
            String urlTarget = targetLocation.toString();
//            String urlTarget = targetLocation.toString()
//                    .substring(targetLocation.toString().lastIndexOf(pathUser));
            if (fileUpload != null) {
                fileUpload.setFileFormat(fileExtension);
                fileUpload.setFileName(fileName);
                fileUpload.setFileType(fileType);
                fileUpload.setUploadDir(urlTarget);
                fileUploadRepository.save(fileUpload);
            } else {
                FileUpload newFile = new FileUpload();
                newFile.setUserId(userId);
                newFile.setFileFormat(fileExtension);
                newFile.setFileName(fileName);
                newFile.setFileType(fileType);
                newFile.setUploadDir(urlTarget);
                fileUploadRepository.save(newFile);
            }
            return fileName;
        } catch (NullPointerException | IOException ex) {
            ex.printStackTrace();
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
    public boolean deleteFile(Long id) throws FileNotFoundException {
        FileUpload fileUpload = fileUploadRepository.findById(id).orElseGet(null);
        if (fileUpload != null) {
            String fileName = fileUpload.getFileName();
            try {
                Path targetLocation = this.fileStorageLocation.resolve(fileName);
                File delFile = new File(targetLocation.toString());
                if (delFile.delete()) {
                    fileUploadRepository.deleteById(id);
                    return true;
                } else {
                    throw new FileNotFoundException("File '" + fileName + "' not found for delete!");
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                throw new FileNotFoundException("File '" + fileName + "' not found in path!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public String getFileName(Long userId, String fileType) {
        return fileUploadRepository.getUploadFilePath(userId, fileType);
    }
}
