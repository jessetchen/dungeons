package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
//import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;
//import java.util.Random;

//player class, contains coordinates of player and move/draw functions
public class Player implements Serializable {
    protected int x;
    protected int y;
    protected TETile avatar;
    private static final long serialVersionUID = 1123L;
    protected int points;


    //spawns player in first room in random coordinates
    public Player(Room r, TETile a) {
        x = RandomUtils.uniform(GenWorld2.RANDOM, (r.startPositionX), (r.endPositionX));
        y = RandomUtils.uniform(GenWorld2.RANDOM, (r.startPositionY), (r.endPositionY));
        avatar = a;
    }

    //draws player to the board
    public void drawPlayer(TETile[][] board) {
        board[x][y] = avatar;
    }

    //moves player using xdir and ydir received from genGworld2 move function
    public void movePlayer(TETile[][] board, int xdir, int ydir) {
        if ((board[x + xdir][y + ydir].equals(GenWorld2.floortile)) || (board[x + xdir][y + ydir].equals(GenWorld2.gold))) {
            if (board[x + xdir][y + ydir].equals(GenWorld2.gold)) {
                points += 50;
            }
                board[x][y] = GenWorld2.floortile;
                x += xdir;
                y += ydir;
                drawPlayer(board);
                //System.out.println(x + " " + y);
        } else if ((board[x + xdir][y + ydir].equals(GenWorld2.spike))) {
            try {
                board[Game.WIDTH - GenWorld2.lives][Game.HEIGHT - 1] = Tileset.NOTHING;
                GenWorld2.lives = GenWorld2.lives - 1;
            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
    }

}


