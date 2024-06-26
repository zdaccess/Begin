package src.ex02;

public interface UsersList {

    void add(User name);

    User getUsersId(Integer id) throws UserNotFoundException;

    User getUsersIndex(Integer index) throws UserNotFoundException;

    Integer getCountUsers();
}
