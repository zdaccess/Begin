package edu.school21.sockets;

public class Room {
    private static Long counter = 0L;
    private Long id;
    private String name;

    public Room() {}

    public Room(String name) {
        this.name = name;
        addIdentifier();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void addIdentifier() {
        counter++;
    }

    @Override
    public String toString() {
        return "Room: [id=" + id
                + ", name=" + name
                + "]";
    }

}
