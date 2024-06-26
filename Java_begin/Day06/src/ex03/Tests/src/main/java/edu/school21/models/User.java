package edu.school21.numbers;

import java.util.Objects;

public class User {
    private Long    identifier;
    private String  login;
    private String  password;
    private boolean authStatus;

    public User(Long identifier, String login, String password,
                boolean status) {
        this.identifier = identifier;
        this.login = login;
        this.password = password;
        this.authStatus = status;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthStatus(boolean authStatus) {
        this.authStatus = authStatus;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAuthStatus() {
        return authStatus;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;

        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }

        User data = (User) object;

        return (identifier.equals(data.identifier) && login.equals(data.login)
                && password.equals(data.password)
                && authStatus == data.authStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, login, password, authStatus);
    }

    @Override
    public String toString() {
        return "Product: [identifier=" + identifier
                + ", name=" + login
                + ", price=" + password
                + ", price=" + authStatus
                + "]";
    }
}
