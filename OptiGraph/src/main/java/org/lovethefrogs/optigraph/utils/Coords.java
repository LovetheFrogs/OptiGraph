package org.lovethefrogs.optigraph.utils;

public class Coords {
    private int x;
    private int y;

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double distance(Coords obj) {
        Coords aux = new Coords(obj.getX() - this.x, obj.getY() - this.y);
        return Math.sqrt((aux.getX() * aux.getX()) + (aux.getY()) * aux.getY());
    }
}
