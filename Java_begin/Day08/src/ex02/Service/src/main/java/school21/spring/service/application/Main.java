package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.services.UsersService;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        ApplicationContext context = new AnnotationConfigApplicationContext
                (applicationConfig.getClass());
        UsersService usersService = context.getBean("usersServiceImpl",
                                                    UsersService.class);
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        User[] users = new User[5];
        UUID password;
        for (int i = 0; i < 5; i++) {
            password = UUID.randomUUID();
            users[i] = new User();
            users[i].setIdentifier(Long.valueOf((long) i));
            users[i].setEmail(i + "@mail.ru");
            users[i].setPassword(password);
            usersRepository.save(users[i]);
        }
        System.out.println("password " + usersService.signUp("userservice" +
                                                                     "@yandex" +
                                                               ".ru"));
        users[2].setEmail("update@mail.ru");
        usersRepository.delete(4L);
        usersRepository.update(users[2]);
        System.out.println(usersRepository.findByEmail("3@mail.ru"));
        System.out.println(usersRepository.findById(6L));
        System.out.println(usersRepository.findAll());
        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        System.out.println(usersRepository.findByEmail("3@mail.ru"));
        password = UUID.randomUUID();
        User six = new User(6L, "six@mail.ru", password);
        usersRepository.save(six);
        six.setEmail("seven@mail.ru");
        usersRepository.update(six);
        usersRepository.delete(1L);
        System.out.println(usersRepository.findById(6L));
        System.out.println(usersRepository.findAll());
    }
}
