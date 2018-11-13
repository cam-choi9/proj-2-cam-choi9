package cs2113.zombies;

import java.awt.*;

import cs2113.util.Helper;


public class Human {
    private int x;                                                  // x coordinate
    private int y;                                                  // y coordinate
    private int direction;                                          // where to move


    public Human (int x, int y) {
        this.x = x;
        this.y = y;

        direction = Helper.nextInt(4);                        // random number from 0 to 3 (0 = up, 1 = right, 2 = down, 3 = left)
    }

    public void draw() {
        ZombieSim.dp.setPenColor(Color.white);                      // set color to white
        ZombieSim.dp.drawDot(x, y);                                 // draw a dot (human)
    }

    public void move(boolean walls[][], int w, int h) {

        Double random = Helper.nextDouble();
        if (random < 0.1) {
            direction = Helper.nextInt(4);                 // change direction with 10% chance
        }

        if (direction == 0) {                                   // if 0, move upward
            if (((y + 1) >= h) || (walls[x][y + 1])) {
                setDirection();                                 // move direction if it goes out of boundary or hits the wall
            }
            else
                y++;                                            // else, keep moving to the same direction
        }

        else if (direction == 1) {                              // if 1, move right
            if (((x + 1) >= w) || (walls[x + 1][y])) {
                setDirection();                                 // move direction if it goes out of boundary or hits the wall
            }
            else
                x++;
        }

        else if (direction == 2) {                              // move down
            if (((y - 1) <= 0) || (walls[x][y - 1])) {
                setDirection();                                 // move direction if it goes out of boundary or hits the wall
            }
            else
                y--;                                            // else, keep moving to the same direction
        }

        else if (direction == 3) {                              // move left
            if (((x - 1) <= 0) || (walls[x - 1][y])) {
                setDirection();                                 // move direction if it goes out of boundary or hits the wall
            }
            else
                x--;                                            // else, keep moving to the same direction
        }

        else
            setDirection();                                     // change direction
    }

    public void setDirection() {
        direction = Helper.nextInt(4);                    // generate a new random number from 0 to 1
    }

}
