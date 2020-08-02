/**
 * @author Ostrovskiy Dmitriy
 * @created 02.08.2020
 * FileUploadRepository
 * @version v1.0
 */

package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.travelmatch.base.entities.FileUpload;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {

    @Query("Select a from FileUpload a where user_id = ?1 and file_type = ?2")
    FileUpload checkFileByUserId(Long userId, String fileType);

    @Query("Select fileName from FileUpload a where user_id = ?1 and file_type = ?2")
    String getUploadFilePath(Long userId, String fileType);
}
