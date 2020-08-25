package ru.travelmatch.services;

import lombok.AllArgsConstructor;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.travelmatch.base.entities.Article;
import ru.travelmatch.base.entities.Comment;
import ru.travelmatch.base.entities.User;
import ru.travelmatch.base.repo.CommentRepository;
import ru.travelmatch.dto.CommentDTO;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @Author Farida Gareeva
 * Created 25.08.2020
 * v1.0
 */
@Service
@AllArgsConstructor
//@Transactional
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private UserService userService;
    private ArticleService articleService;
    private HttpServletResponse response;

    @Override
    public ResponseEntity create(CommentDTO commentDTO) {
        commentDTO.setId(null);
        return save(commentDTO);
    }

    @Override
    public ResponseEntity update(CommentDTO commentDTO) {
        return save(commentDTO);
    }

    public ResponseEntity save(CommentDTO commentDTO) {
        try {
            Comment comment = getComment(commentDTO);
            Comment savedComment = commentRepository.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CommentDTO(savedComment));
        } catch (EntityNotFoundException ex) {
            sendErrorToResponse(ex.getMessage());
            return ResponseEntity.status(response.getStatus())
                    .body(ex.getMessage());
        } catch (Exception ex) {
            sendErrorToResponse(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(commentDTO + " wasn't saved\n" + ex.getMessage());
        }
    }

    private void sendErrorToResponse(String msg) {
        try {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private Comment getComment(CommentDTO dto) throws EntityNotFoundException {
        Comment comment = null;
        if (dto.getId() != null) {
            comment = commentRepository.findById(dto.getId()).orElse(null);
        }

        Article article = articleService.findById(dto.getArticleId());
        User user = userService.findById(dto.getUserId());
        Comment parent = null;
        if (dto.getParentId() != null) {
            parent = commentRepository.findById(dto.getParentId()).orElse(null);
        }
        StringJoiner stringJoiner = new StringJoiner("\n");
        if (user == null) {
            stringJoiner.add("User with id = " + dto.getUserId() + " not found");
        }
        if (article == null) {
            stringJoiner.add("Article with id = " + dto.getArticleId() + " not found");
        }
        if (dto.getParentId() != null && parent == null) {
            stringJoiner.add("Comment with id = " + dto.getParentId() + " not found");
        }
        if (stringJoiner.length() > 0) {
            throw new EntityNotFoundException(stringJoiner.toString());
        }
        if (comment == null) {
            comment = new Comment();
        }
        comment.setArticle(article);
        comment.setUser(user);
        comment.setParentId(dto.getParentId());
        comment.setText(dto.getText());

        return comment;
    }

    @Override
    public List<CommentDTO> getAll() {
        return commentRepository.findAll()
                .stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity delete(Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) {
            sendErrorToResponse("Comment with id = " + id + " could't be deleted because it isn't exists");
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("");
        }
        //проверим, можно ли удалять объект, возможно, он является владельцем других объектов
        List<Comment> children = commentRepository.findAllByParentId(comment.getId());
        if (children.size() > 0) {
            StringJoiner stringJoiner = new StringJoiner(",");
            children.forEach(com -> {
                stringJoiner.add(com.getId().toString());
            });
            sendErrorToResponse("Comment with id = " + id + " could't be deleted because it's an owner of other comments (id = " +
                    stringJoiner.toString() + ")");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("");
        }
        try {
            commentRepository.delete(comment);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Comment with id =" + id + " was successfully deleted");
        } catch (NonTransientDataAccessException ex) {
            sendErrorToResponse(ex.getCause().getCause().getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Comment with id = " + id + " wasn't deleted\n" + ex.getMessage());
        }
    }

    @Override
    public ResponseEntity findById(Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Comment with id = " + id + " not found");
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CommentDTO(comment));
        }
    }
}
