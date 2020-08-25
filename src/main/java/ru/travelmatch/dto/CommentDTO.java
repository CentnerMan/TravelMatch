package ru.travelmatch.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.travelmatch.base.entities.Comment;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Farida Gareeva
 * Created 25.08.2020
 * v1.0
 */
@Getter
@NoArgsConstructor
public class CommentDTO implements Serializable {

    private static final long serialVersionUID = 1610454185455304647L;

    @Setter
    private Long id;
    private String text;

    @NotNull(message = "Article Id shouldn't be null!")
    private Long articleId;

    @NotNull(message = "User Id shouldn't be null!")
    private Long userId;

    private Long parentId;

    private LocalDateTime created;
    private LocalDateTime lastUpdated;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.userId = comment.getUser().getId();
        this.articleId = comment.getArticle().getId();
        this.parentId = comment.getParentId();
        this.created = comment.getCreated();
        this.lastUpdated = comment.getLastUpdated();
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", articleId=" + articleId +
                ", userId=" + userId +
                ", parentId=" + parentId +
                ", created=" + created +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
