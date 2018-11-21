package cs2113.zombies;

import cs2113.util.Helper;

public class Survivor extends Human {

    public Survivor (int x, int y) {
        super (x, y);
    }

    public void move(boolean walls[][], int w, int h, boolean[][] zombies) {
        isItSafe(walls, w, h, zombies);
        move(walls, w, h, 0.1);
    }
    // running away from zombies
    public boolean isItSafe (boolean[][] walls, int width, int height, boolean[][] zombies) {

        if (direction == 0) {                                                               // facing up
            for (int i=1; i<11; i++) {                                                      // check if a zombie is within 10 squares of its direction
//                System.out.println("You idiot--------------------");
                if (inBoundsNotWalls(walls, width, height, x, y+i)) {
                    if (zombies[x][y+i]) {
                        if (inBoundsNotWalls(walls, width, height, x, y-1))              // while it's ok to move 1 square down
                        {
                            setDirection(2);                                                // face down
                            y--;                                                            // move down once
                            return true;
                        }
                        if (inBoundsNotWalls(walls, width, height, x, y-2))              // while it's ok to move 1 square down
                        {
                            setDirection(2);                                                // face down
                            y--;                                                            // move down once
                            return true;
                        }
                    }
                }
            }
        }

        else if (direction == 1) {                                                          // facing right
            for (int i=1; i<11; i++) {                                                      // check if a zombie is within 10 squares of its direction
//                System.out.println("You idiot2--------------------");
                if (inBoundsNotWalls(walls, width, height, x+i, y)) {
                    if (zombies[x+i][y]) {
                        if (inBoundsNotWalls(walls, width, height, x-1, y))              // while it's ok to move 1 square left
                        {
                            setDirection(3);                                                // face left
                            x--;                                                            // move left once
                            return true;
                        }
                        if (inBoundsNotWalls(walls, width, height, x-2, y))              // while it's ok to move 1 square left
                        {
                            setDirection(3);                                                // face left
                            x--;                                                            // move left once
                            return true;
                        }
                    }
                }
            }
        }

        else if (direction == 2) {                                                         // facing down
            for (int i=1; i<11; i++) {                                                     // check if a zombie is within 10 squares of its direction
                if (inBoundsNotWalls(walls, width, height, x, y-i)) {
//                    System.out.println("You idiot3--------------------");
                    if (zombies[x][y-i]) {
                        if (inBoundsNotWalls(walls, width, height, x, y+1))             // while it's ok to move 1 square up
                        {
                            setDirection(0);                                               // face up
                            y++;                                                           // move up once
                            return true;
                        }
                        if (inBoundsNotWalls(walls, width, height, x, y+2))             // while it's ok to move 1 square up
                        {
                            setDirection(0);                                               // face up
                            y++;                                                           // move up once
                            return true;
                        }
                    }
                }
            }
        }

        else if (direction == 3) {                                                         // facing left
            for (int i=1; i<11; i++) {                                                     // check if a zombie is within 10 squares of its direction
//                System.out.println("You idiot4--------------------");
                if (inBoundsNotWalls(walls, width, height, x-i, y)) {
                    if (zombies[x-i][y]) {
                        if (inBoundsNotWalls(walls, width, height, x+1, y))             // while it's ok to move 1 square right
                        {
                            setDirection(1);                                               // face right
                            x++;                                                           // move right once
                            return true;
                        }
                        if (inBoundsNotWalls(walls, width, height, x+2, y))             // while it's ok to move 1 square down
                        {
                            setDirection(1);                                               // face right
                            x++;                                                           // move right once
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
