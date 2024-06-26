package edu.school21.chaselogic.coordinates;

public class Coordinates {
    protected Integer x = 0;
    protected Integer y = 0;

    public Coordinates(Coordinates other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setCoordinates(Coordinates coordinates) {
        x = coordinates.x;
        y = coordinates.y;
    }

    public Coordinates getCoordinates() {
        return this;
    }

}
