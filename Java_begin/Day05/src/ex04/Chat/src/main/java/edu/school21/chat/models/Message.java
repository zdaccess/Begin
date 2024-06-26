package edu.school21.chat;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long            id;
    private User            author;
    private Room            room;
    private String          text;
    private LocalDateTime   dateTime;

    public Message () {
        id = null;
        author = null;
        room = null;
        text = null;
        dateTime = null;
    }

    public Message (Long id, User author, Room room, String text,
                    LocalDateTime dateTime) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.dateTime = dateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Room getRoom() {
        return room;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;

        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }

        Message data = (Message) object;

        return (id.equals(data.id) && author.equals(data.author)
                && room.equals(data.room) && text.equals(data.text)
                && dateTime.equals(data.dateTime));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text,dateTime);
    }

    @Override
    public String toString() {
        return "Message: [id=" + id
                + ",author=" + author
                + ",room=" + room
                + ",text=" + text
                + ",dateTime=" + dateTime
                + "]";
    }
}
