package ListManager;


import java.util.ArrayList;
import java.util.List;

abstract class Shape {
    abstract double area();

    public String getShape() {
        return getClass().getSimpleName() + ", Area: " + area();
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    double area() {
        return width * height;
    }
}

class Square extends Rectangle {
    public Square(double side) {
        super(side, side);
    }
}

public class ShapeDemo {
    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Circle(5.0));
        shapes.add(new Rectangle(4.0, 6.0));
        shapes.add(new Square(4.0));

        for (Shape s : shapes) {
            System.out.println(s.getShape());
        }
    }
}
