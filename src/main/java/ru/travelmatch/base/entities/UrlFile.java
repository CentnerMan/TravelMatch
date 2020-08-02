/**
 * @author Ostrovskiy Dmitriy
 * @created 02.08.2020
 * UrlFile
 * @version v1.0
 */

package ru.travelmatch.base.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name ="url_file")
public class UrlFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //5%
    @Column(name = "sm_url")
    private String smUrl;

    //50%
    @Column(name = "mid_url")
    private String midUrl;

    //90%
    @Column(name = "xl_url")
    private String xlUrl;

    //max size FullHD
    @Column(name = "origin_url")
    private String originUrl;
}
