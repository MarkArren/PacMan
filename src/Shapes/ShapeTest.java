package Shapes;

// import com.sun.javaws.util.JfxHelper;

import javax.swing.*;
import java.awt.*;

public class ShapeTest extends JFrame {

    public static void main(String[] args) {
        ShapeTest shapeTest = new ShapeTest("Test Shapes");

    }
    public ShapeTest(String title){
        super(title);

        // Creating all the shapes to test them
        CircleSector pieCircle =  new CircleSector(0,0,40,210,300,0);
        Circle circle = new Circle(50,130,40,0);
        Square square = new Square(50,210, 80, 30);
        Square square2 = new Square(50,290, 80, 0);
        Rectangle rectangle = new Rectangle(50, 370,80, 20, 0);
        Rectangle rectangle2 = new Rectangle(50, 390,80, 20, 20);
        Triangle triangle = new Triangle(50, 410, 80,0);
        Triangle triangle2 = new Triangle(50, 490, 80,-20);


        CircleSector pieCircle1 =  new CircleSector(150,0,40,210,300,0);
        CircleSector pieCircle2 =  new CircleSector(250,0,40,225,270,0);
        CircleSector pieCircle3 =  new CircleSector(350,0,40,270,180,0);

        // Adding a panel to the frame and adding the shapes paint component to it
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                pieCircle.paintComponent(g);
                circle.paintComponent(g);
                square.paintComponent(g);
                square2.paintComponent(g);
                rectangle.paintComponent(g);
                rectangle2.paintComponent(g);
                triangle.paintComponent(g);
                triangle2.paintComponent(g);

                pieCircle1.paintComponent(g);
                pieCircle2.paintComponent(g);
                pieCircle3.paintComponent(g);

                g.drawRect(0,0,80,80);
            }
        };
        add(panel);
        setPreferredSize(new Dimension(800,700));
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
