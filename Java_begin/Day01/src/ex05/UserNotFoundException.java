package src.ex05;

public class UserNotFoundException extends Throwable {

    public UserNotFoundException(Integer id, String str) {
        if (str.equals("id"))
            System.out.println("Error! The user does not exist by ID: " + id);
        else if (str.equals("index"))
            System.out.println("Error! The user does not exist by INDEX: " + id);
    }
}
