package edu.school21.sockets;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component ("messagesRepositoryJdbcTemplate")
public class MessagesRepositoryImpl {
    private JdbcTemplate jdbcTemplate;
    private List<String> messages;

    @Autowired
    public MessagesRepositoryImpl(@Qualifier ("dataSourceHikari")
                                      DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<String> findMessagesRoom(Long chat, Long userId) {
        int countUser = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM messages WHERE chat = ?",
                new Object[] {chat}, Integer.class
        );
        if (countUser == 0)
            return new ArrayList<>();
        messages = this.jdbcTemplate.query("SELECT CONCAT " +
                "(CASE WHEN users.id = ? THEN 'You' ELSE users.name END, " +
                "': ' ,msg.message)\n" + "FROM messages AS msg\n" + "LEFT " +
                "JOIN users ON msg.sender = users.id \n" + " " +
                "WHERE chat=? ORDER BY " +
                "msg.id DESC LIMIT 30", (resultSet, rowNum) -> {
            String str;
            str = resultSet.getString("concat");
            return str;
        }, userId, chat);
        for (String tu : messages) {

        }
        Collections.reverse(messages);
        return messages;
    }

    public void save(Message entity) {
        if (!entity.getChat().toString().isEmpty()
        && !entity.getSender().toString().isEmpty()
         && !entity.getText().isEmpty()) {
            jdbcTemplate.update("INSERT INTO messages(chat, sender, message,"
                    + " time) VALUES (?, ?, ?, ?)", entity.getChat(),
                    entity.getSender(), entity.getText(),
                    Timestamp.valueOf(entity.getDateTime()));
        }
    }
}

