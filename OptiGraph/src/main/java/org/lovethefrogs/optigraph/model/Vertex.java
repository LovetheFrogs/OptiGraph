package org.lovethefrogs.optigraph.model;

public class Vertex {
    private Node origin;
    private Node destination;
    private double length;

    public Vertex(Node origin, Node destination, double length) {
        this.origin = origin;
        this.destination = destination;
        this.length = length;
    }

    public Node getOrigin() {
        return origin;
    }

    public void setOrigin(Node origin) {
        this.origin = origin;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
