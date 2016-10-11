package ru.academit.ilnitsky.shapes;

/**
 * Created by Mike on 10.10.2016.
 * Класс "Квадрат"
 */
public class Square implements Shape {
    private double edge;
    private static final int NUM_EDGES = 4;

    public Square(double edge) {
        this.edge = edge;
    }

    public double getWidth() {
        return edge;
    }

    public double getHeight() {
        return edge;
    }

    public double getArea() {
        return edge * edge;
    }

    public double getPerimeter() {
        return edge * NUM_EDGES;
    }

    public void clone(Square square) {
        this.edge = Math.max(square.getHeight(), square.getWidth());
    }

    public void setEdge(double edge) {
        this.edge = edge;
    }

    public void setArea(double area) {
        this.edge = Math.sqrt(area);
    }

    public void setPerimeter(double perimeter) {
        this.edge = perimeter / NUM_EDGES;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + HashCode.hashCode(edge);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        } else if (this == object) {
            return true;
        } else if (this.getClass() == object.getClass()) {
            Square other = (Square) object;
            return edge == other.edge;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("[Square: W = H = %f]", edge);
    }
}
