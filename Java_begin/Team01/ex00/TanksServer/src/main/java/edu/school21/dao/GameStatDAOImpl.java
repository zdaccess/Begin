package edu.school21.dao;

import edu.school21.entity.GameStat;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
public class GameStatDAOImpl implements GameStatDAO {
    private final JdbcTemplate template;

    public GameStatDAOImpl(DataSource source) {
        this.template = new JdbcTemplate(source);
        init();
    }

    private void init() {
        template.execute("CREATE SCHEMA IF NOT EXISTS server_tanks;");
        template.execute("DROP TABLE IF EXISTS server_tanks.game_stat;");
        template.execute("CREATE TABLE IF NOT EXISTS server_tanks.game_stat (num_client INT, shot INT, hit INT);");
    }

    @Override
    @Transactional
    public void save(GameStat entity) {
        var checkQuery = "SELECT COUNT(*) FROM server_tanks.game_stat WHERE num_client = ?";
        Integer count = template.queryForObject(checkQuery, Integer.class, entity.getNumberClient());

        if (count != null && count > 0) {
            var updateQuery = "UPDATE server_tanks.game_stat SET shot = ?, hit = ? WHERE num_client = ?";
            int updated = template.update(updateQuery, entity.getShot(), entity.getHit(), entity.getNumberClient());

            if (updated > 0) {
                System.err.printf("Failed to update client %s%n", entity.getNumberClient());
            }
        } else {
            var insertQuery = "INSERT INTO server_tanks.game_stat (num_client, shot, hit) VALUES (?, ?, ?)";
            int inserted = template.update(insertQuery, entity.getNumberClient(), entity.getShot(), entity.getHit());

            if (inserted == 0) {
                System.err.printf("Failed to insert client %s%n", entity.getNumberClient());
            }
        }
    }

    @Override
    @Transactional
    public GameStat findByNumberClient(int numberClient) {
        var query = "SELECT * FROM server_tanks.game_stat WHERE num_client = ?";
        return template.query(query, new BeanPropertyRowMapper<>(GameStat.class), numberClient)
                .stream()
                .findFirst()
                .orElse(null);
    }
}
