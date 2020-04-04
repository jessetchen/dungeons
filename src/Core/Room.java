package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Room implements Serializable {
    protected int height;
    protected int width;
    protected int startPositionX;
    protected int endPositionX;
    protected int startPositionY;
    protected int endPositionY;
    private static final long serialVersionUID = 1231231231231L;

    //Room constructor
    public Room(int h, int w, int startx, int starty) {
        height = h;
        width = w;
        startPositionX = startx;
        startPositionY = starty;
        endPositionX = startx + w;
        endPositionY = starty + h;
    }

    //function for actually drawing the tiles
    public void draw(TETile[][] single) {

        for (int i = startPositionX; i < (width + startPositionX); i++) {
            for (int j = startPositionY; j < (height + startPositionY); j++) {
                single[i][j] = GenWorld2.floortile;
            }
        }
    }

    //makes sure this room's parameters do not overlap another room
    public boolean checkempty(TETile[][] single) {
        for (int i = startPositionX - 1; i < (width + startPositionX + 1); i++) {
            for (int j = startPositionY - 1; j < (height + startPositionY + 1); j++) {
                if (single[i][j] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    public double findDistance(Room r) {
        int x1 = (this.startPositionX + this.endPositionX) / 2;
        int x2 = (r.endPositionX + r.startPositionX) / 2;
        int y1 = (this.endPositionY + this.startPositionY) / 2;
        int y2 = (r.startPositionY + r.endPositionY) / 2;
        double distance = Math.sqrt((Math.pow((x2 - x1), 2)) + Math.pow((y2 - y1), 2));
        return distance;
    }
}

