/**
 * @author Ostrovskiy Dmitriy
 * @created 11.07.2020
 * FileDto
 * @version v1.0
 */

package ru.travelmatch.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class FileDto {

    private String name;

    private Date created;

    private String link;

    private String file;
}
