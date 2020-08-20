/**
 * @author Ostrovskiy Dmitriy
 * @created 03.08.2020
 * FileUploadController
 * @version v1.0
 */

package ru.travelmatch.controllers.rest;

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
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

    @GetMapping()
    public @ResponseBody List<FileUpload> getDocument() {
        return fileUploadService.findAll();
    }

    @PostMapping(value = "/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFile(HttpServletRequest request,
                                        @RequestParam(value = "userId", required = false) Long userId,
                                        @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
//        Long userId = userService.findByUsername(request.getUserPrincipal().getName()).getId();
       if (userId==null) {
           userId = 2L;
       }
        String fileName = null;
       try {
           fileName = fileUploadService.storeFile(file, userId);
       } catch (NullPointerException ex) {
           throw new FileUploadException("Could not store file " + fileName + ". Please try again!", ex);
       }
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download")
                .path(fileName)
                .toUriString();
//        if (fileName.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        FileUpload fileUpload = fileUploadService.findByFileName(fileName);
        FileUploadResponceDto fileDto = new FileUploadResponceDto(fileUpload);
        return new ResponseEntity<>(fileDto,HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/download")
    public ResponseEntity<?> downloadFile(@RequestParam(value = "userId", required = false) Long userId,
                                               @RequestParam(value = "fileType", required = false) String fileType,
                                               @RequestParam(value = "fileName", required = false) String fileName,
                                               HttpServletRequest request) throws IOException {
//        String fileName = fileUploadService.getFileName(userId, fileType);
        Resource resource = null;
        if(fileName !=null && !fileName.isEmpty()) {
            try {
                resource = fileUploadService.loadFileAsResource(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Try to determine file's content type
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                //logger.info("Could not determine file type.");
            }
            // Fallback to the default content type if type could not be determined
            if(contentType == null) {
                contentType = "application/octet-stream";
            }
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.parseMediaType(contentType));
            httpHeaders.setContentDisposition(ContentDisposition.parse("attachment; filename=\"" + resource.getFilename() + "\""));

            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource res = resource.createRelative(fileName);
            try {
                logger.info("resource:" + res.contentLength());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(res, httpHeaders, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteFile(@RequestParam(value = "id", required = false) Long id,
                                          @RequestParam(value = "fileName", required = false) String fileName,
                                          HttpServletRequest request) throws IOException {
        fileUploadService.deleteFile(id);
        return new ResponseEntity<>(fileUploadService.findAll(), HttpStatus.OK);
    }
}
