/**
 * @author Ostrovskiy Dmitriy
 * @created 03.08.2020
 * FileUploadController
 * @version v1.0
 */

package ru.travelmatch.controllers.rest;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.travelmatch.base.entities.FileUpload;
import ru.travelmatch.dto.FileUploadResponceDto;
import ru.travelmatch.exception.FileUploadException;
import ru.travelmatch.services.FileUploadServiceImpl;
import ru.travelmatch.services.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping(value = "api/v1/file")
@CrossOrigin(origins = "*")
public class FileUploadRestController implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadRestController.class);

    private final Path fileStorageLocation;
    private FileUploadServiceImpl fileUploadService;
    private UserServiceImpl userService;
    private String pathUser = "download";

    @Autowired
    public FileUploadRestController(FileUploadServiceImpl fileUploadService, UserServiceImpl userService) {
        this.fileUploadService = fileUploadService;
        this.userService = userService;
        this.fileStorageLocation = Paths.get(pathUser).toAbsolutePath().normalize();
    }

    /**
     * Метод запроса списка файлов
     *
     * @return List<FileUpload>
     * @return Exception
     */
    @GetMapping()
    @ApiOperation("Return list of file.")
    public @ResponseBody List<FileUpload> getFileList() {
        List<FileUpload> fileList = fileUploadService.findAll();
        logger.info("Get file list. List size = '{}'.", fileList.size());
        return fileList;
    }


    /**
     * Метод отправки файла на сервер
     *
     * @param request                           - HttpServletRequest запрос
     * @param response                          - HttpServletResponse ответ
     * @param userId                            - Long, user Id
     * @param file                              - MultipartFile, formData for file
     *
     * Файл сохраняется с префиксом userId + название файла fileName: userId_fileName
     * @return fileDto
     * @return Exception
     */
    @PostMapping(value = "/upload")
    @ApiOperation("Upload file to server.")
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFile(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam(value = "userId", required = false) Long userId,
                                        @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
//        Long userId = userService.findByUsername(request.getUserPrincipal().getName()).getId();
        if (userId==null) {
            userId = 2L;
        }
        String fileName = null;
        try {
            fileName = fileUploadService.storeFile(file, userId);
            logger.info("File '{}' upload.", fileName);
        } catch (NullPointerException ex) {
            throw new FileUploadException("Could not store file " + fileName + ". Please try again!", ex);
        }
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download")
                .path(fileName)
                .toUriString();
//        if (fileName.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        FileUpload fileUpload = fileUploadService.findByFileName(fileName);
        logger.info("File '{}' save information  in DB. File= {}", fileName, fileUpload.toString());
        FileUploadResponceDto fileDto = new FileUploadResponceDto(fileUpload);
        return new ResponseEntity<>(fileDto,HttpStatus.CREATED);
    }

    /**
     * Метод загрузки файла с сервера
     *
     * @param request                           - HttpServletRequest запрос
     * @param response                          - HttpServletResponse ответ
     * @param userId                            - Long, user Id
     * @param fileType                          - String, fileType from front-end
     * @param fileName                          - String, fileName from front-end
     * resource                                 - Resource, resource for download
     *
     * Загрузка файла осуществляется по имени - ищется соответствующий файл на сервере
     * URI найденного файла и вся необходимая информация запаковывается в resource
     * Front-end на основе полученного resource получает файл по указаному пути в URI
     * @return resource
     * @return FileNotFoundException
     */
    @GetMapping(value = "/download")
    @ApiOperation("Download file from server.")
    public ResponseEntity<?> downloadFile(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(value = "userId", required = false) Long userId,
                                          @RequestParam(value = "fileType", required = false) String fileType,
                                          @RequestParam(value = "fileId", required = false) Long fileId,
                                          @RequestParam(value = "fileName", required = false) String fileName) throws IOException {
//        String fileName = fileUploadService.getFileName(userId, fileType);
        Resource resource = null;
        FileUpload file = getFile(fileId, fileName);
        fileName = file.getFileName();
        if(fileName !=null && !fileName.isEmpty()) {
            try {
                resource = fileUploadService.loadFileAsResource(fileName);
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error("Resource not load!{}", ex.toString());
            }
            // Try to determine file's content type
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                logger.error("Could not determine file type!{}", ex.toString());
            }
            // Fallback to the default content type if type could not be determined
            if(contentType == null) {
                contentType = "application/octet-stream";
            }
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.parseMediaType(contentType));
            httpHeaders.setContentDisposition(ContentDisposition.parse("attachment; filename=\"" + resource.getFilename() + "\""));

//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
//            Resource res = resource.createRelative(fileName);
            try {
                logger.info("File length: {} byte", resource.contentLength());
//                logger.info("File length res: {} byte", res.contentLength());
                logger.info("File '{}' send to download", resource.getFilename());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Метод удаления файла с сервера
     *
     * @param request                           - HttpServletRequest запрос
     * @param fileId                            - Long, fileId from front-end
     * @param fileName                          - String, fileName from front-end
     * resource                                 - Resource, resource for download
     *
     * Для
     * @return List<FileUpload>
     * @return FileNotFoundException
     */
    @DeleteMapping(value = "/delete")
    @ApiOperation("Delete file from server.")
    public ResponseEntity<?> deleteFile(HttpServletRequest request,
                                        @RequestParam(value = "fileId", required = false) Long fileId,
                                        @RequestParam(value = "fileName", required = false) String fileName) throws IOException {
        FileUpload file = getFile(fileId, fileName);
        fileUploadService.deleteFile(file.getId());
        logger.info("Delete file '{}' done.", file.getFileName());
        return new ResponseEntity<>(fileUploadService.findAll(), HttpStatus.OK);
    }

    /**
     * Метод поиска файла по логину или имени
     *
     * @param id                                - Long, fileId from front-end
     * @param name                              - String, fileName from front-end
     *
     * @return FileUpload file
     * @return throw new FileNotFoundException()
     */

    public FileUpload getFile(Long id, String name) throws IOException{
        FileUpload file;
        if (id==null) {
            if (name==null) {
                throw new FileNotFoundException("File not found!");
            } else {
                file = fileUploadService.findByFileName(name);
            }
        }
        file = fileUploadService.findById(id);
        return file;
    }
}
