package org.lovethefrogs.optigraph.model;

import org.lovethefrogs.optigraph.utils.Coords;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Node implements Serializable {
    private final int id;
    private String name;
    private Coords coords;
    private boolean center;

    private ArrayList<Double> dist;

    public Node(int id, String name, Coords coords, boolean center) {
        this.id = id;
        this.name = name;
        this.coords = coords;
        this.center = center;
        this.dist = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public boolean isCenter() {
        return center;
    }

    public void setCenter(boolean center) {
        this.center = center;
    }

    public ArrayList<Double> getDist() {
        return dist;
    }

    public void setDist(ArrayList<Double> dist) {
        this.dist = dist;
    }
}
