package edu.school21.sockets;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findByName(String name);
}

