

/*package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class GenWorld {
    private int startPositionX;
    private int endPositionX;
    private int startPositionY;
    private int endPositionY;

    private static final long SEED = 380195 ;
    private static final Random RANDOM = new Random(SEED);

    public static void emptyBoard(TETile[][] board) {
        for(int i = 0; i < Game.WIDTH; i++) {
            for (int j = 0; j < Game.HEIGHT; j++) {
                board[i][j] = Tileset.NOTHING;
            }
        }
    }

    public void genRoom(TETile[][] single, int x, int y) {
        int width = RandomUtils.uniform(RANDOM, 2, 10);
        int height = RandomUtils.uniform(RANDOM, 2, 10);
        if ((width + x) >= Game.WIDTH || (height + y) >= Game.HEIGHT) {
            genRoom(single, x, y);
        }
        for (int i = x; i < (width + x); i++) {
            for (int j = y; j < (height + y); j++) {
                single[i][j] = Tileset.WATER;
            }
        }
        this.startPositionX = x;
        this.endPositionX = x + width;
        this.startPositionY = y;
        this.endPositionY = y + height;
    }

    public void genHorizontalHallwayR(TETile[][] single, int x, int y) {
        int width = RandomUtils.uniform(RANDOM, 2, 10);
        for(int i = x; i < (x + width); i++) {
            single[i][y] = Tileset.WALL;
        }
    }

    public void genHorizontalHallwayL(TETile[][] single, int x, int y) {
        int width = RandomUtils.uniform(RANDOM, 2, 10);
        for(int i = x - 1; width >= x - i; i--) {
            single[i][y] = Tileset.WALL;
        }
    }

    public void genRoomHallwayHorizon(TETile[][] single) {
        int roomX = RandomUtils.uniform(RANDOM, 0, Game.WIDTH);
        int roomY = RandomUtils.uniform(RANDOM, 0, Game.HEIGHT);
        genRoom(single, roomX, roomY);
        int hallwayY = RandomUtils.uniform(RANDOM, startPositionY, endPositionY);
        randomHallwayGenX(single, hallwayY);

    }

    public void randomHallwayGenX(TETile[][] single, int y) {
        int num = RANDOM.nextInt(1);
        if (num == 1) {
            genHorizontalHallwayR(single, endPositionX, y);
        } else {
            genHorizontalHallwayL(single, startPositionX, y);
        }
    }

    public void genVerticalHallwayU(TETile[][] single, int x, int y) {
        int height = RandomUtils.uniform(RANDOM, 2, 10);
        for(int i = y; i < (y + height); i++) {
            single[x][i] = Tileset.WALL;
        }
    }

    public void genVerticalHallwayD(TETile[][] single, int x, int y) {
        int height = RandomUtils.uniform(RANDOM, 2, 10);
        for(int i = y - 1; height >= y - i; i--) {
            single[x][i] = Tileset.WALL;
        }
    }

    public void genRoomHallwayVert(TETile[][] single) {
        int roomX = RandomUtils.uniform(RANDOM, 0, Game.WIDTH);
        int roomY = RandomUtils.uniform(RANDOM, 0, Game.HEIGHT);
        genRoom(single, roomX, roomY);
        int hallwayX = RandomUtils.uniform(RANDOM, startPositionX, endPositionX);
        randomHallwayGenY(single, hallwayX);

    }

    public void randomHallwayGenY(TETile[][] single, int x) {
        int num = RANDOM.nextInt(1);
        if (num == 1) {
            genVerticalHallwayD(single, x, startPositionY);
        } else {
            genVerticalHallwayU(single, x, endPositionY);
        }
    }

    public static TETile randomTile() {
        int tileNum = RANDOM.nextInt(9);
        switch (tileNum) {
            case 0:
                return Tileset.WALL;
            case 1:
                return Tileset.FLOWER;
            case 2:
                return Tileset.FLOOR;
            case 3:
                return Tileset.GRASS;
            case 4:
                return Tileset.LOCKED_DOOR;
            case 5:
                return Tileset.MOUNTAIN;
            case 6:
                return Tileset.SAND;
            case 7:
                return Tileset.TREE;
            case 8:
                return Tileset.UNLOCKED_DOOR;
            case 9:
                return Tileset.WATER;
            default:
                return Tileset.NOTHING;
        }
    }

}
*/
