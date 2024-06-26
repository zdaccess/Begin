package src.ex02;

public class Program {
    public static void main(String[] args) throws UserNotFoundException {
        UsersList users = new UsersArrayList();
        User Damir = new User("Damir", 800);
        User Erik = new User("Erik", 800);
        User Almaz = new User("Almaz", 700);
        User Damir1 = new User("Damir1", 800);
        User Erik1 = new User("Erik1", 800);
        User Almaz1 = new User("Almaz1", 700);
        User Damir2 = new User("Damir2", 800);
        User Erik2 = new User("Erik2", 800);
        User Almaz2 = new User("Almaz2", 700);
        User Damir3 = new User("Damir3", 800);
        User Erik3 = new User("Erik3", 800);
        User Almaz3 = new User("Almaz3", 700);

        users.add(Damir);
        users.add(Erik);
        users.add(Almaz);
        users.add(Damir1);
        users.add(Erik1);
        users.add(Almaz1);
        users.add(Damir2);
        users.add(Erik2);
        users.add(Almaz2);
        users.add(Damir3);
        users.add(Erik3);
        users.add(Almaz3);

        System.out.println("Найден пользователь под index - 1: " + users.getUsersIndex(1));
        System.out.println("Найден пользователь под id - 1: " + users.getUsersId(1));
        System.out.println("Найден пользователь под id - 2: " + users.getUsersId(2));
        System.out.println("Найден пользователь по индексу - 3: " + users.getUsersIndex(3));
        System.out.println("Найден пользователь по индексу - 10: " + users.getUsersIndex(10));
        System.out.println("Общее количество пользователей: " + users.getCountUsers());
    }
}
