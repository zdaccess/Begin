package edu.school21.chat;

import java.util.List;
import java.util.Optional;

public interface MessagesRepository {
    Optional<Message> findById(Long id);
}