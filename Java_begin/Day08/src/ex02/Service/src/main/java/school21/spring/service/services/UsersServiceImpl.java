package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import java.util.UUID;
@Component("usersServiceImpl")
public class UsersServiceImpl implements UsersService{
    private UsersRepository usersRepository;
    private List<User> users;

    public UsersServiceImpl(@Qualifier("jdbcTemplateRepository")UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) {
        if (email == null || email.isEmpty())
            return null;
        users = usersRepository.findAll();
        UUID password = UUID.randomUUID();
        User user = new User();
        user.setIdentifier(users.get(users.size() - 1).getIdentifier() + 1);
        user.setPassword(password);
        user.setEmail(email);
        usersRepository.save(user);
        return String.valueOf(password);
    }
}
