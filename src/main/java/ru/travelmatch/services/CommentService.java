package ru.travelmatch.services;

import org.springframework.http.ResponseEntity;
import ru.travelmatch.dto.CommentDTO;

import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 23.08.2020
 * v1.0
 */
public interface CommentService {
    ResponseEntity create(CommentDTO commentDTO);
    ResponseEntity update(CommentDTO commentDTO);
    List<CommentDTO> getAll();
    ResponseEntity delete(Long id);
    ResponseEntity findById(Long id);
}
