package school21.spring.service.models;

import java.util.UUID;

public class User {
    private Long    identifier;
    private String  email;
    private UUID    password;

    public User() {
    }

    public User(Long identifier, String email, UUID password) {
        this.identifier = identifier;
        this.email = email;
        this.password = password;
    }

    public UUID getPassword() {
        return password;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public String getEmail() {
        return email;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(UUID password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User: [id=" + identifier
                + ", email=" + email
                + ", password=" + password
                + "]";
    }
}
