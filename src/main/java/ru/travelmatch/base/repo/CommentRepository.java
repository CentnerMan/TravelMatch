package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
