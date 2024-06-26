package edu.school21.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room {
    private Long                id;
    private String              name;
    private User                owner;
    private final List<Message> messages;

    public Room() {
        messages = new ArrayList<>();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;

        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }

        Room data = (Room) object;

        return (id.equals(data.id) &&  name.equals(data.name) && owner.equals(data.owner) && Objects.equals(messages, data.messages));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, messages);
    }

    @Override
    public String toString() {
        return "Room: [id=" + id
                + ", name=" + name
                + ", owner=" + owner
                + ", messages=" + messages
                + "]";
    }
}

