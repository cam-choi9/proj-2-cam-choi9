package cs2113.zombies;

import java.awt.*;
import java.util.ArrayList;

import cs2113.util.Helper;


public class Human {
    protected int x;                                                  // x coordinate
    protected int y;                                                  // y coordinate
    protected int direction;                                          // where to move
    protected double chance;

    public Human (int x, int y) {
        this.x = x;
        this.y = y;
        changeDirection();
    }

    public void draw() {
        ZombieSim.dp.setPenColor(Color.white);                      // set color to white
        ZombieSim.dp.drawDot(getX(), getY());                                 // draw a dot (human)
    }

    public void switchPos () {
        x = Helper.nextInt(200);
        y = Helper.nextInt(200);						        // generate other random numbers
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public int getDirection () {
        return direction;
    }

    public void changeDirection() {
        direction = Helper.nextInt(4);
    }

    public void setDirection(int thisDirection) {
        direction = thisDirection;                        // random number from 0 to 3 (0 = up, 1 = right, 2 = down, 3 = left)
    }

    public void setX (int x) {
        this.x = x;
    }

    public void setY (int y) {
        this.y = y;
    }

    public void move(boolean walls[][], int w, int h, double chance) {

        Double random = Helper.nextDouble();
//        int x = getX();
//        int y = getY();

        if (random < chance) {
            changeDirection();                    // change direction with 10% chance
        }

        if (direction == 0) {                                       // if 0, move upward
            if (!inBoundsNotWalls(walls, w, h, x, y+1)) {         // check if it's a valid move
                changeDirection();                                     // move direction if it goes out of boundary or hits the wall
            }
            else
                y++;                                                // else, keep moving to the same direction
        }

        else if (direction == 1) {                                  // if 1, move right
            if (!inBoundsNotWalls(walls, w, h, x+1, y)) {         // check if it's a valid move
                changeDirection();                                     // move direction if it goes out of boundary or hits the wall
            }
            else
                x++;
        }

        else if (direction == 2) {                                  // move down
            if (!inBoundsNotWalls(walls, w, h, x, y-1)) {         // check if it's a valid move
                changeDirection();                                     // move direction if it goes out of boundary or hits the wall
            }
            else
                y--;                                                // else, keep moving to the same direction
        }

        else if (direction == 3) {                                  // move left
            if (!inBoundsNotWalls(walls, w, h, x-1, y)) {         // check if it's a valid move
                changeDirection();                                     // move direction if it goes out of boundary or hits the wall
            }
            else
                x--;                                                // else, keep moving to the same direction
        }

        else
            changeDirection();                                         // change direction
    }

    public boolean inBoundsNotWalls (boolean walls[][], int w, int h, int x, int y) {

        if ((x >= w) ||(x <= 0)) {                                  // if x is out of boundary
            return false;                                           // return false
        }

        else if ((y >= h) ||(y <= 0)) {                             // if y is out of boundary
            return false;                                           // return false
        }

        else if (walls[x][y]) {                                     // if there is a wall
            return false;                                           // return false
        }

        else                                                        // else,
            return true;                                            // return true

    }

    public boolean checkSurrounding (boolean[][] walls, int w, int h, int x, int y) {
        if (!inBoundsNotWalls(walls, w, h, x+1, y)) {
            return false;
        }

        else if (!inBoundsNotWalls(walls, w, h, x-1, y)) {
            return false;
        }

        else if (!inBoundsNotWalls(walls, w, h, x, y+1)) {
            return false;
        }

        else if (!inBoundsNotWalls(walls, w, h, x, y-1)) {
            return false;
        }

        else
            return true;
    }




}
