package ru.academit.ilnitsky.shapes;

import ru.academit.ilnitsky.shapes.Shape;

/**
 * Created by Mike on 10.10.2016.
 * Класс "Квадрат"
 */
public class Square implements Shape {
    private double edge;

    public Square(double edge) {
        this.edge = edge;
    }

    public Square(Shape shape) {
        set(shape);
    }

    public Square() {
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
        return edge * 4;
    }

    public void set(Shape shape) {
        this.edge = Math.max(shape.getHeight(), shape.getWidth());
    }

    public void setEdge(double edge) {
        this.edge = edge;
    }

    public void setArea(double area) {
        this.edge = Math.sqrt(area);
    }

    public void setPerimeter(double perimeter) {
        this.edge = perimeter / 4;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + HashCode.hashCode(edge);
        return result;
    }

    public boolean equals(Square square) {
        return edge == square.edge;
    }

    public String toString() {
        return String.format("[Square: W = H = %f]", edge);
    }
}
