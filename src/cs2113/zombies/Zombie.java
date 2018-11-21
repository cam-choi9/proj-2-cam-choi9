package cs2113.zombies;

import java.awt.*;
import java.util.ArrayList;

public class Zombie extends Human {

    public Zombie (int x, int y) {
        super (x, y);
        changeDirection();
    }

    public void draw() {
        ZombieSim.dp.setPenColor(Color.red);                                           // set color to red
        ZombieSim.dp.drawDot(getX(), getY());                                          // draw a dot (zombie)
    }

    public void move(boolean walls[][], int w, int h, boolean[][] survivors) {

        whereIsMyFood(walls, w, h, survivors);
        move(walls, w, h, 0.2);
    }

    // chasing humans
    public boolean whereIsMyFood (boolean[][] walls, int width, int height, boolean[][] survivors) {

        if (direction == 0) {                                                          // facing up
            for (int i=1; i<11; i++) {                                                 // check if a human is within 10 squares of its direction
                if (inBoundsNotWalls(walls, width, height, x, y+i)) {
                    if (survivors[x][y+i]) {
                        setDirection(0);                                               // face up
                        y++;                                                           // move up
                        return true;
                    }
                    //System.out.println ("YOU ZOMBIEEEEEE==============================================1");
                }
            }
        }


        else if (direction == 1) {                                                     // facing down
            for (int i=1; i<11; i++) {                                                 // check if a human is within 10 squares of its direction
                if (inBoundsNotWalls(walls, width, height, x+i, y)) {
                    if (survivors[x+i][y]) {
                        setDirection(1);                                               // face down
                        x++;                                                           // move right
                        return true;
                    }
                }
                //System.out.println ("YOU ZOMBIEEEEEE==============================================2");
            }
        }

        else if (direction == 2) {                                                     // facing down
            for (int i=1; i<11; i++) {                                                 // check if a human is within 10 squares of its direction
                if (inBoundsNotWalls(walls, width, height, x, y-i)) {
                    if (survivors[x][y-i]) {
                        setDirection(2);                                               // face down
                        y--;                                                           // move down
                        return true;
                    }
                }
                //System.out.println ("YOU ZOMBIEEEEEE==============================================3");
            }
        }

        else if (direction == 3) {                                                     // facing left
            for (int i=1; i<11; i++) {                                                 // check if a human is within 10 squares of its direction
                if (inBoundsNotWalls(walls, width, height, x-i, y)) {
                    if (survivors[x-i][y]) {
                        setDirection(3);                                               // face left
                        x--;                                                           // move left
                        return true;
                    }
                }
                //System.out.println ("YOU ZOMBIEEEEEE==============================================4");
            }
        }
        return false;
    }
}
