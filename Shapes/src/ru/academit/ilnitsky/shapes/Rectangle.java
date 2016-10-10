package ru.academit.ilnitsky.shapes;

/**
 * Created by Mike on 10.10.2016.
 * Класс "Прямоугольник"
 */
public class Rectangle implements Shape {
    private double height;
    private double width;

    public Rectangle(double width, double height) {
        set(width, height);
    }

    public Rectangle(double edge) {
        set(edge, edge);
    }

    public Rectangle(Shape shape) {
        set(shape);
    }

    public Rectangle() {
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getArea() {
        return width * height;
    }

    public double getPerimeter() {
        return (width + height) * 2;
    }

    public void set(double width, double height) {
        this.height = height;
        this.width = width;
    }

    public void set(Shape shape) {
        set(shape.getWidth(), shape.getHeight());
    }

    public boolean equals(Rectangle rectangle) {
        return ((height == rectangle.height) && (width == rectangle.width))
                || ((height == rectangle.width) && (width == rectangle.height));
    }

    public String toString() {
        return String.format("[Rectangle: W = %f, H = %f]", width, height);
    }
}
