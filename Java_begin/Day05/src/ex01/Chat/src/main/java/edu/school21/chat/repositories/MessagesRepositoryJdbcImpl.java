package edu.school21.chat;

import javax.sql.*;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource  dataSource;
    private final Message     message;
    private final User        user;
    private final Room        room;

    public MessagesRepositoryJdbcImpl (DataSource dataSource) {
        this.dataSource = dataSource;
        this.message = new Message();
        this.user = new User();
        this.room = new Room();
    }

    public Optional<Message> findById(Long id) {
        String command = "SELECT mes.id, foo, oof, mes.text, mes.textdatetime" +
                        " FROM chat.message AS mes\n JOIN (SELECT * FROM " +
                        "chat.users) AS foo ON mes.author = foo.id\n" +
                        "JOIN (SELECT id, name, 'null' FROM chat.room) " +
                        "AS oof ON mes.room = oof.id\n" +
                        "WHERE mes.id = " + id;
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSetFirst = statement.executeQuery(command);
            String[] splitUser = null;
            String[] splitRoom = null;
            long authorId = 0;
            long roomId = 0;
            while (resultSetFirst.next()) {
                try {
                    message.setId(Long.valueOf(resultSetFirst.getString(1)));
                    splitUser = resultSetFirst.getString(2).split(",");
                    user.setId(Long.valueOf(splitUser[0].trim().replace("(","")));
                    user.setLogin(splitUser[1]);
                    user.setPassword(splitUser[2].trim().replace(")",""));
                    splitRoom = resultSetFirst.getString(3).split(",");
                    room.setId(Long.valueOf(splitRoom[0].trim().replace("(","")));
                    room.setName(splitRoom[1]);
                    if (splitRoom[2].trim().replace(")","") == null)
                        room.setOwner(null);
                    message.setText(resultSetFirst.getString(4));
                    if (resultSetFirst.getString(5) == null) {
                        message.setDateTime(null);
                    } else {
                        message.setDateTime(Timestamp.valueOf(resultSetFirst.
                                getString(5)).toLocalDateTime());
                    }
                } catch (NullPointerException e) {}
            }
            message.setAuthor(user);
            message.setRoom(room);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(message);
    }
}
