package org.lovethefrogs.optigraph.utils;

import java.io.Serializable;

public class Coords implements Serializable {
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
        return Math.abs(this.x - obj.getX()) + Math.abs(this.y - obj.getY());
    }
}
