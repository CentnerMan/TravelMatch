/**
 * @author Ostrovskiy Dmitriy
 * @created 02.08.2020
 * FileUpload
 * @version v1.0
 */

package ru.travelmatch.base.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name ="file_upload")
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_format")
    private String fileFormat;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "upload_dir")
    private Long uploadDir;

    @JsonManagedReference
    @OneToOne @JoinTable(name = "url_file",
            joinColumns = @JoinColumn(name = "upload_dir"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private UrlFile uploadUrl;

}
