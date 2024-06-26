package edu.school21.chat;

import java.util.List;

public interface UsersRepository {
    List<User> findAll(int page, int size);
}
