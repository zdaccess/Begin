package edu.school21.sockets;

import java.time.LocalDateTime;

public class Message {
    private static          Long counter = 0L;
    private Long            id;
    private Long            chat;
    private Long            sender;
    private String          text;
    private LocalDateTime   dateTime;

    public Message() {}

    public Message(Long chat, Long sender, String text, LocalDateTime dateTime) {
        this.chat = chat;
        this.sender = sender;
        this.text = text;
        this.dateTime = dateTime;
        addIdentifier();
    }

    public Long getChat() {
        return chat;
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

    public void setChat(Long chat) {
        this.chat = chat;
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

    public static void addIdentifier() {
        counter++;
    }

    @Override
    public String toString() {
        return "Message: [chat=" + chat
                + "sender=" + sender
                + ", text=" + text
                + ", dateTime=" + dateTime
                + "]";
    }
}
