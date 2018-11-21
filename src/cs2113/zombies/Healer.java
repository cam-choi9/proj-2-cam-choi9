package cs2113.zombies;

import java.awt.*;

import cs2113.util.Helper;


public class Healer extends Human {


    public Healer (int x, int y) {
        super (x, y);
        changeDirection();
    }

    public void draw() {
        ZombieSim.dp.setPenColor(Color.green);                      // set color to green
        ZombieSim.dp.drawDot(x, y);                                 // draw a dot (healer)
    }

    public void move(boolean walls[][], int w, int h, boolean[][] zombies) {
        Healing(walls, w, h, zombies);
        move(walls, w, h, 0.1);
    }

    // convert zombies into humans again
    public boolean Healing (boolean[][] walls, int width, int height, boolean[][] zombies) {

        if (direction == 0) {                                                          // facing up
            for (int i=1; i<11; i++) {                                                 // check if a zombie is within 10 squares of its direction
                if (inBoundsNotWalls(walls, width, height, x, y+i)) {
                    if (zombies[x][y+i]) {
                        setDirection(0);                                               // face up
                        y++;                                                           // move up
                        return true;
                    }
                }
            }
        }

        else if (direction == 1) {                                                     // facing down
            for (int i=1; i<11; i++) {                                                 // check if a zombie is within 10 squares of its direction
                if (inBoundsNotWalls(walls, width, height, x+i, y)) {
                    if (zombies[x+i][y]) {
                        setDirection(1);                                               // face down
                        x++;                                                           // move right
                        return true;
                    }
                }
            }
        }

        else if (direction == 2) {                                                     // facing down
            for (int i=1; i<11; i++) {                                                 // check if a zombie is within 10 squares of its direction
                if (inBoundsNotWalls(walls, width, height, x, y-i)) {
                    if (zombies[x][y-i]) {
                        setDirection(2);                                               // face down
                        y--;                                                           // move down
                        return true;
                    }
                }
            }
        }

        else if (direction == 3) {                                                     // facing left
            for (int i=1; i<11; i++) {                                                 // check if a zombie is within 10 squares of its direction
                if (inBoundsNotWalls(walls, width, height, x-i, y)) {
                    if (zombies[x-i][y]) {
                        setDirection(3);                                               // face left
                        x--;                                                           // move left
                        return true;
                    }
                }
            }
        }
        return false;

    }
}
