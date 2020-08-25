/**
 * @author Ostrovskiy Dmitriy
 * @created 02.08.2020
 * FileUploadRepository
 * @version v1.0
 */

package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.travelmatch.base.entities.FileUpload;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {

    @Query("Select a from FileUpload a where user_id = ?1 and file_name = ?2")
    FileUpload checkFileByUserId(Long userId, String fileName);

    @Query("Select fileName from FileUpload a where user_id = ?1 and file_name = ?2")
    String getUploadFilePath(Long userId, String fileName);


    Optional<FileUpload> findById(Long id);
    Optional<FileUpload> findByFileName(String fileName);

    @Override
    void deleteById(Long FileId);

    List<FileUpload> findAll();
    List<FileUpload> findAllByUserId(Long id);

}
