package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.Message;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
