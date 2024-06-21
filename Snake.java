package snek;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;
import javax.swing.*;

public class Snake extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    // board
    int width;
    int heigth;
    int tileSize = 50;

    // food
    Tile food;
    Random random;

    // gamelogic
    Timer loopTimer;
    int speedY; // up or downd
    int speedX; // left or rigth
    boolean gameOver = false;

    Snake(int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
        setPreferredSize(new Dimension(this.width, this.heigth));
        setBackground(Color.GRAY);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        speedX = 0;
        speedY = 0;

        loopTimer = new Timer(100, this);
        loopTimer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        // snake head
        g.setColor(Color.MAGENTA);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // snake body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        // food
        g.setColor(Color.RED);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        // score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.MAGENTA);
            g.drawString("Game over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        } else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }

    }

    public void placeFood() {
        food.x = random.nextInt(width / tileSize);
        food.y = random.nextInt(heigth / tileSize);
    }

    public boolean collidingWithFood(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            loopTimer.stop();
        }
    }

    public void move() {

        // eating
        if (collidingWithFood(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // body moving
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile previousPart = snakeBody.get(i - 1);
                snakePart.x = previousPart.x;
                snakePart.y = previousPart.y;
            }
        }

        snakeHead.x += speedX;
        snakeHead.y += speedY;

        // check if game over
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (snakePart.x == snakeHead.x && snakePart.y == snakeHead.y) {
                gameOver = true;
            }

        }
        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > width || snakeHead.y * tileSize > heigth
                || snakeHead.y * tileSize < 0) {
            gameOver = true;
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && speedY != 1) {
            speedY = -1;
            speedX = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && speedY != -1) {
            speedY = 1;
            speedX = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && speedX != -1) {
            speedY = 0;
            speedX = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && speedX != 1) {
            speedY = 0;
            speedX = -1;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
