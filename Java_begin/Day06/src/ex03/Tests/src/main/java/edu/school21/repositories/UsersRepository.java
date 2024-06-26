package edu.school21.numbers;

public interface UsersRepository {
    User findByLogin(String login);
    void update(User user);
}
