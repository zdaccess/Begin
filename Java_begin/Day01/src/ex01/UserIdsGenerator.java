package src.ex01;

public class UserIdsGenerator {

    private static int              id = 0;
    private static UserIdsGenerator instance;

    private UserIdsGenerator() {};

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public int generateId() {
        return (intgenerId());
    }

    public int intgenerId() {
        id++;
        return id;
    }
}
