package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;

public class UsersServiceImplTest {
    private User                user;
    private UsersRepository     usersRepositoryMock;
    private UsersServiceImpl    usersServiceImpl;

    @BeforeEach
    void init() {
        user = new User(1L, "loginFirst", "passFirst", false);
        usersRepositoryMock = Mockito.mock(UsersRepository.class);
        usersServiceImpl = new UsersServiceImpl(usersRepositoryMock);
        Mockito.when(usersRepositoryMock.findByLogin("loginFirst")).
                thenReturn(user);
    }

    @Test
    void checkCorrectLoginPassword()
            throws AlreadyAuthenticatedException, EntityNotFoundException {
        usersRepositoryMock.update(user);
        Mockito.verify(usersRepositoryMock).update(user);
        Assertions.assertTrue(
                usersServiceImpl.authenticate("loginFirst", "passFirst")
        );
    }

    @Test
    void checkIncorrectLogin() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            usersServiceImpl.authenticate("login", "passFirst");
        });
    }

    @Test
    void checkIncorrectPassword() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            usersServiceImpl.authenticate("login", "passFirst");
        });
    }

}
