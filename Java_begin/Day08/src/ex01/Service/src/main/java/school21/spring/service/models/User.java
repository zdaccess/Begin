package school21.spring.service.models;

public class User {
    private Long identifier;
    private String email;

    public User() {
    }

    public User(Long identifier, String email) {
        this.identifier = identifier;
        this.email = email;
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

    @Override
    public String toString() {
        return "User: [id=" + identifier
                + ", email=" + email
                + "]";
    }
}
