package edu.school21.sockets;

public class User {
    private static Long counter = 0L;
    private Long        identifier;
    private String      userName;
    private String      password;

    public User() {
    }

    public User(Long identifier, String email, String password) {
        this.identifier = identifier;
        this.userName = email;
        this.password = password;
        addIdentifier();
    }

    public String getPassword() {
        return password;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public String getUserName() {
        return userName;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void addIdentifier() {
        counter++;
    }

    @Override
    public String toString() {
        return "User: [id=" + identifier
                + ", userName=" + userName
                + ", password=" + password
                + "]";
    }
}

