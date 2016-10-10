package ru.academit.ilnitsky.shapes;

/**
 * Created by Mike on 10.10.2016.
 * Класс "Окружность"
 */
public class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public Circle(Shape shape) {
        set(shape);
    }

    public Circle() {
    }

    public double getWidth() {
        return radius * 2;
    }

    public double getHeight() {
        return radius * 2;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setDiameter(double diameter) {
        this.radius = diameter / 2;
    }

    public void setArea(double area) {
        this.radius = Math.sqrt(area / Math.PI);
    }

    public void setPerimeter(double perimeter) {
        this.radius = perimeter / 2 / Math.PI;
    }

    public void set(Shape shape) {
        this.radius = Math.min(shape.getHeight(), shape.getWidth()) / 2;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + HashCode.hashCode(radius);
        return result;
    }

    public boolean equals(Circle circle) {
        return radius == circle.radius;
    }

    public String toString() {
        return String.format("[Circle: R = %f]", radius);
    }
}
