package edu.school21.chat;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository{
    private final DataSource    dataSource;
    private User[]              users;
    private int                 ownersCount;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        ownersCount = 0;
    }

    public List<User> findAll(int page, int size) {
        List<User> list = new ArrayList<>();
        int offset = page * size;
        String command = "WITH CTE AS(SELECT chat.message.author, " +
                        "STRING_AGG(DISTINCT rooms2.rooms, ';') AS all_rooms\n"
                        + "FROM chat.message\n" + "LEFT JOIN (SELECT room.id, " +
                        "STRING_AGG(room.id || ',' || name || ',' || ubr || ','" +
                        " || 'null', ';' ) AS rooms FROM chat.room \n"
                        + "JOIN (SELECT * FROM chat.users) AS ubr " +
                        "ON room.owners = ubr.id\n" + "GROUP BY room.id) " +
                        "AS rooms2 ON rooms2.id = chat.message.room\n" +
                        "GROUP BY chat.message.author) \n" +
                        "SELECT chat.users.id, chat.users.login, " +
                        "chat.users.password, rooms1 AS list_created_rooms, " +
                        "CTE.all_rooms AS list_participates_rooms\n" +
                        "FROM chat.users JOIN CTE ON CTE.author = chat.users.id\n"
                        + "LEFT JOIN (SELECT owners, STRING_AGG(room.id || ','" +
                        " || name || ',' || usr || ',' || 'null', ';') " +
                        "AS rooms1 FROM chat.room \n" +
                        "JOIN (SELECT * FROM chat.users) AS usr " +
                        "ON chat.room.owners = usr.id\n" + "GROUP BY owners)\n"
                        + "AS rooms ON rooms.owners = chat.users.id ORDER BY id" +
                        " LIMIT " + size + " OFFSET " + offset;
        List<Message>[] message = new ArrayList[size];
        List<Room>[] roomCreated = new ArrayList[size];
        List<Room>[] roomParticipates = new ArrayList[size];
        Room[] rooms = null;
        String[] cells = null;
        String[] data = null;
        User addUser = null;
        List<User> owners = new ArrayList<>();
        int forward = 0;
        try (Connection connection = dataSource.getConnection()) {
             users = new User[size];
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
            while (resultSet.next()) {
                try {
                    users[forward] = new User();
                    users[forward].setId(resultSet.getLong(1));
                    users[forward].setLogin(resultSet.getString(2));
                    users[forward].setPassword(resultSet.getString(3));
                    cells = resultSet.getString(4).split(";");
                    rooms = new Room[cells.length];
                    roomCreated[forward] = new ArrayList<>();
                    for (int j = 0; j < cells.length; j++) {
                        data = cells[j].split(",");
                        rooms[j] = new Room();
                        rooms[j].setId(Long.parseLong(data[0]));
                        rooms[j].setName(data[1]);
                        addUser =
                                new User(Long.parseLong(data[2].substring(1))
                        , data[3], data[4].substring(0, data[4].length() - 1)
                                         , new ArrayList<>(), new ArrayList<>());
                        owners.add(addUser);
                        rooms[j].setOwner(owners.get(ownersCount));
                        ownersCount++;
                        if (data[5].equals("null"))
                            message = null;
                        else
                            throw new NotSavedSubEntityException("message");
                        roomCreated[forward].add(rooms[j]);
                    }
                    users[forward].setChatRooms(roomCreated[forward]);
                    cells = resultSet.getString(5).split(";");
                    rooms = new Room[cells.length];
                    roomParticipates[forward] = new ArrayList<>();
                    for (int j = 0; j < cells.length; j++) {
                        data = cells[j].split(",");
                        rooms[j] = new Room();
                        rooms[j].setId(Long.parseLong(data[0]));
                        rooms[j].setName(data[1]);
                        addUser = new User(Long.parseLong(data[2].substring(1)),
                                data[3], data[4].substring(0, data[4].length() - 1),
                                new ArrayList<>(), new ArrayList<>());
                        owners.add(addUser);
                        rooms[j].setOwner(owners.get(ownersCount));
                        ownersCount++;
                        if (data[5].equals("null"))
                            message = null;
                        else
                            throw new NotSavedSubEntityException("Message");
                        roomParticipates[forward].add(rooms[j]);
                    }
                    users[forward].setSocializesChatRooms(roomParticipates[forward]);
                    list.add(users[forward]);
                    forward++;
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (SQLException  e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}

















