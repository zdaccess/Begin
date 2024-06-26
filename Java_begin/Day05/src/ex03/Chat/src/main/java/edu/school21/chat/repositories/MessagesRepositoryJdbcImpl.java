package edu.school21.chat;

import javax.sql.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource  dataSource;
    private Message     message;
    private User        user;
    private Room        room;

    public MessagesRepositoryJdbcImpl (DataSource dataSource) {
        this.dataSource = dataSource;
        this.message = new Message();
        this.user = new User();
        this.room = new Room();
    }
    public MessagesRepositoryJdbcImpl (DataSource dataSource, User user,
                                       Message message, Room room) {
        this.dataSource = dataSource;
        this.message = message;
        this.user = user;
        this.room = room;
    }

    public void update (Message message) {
        String command = "UPDATE chat.message SET text = '"
                        + message.getText() + "', textdatetime = "
                        + message.getDateTime() + " WHERE "
                        + "id = " + message.getId();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preStatement = connection.prepareStatement(command);
            preStatement.executeUpdate();
            preStatement.close();
        } catch (SQLException e) {
            System.out.println("Connection failed! " + e.getMessage());
        }
    }

    public void save (Message message) {
        String command = "SELECT CONCAT(id ,',', name)\n" +
                "FROM chat.room\n" + "WHERE id = " + message.getRoom().getId();
        String str =
                message.getRoom().getId() + "," + message.getRoom().getName();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSetFirst = statement.executeQuery(command);
            while (resultSetFirst.next()) {
                if (!str.equals(resultSetFirst.getString(1))) {
                    throw new NotSavedSubEntityException("Room");
                }
            }
            resultSetFirst.close();
            command = "SELECT CONCAT(id ,',', login)\n" + "FROM chat.users\n" +
                    "WHERE id = " + message.getRoom().getOwner().getId();
            str = message.getRoom().getOwner().getId() + ","
                    + message.getRoom().getOwner().getLogin();
            ResultSet resultSetSecond = statement.executeQuery(command);
            while (resultSetSecond.next()) {
                if (!str.equals(resultSetSecond.getString(1))) {
                    throw new NotSavedSubEntityException("User");
                }
            }
            resultSetSecond.close();
            String addMessageCommand = "INSERT INTO chat.message(author, " +
                    "room, text, textdatetime) VALUES (?, ?, ?, ?)";

            PreparedStatement resultSetThird =
                    connection.prepareStatement(addMessageCommand);
            resultSetThird.setLong(1, message.getRoom().getOwner().getId());
            resultSetThird.setLong(2, message.getRoom().getId());
            resultSetThird.setString(3, message.getText());
            resultSetThird.setTimestamp(4,
                                        Timestamp.valueOf(message.getDateTime()));

            resultSetThird.executeUpdate();
            resultSetThird.close();
        } catch (SQLException e) {
            System.out.println("Connection failed! " + e.getMessage());
        }
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
