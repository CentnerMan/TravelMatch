package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.MessageObject;

public interface MessageObjectRepository extends JpaRepository<MessageObject,Long> {
}
