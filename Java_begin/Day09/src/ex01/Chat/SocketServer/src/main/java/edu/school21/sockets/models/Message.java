package edu.school21.sockets.src.main.java.edu.school21.sockets.models;

import java.time.LocalDateTime;

public class Message {
    private Long  sender;
    private String  text;
    private LocalDateTime dateTime;

    public Message(Long sender, String text, LocalDateTime dateTime) {
        this.sender = sender;
        this.text = text;
        this.dateTime = dateTime;
    }

    public Long getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Room: [sender=" + sender
                + ", text=" + text
                + ", dateTime=" + dateTime
                + "]";
    }
}
