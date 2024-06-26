package edu.school21.chat;

import java.util.Optional;

public interface MessagesRepository {
    Optional<Message> findById(Long id);
    void save (Message message);
    void update(Message message);
}

