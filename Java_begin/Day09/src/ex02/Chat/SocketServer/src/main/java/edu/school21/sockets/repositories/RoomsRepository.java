package edu.school21.sockets;

import java.util.Optional;

public interface RoomsRepository extends CrudRepository<Room> {
    Optional<Room> findByName(String name);
}