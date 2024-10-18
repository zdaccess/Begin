package edu.school21.sockets;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("roomsRepositoryJdbcTemplate")
public class RoomsRepositoryImpl implements RoomsRepository {
    private JdbcTemplate jdbcTemplate;
    private List<Room> Rooms;

    @Autowired
    public RoomsRepositoryImpl(@Qualifier("dataSourceHikari")
                                   DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Room> findById(Long id) {
        int countRoom = jdbcTemplate.queryForObject("SELECT count(*) " +
                "FROM rooms WHERE id = ?", new Object[]{id}, Integer.class);
        Room room;
        if (countRoom == 0)
            return Optional.empty();
        else {
            room = jdbcTemplate.queryForObject("SELECT id, name FROM " +
                    "rooms WHERE id = ?", (resultSet, rowNum) -> {
                Room newRoom = new Room();
                newRoom.setId(resultSet.getLong("id"));
                newRoom.setName(resultSet.getString("name"));
                return newRoom;
            }, id);
        }
        return Optional.of(room);
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = this.jdbcTemplate.query(
                "SELECT id, name, password FROM users",
                (resultSet, rowNum) -> {
                    Room newroom = new Room();
                    newroom.setId(resultSet.getLong("id"));
                    newroom.setName(resultSet.getString("name"));
                    return newroom;
                });
        return rooms;
    }

    @Override
    public void save(Room entity) {
        if (!entity.getName().isEmpty()) {
            this.jdbcTemplate.update("INSERT INTO rooms(name) VALUES (?)",
                    entity.getName());
        }
    }

    @Override
    public void update(Room entity) {
        if (!entity.getName().isEmpty()
        && !entity.getId().toString().isEmpty()) {
            this.jdbcTemplate.update("UPDATE rooms SET name = ? WHERE id = ?",
                    entity.getName(), entity.getId());
        }
    }

    @Override
    public void delete(Long id) {
        this.jdbcTemplate.update(
                "DELETE FROM rooms WHERE id = ?", id);
    }

    @Override
    public Optional<Room> findByName(String name) {
        int countRoom = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM rooms WHERE name = ?",
                new Object[] {name}, Integer.class
        );
        if (countRoom == 0)
            return Optional.empty();
        Room Room = jdbcTemplate.queryForObject(
                "SELECT id, name FROM rooms WHERE name = ?",
                (resultSet, rowNum) -> {
                    Room newRoom = new Room();
                    newRoom.setId(resultSet.getLong("id"));
                    newRoom.setName(resultSet.getString("name"));
                    return newRoom;
                }, name
        );
        return Optional.of(Room);
    }
}
