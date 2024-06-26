package edu.school21.chat;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

public class User {
    private Long                id;
    private String              login;
    private String              password;
    private List<Room>          chatRooms;
    private List<Room>          socializesChatRooms;

    public User() {
        chatRooms = new ArrayList<>();
        socializesChatRooms = new ArrayList<>();
    }

    public User (Long id, String login, String password,
                 List<Room> chatRooms, List<Room> socializesChatRooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.chatRooms = chatRooms;
        this.socializesChatRooms = socializesChatRooms;
    }

    public User(List<Room> chatRooms, List<Room> socializesChatRooms) {
        this.chatRooms = chatRooms;
        this.socializesChatRooms = socializesChatRooms;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Room> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(List<Room> chatRooms) {
        this.chatRooms = chatRooms;
    }

    public void setSocializesChatRooms(List<Room> socializesChatRooms) {
        this.socializesChatRooms = socializesChatRooms;
    }

    public List<Room> getSocializesChatRooms() {
        return socializesChatRooms;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;

        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }

        User data = (User) object;

        return (id.equals(data.id) &&  login.equals(data.login)
                && password.equals(data.password)
                && Objects.equals(chatRooms, data.chatRooms)
                && Objects.equals(socializesChatRooms, data.socializesChatRooms));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, chatRooms,
                            socializesChatRooms);
    }

    @Override
    public String toString() {
        return "User: [id=" + id
                + ",login=" + login
                + ",password=" + password
                + ",chatRooms=" + chatRooms
                + ",socializesChatRooms=" + socializesChatRooms
                + "]";
    }
}
