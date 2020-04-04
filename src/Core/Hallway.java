package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;

public class Hallway implements Serializable {
    private int centerx1;
    private int centery1;
    private int centerx2;
    private int centery2;
    private int xdir;
    private int ydir;
    private static final long serialVersionUID = 23123L;
    public Hallway(int cx1, int cy1, int cx2, int cy2) {
        centerx1 = cx1;
        centery1 = cy1;
        centerx2 = cx2;
        centery2 = cy2;
        if (centerx1 <= centerx2) {
            xdir = 1;
        } else {
            xdir = -1;
        }
        if (centery1 <= centery2) {
            ydir = 1;
        } else {
            ydir = -1;
        }
    }


    //draws hallways vertically
    public void drawVertical(TETile[][] single, int x, int y, int ey) {
        if (ydir < 0) {
            for (int i = y; i >= ey; i += ydir) {
                single[x][i] = GenWorld2.floortile;
            }

        } else {
            for (int i = y; i <= ey; i += ydir) {
                single[x][i] = GenWorld2.floortile;
            }
        }
    }

    //draws hallways horizontally
    public void drawHorizontal(TETile[][] single, int x, int y, int ex) {
        if (xdir < 0) {
            for (int i = x; i >= ex; i += xdir) {
                single[i][y] = GenWorld2.floortile;
            }

        } else {
            for (int i = x; i <= ex; i += xdir) {
                single[i][y] = GenWorld2.floortile;
            }
        }
    }


}

