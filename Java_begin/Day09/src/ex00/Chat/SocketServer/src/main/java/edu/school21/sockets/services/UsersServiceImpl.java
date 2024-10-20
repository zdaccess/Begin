package edu.school21.sockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("usersServiceImpl")
public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepositoryImpl;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryJdbcTemplate")
                            UsersRepositoryImpl usersRepository,
                            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepositoryImpl = usersRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String signUp(String name, String password) {
        if (name == null || name.isEmpty() || password == null
        || password.isEmpty())
            return null;
        User user = new User();
        String encodePassword = passwordEncoder.encode(password);
        user.setPassword(encodePassword);
        user.setUserName(name);
        usersRepositoryImpl.save(user);
        return encodePassword;
    }
}
