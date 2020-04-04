package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Wall implements Serializable {

    protected int x;
    protected int y;
    protected static TETile tile = Tileset.WALL;
    private static final long serialVersionUID = 112377L;

    public Wall(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(TETile[][] board) {
        board[x][y] = tile;
    }
}
