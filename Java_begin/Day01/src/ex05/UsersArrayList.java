package src.ex05;

public class UsersArrayList implements UsersList {

    private User[]  list;
    private Integer size = 0;
    private Integer DEFAULT_WIDTH = 10;
    private Integer chooseSize = 0;

    public UsersArrayList(Integer width) {
        if (width <= 0) {
            throw new IllegalArgumentException("Size less than 0");
        }
        else {
            list = new User[width];
            chooseSize = width;
        }
    }

    public UsersArrayList() {
        list = new User[DEFAULT_WIDTH];
        chooseSize = DEFAULT_WIDTH;
    }

    public void add(User item) {
        if (size == chooseSize - 1) {
            chooseSize = chooseSize * 2;
            User[] tmpList = new User[chooseSize * 2];
            for (Integer i = 0; i < size + 1; i++) {
                tmpList[i] = list[i];
            }
            list = tmpList;
            list[size++] = item;
        }
        else
            list[size++] = item;
    }

    public Integer userBalance(User name) {
        User find = null;
        Integer i;
        Integer flag = 0;
        for ( i = 0; list[i] != null; i++ ) {
            find = list[i];
            if (find == name) {
                flag++;
                break;
            }
        }
        if (flag == 0)
            System.out.println("Error! There is no such user!");
        return find.getBalance();
    }

    public User getUsersId(Integer id) throws UserNotFoundException {
        User find = null;
        Integer i;
        for (i = 0; i < chooseSize; i++) {
            find = list[i];
            if (find == null)
                throw new UserNotFoundException(id, "id");
            if (find.getId() == id)
                return find;
        }
        return list[i];
    }

    public User getUsersIndex(Integer index) throws UserNotFoundException {
        Integer i = 0;
        Integer flag = 0;
        while (list[i] != null) {
            if (i == index) {
                flag++;
                break;
            }
            i++;
        }
        if (flag == 0)
            throw new UserNotFoundException(index, "index");
        return list[i];
    }

    public Integer getCount() {
        Integer i = 0;
        while (list[i] != null) {
            i++;
        }
        return i;
    }
}
