package snek;

import javax.swing.*;;

public class App {
    public static void main(String[] args) {

        int heigth = 1200;
        int width = 1200;

        // creating window
        JFrame frame = new JFrame("Snek");
        frame.setVisible(true);
        frame.setSize(heigth, width);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Snake snake = new Snake(width, heigth);
        frame.add(snake);
        frame.pack();
        snake.requestFocus();
    }
}