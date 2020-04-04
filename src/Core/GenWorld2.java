package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;
import java.util.ArrayList;
//import edu.princeton.cs.introcs.StdDraw;


public class GenWorld2 implements Serializable {

    protected ArrayList<Room> listOfRooms = new ArrayList<>();
    //aesthetic seed 6165
    private long SEED;
    protected static Random RANDOM;
    protected static TETile floortile = Tileset.FLOOR;
    protected static TETile walltile = Tileset.WALL;
    protected static TETile spike = Tileset.SPIKE;
    protected static TETile gold = Tileset.SAND;
    protected int goldCount = 0;
    protected Player player;
    protected Player player2;
    protected int yPlayer;
    protected TETile[][] board;
    protected static int lives = 5;
    public static int POINTS = 0;
    private static final long serialVersionUID = 1231433221L;


    public GenWorld2(long input) {
        SEED = input;
        RANDOM = new Random(input);
        //floortile = randomTile();
        board = new TETile[Game.WIDTH][Game.HEIGHT];
    }

    //fills board with Nothing tiles
    public void emptyBoard() {
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int j = 0; j < Game.HEIGHT; j++) {
                board[i][j] = Tileset.NOTHING;
            }
        }

    }

    //creates a new room with random parameters and draw it to the board
    public void genRoom() {
        int width = RandomUtils.uniform(RANDOM, 2, 10);
        int height = RandomUtils.uniform(RANDOM, 2, 10);
        int sx = RandomUtils.uniform(RANDOM, 2, Game.WIDTH - (width + 2));
        int sy = RandomUtils.uniform(RANDOM, 2, Game.HEIGHT - (height + 2));
        Room r = new Room(height, width, sx, sy);
        if (r.checkempty(board)) {
            r.draw(board);
            listOfRooms.add(r);
        } else {
            genRoom();
        }

    }

    //draws a random number of rooms to the board
    public void genRooms() {
        int randNum = RandomUtils.uniform(RANDOM, 6, 15);
        for (int i = 0; i < randNum; i++) {
            genRoom();
        }
    }

    //draws walls to the board
    public void genWalls() {
        for (int i = 1; i < Game.WIDTH - 1; i++) {
            for (int j = 1; j < Game.HEIGHT - 1; j++) {
                if (board[i][j] == Tileset.NOTHING && ((board[i - 1][j] == Tileset.FLOOR)
                        || (board[i + 1][j] == floortile)
                        || (board[i][j - 1] == floortile)
                        || (board[i][j + 1] == floortile)
                        || (board[i - 1][j - 1] == floortile)
                        || (board[i - 1][j + 1] == floortile)
                        || (board[i + 1][j - 1] == floortile)
                        || (board[i + 1][j + 1] == floortile))) {
                    board[i][j] = spikeOrWall();
                }
            }
        }

    }

    private TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0:
                return Tileset.MOUNTAIN;
            case 1:
                return Tileset.FLOWER;
            case 2:
                return Tileset.GRASS;
            case 3:
                return Tileset.SAND;
            case 4:
                return Tileset.WATER;
            case 5:
                return Tileset.FLOOR;
            default:
                return Tileset.FLOOR;
        }
    }

    public TETile spikeOrWall() {
        int x = RANDOM.nextInt(50);
        if (x < 45) {
            return walltile;
        } else {
            return spike;
        }
    }

    public void connectTwo(Room r1, Room r2) {
        int centerx1 = RandomUtils.uniform(RANDOM, r1.startPositionX, r1.endPositionX);
        int centery1 = RandomUtils.uniform(RANDOM, r1.startPositionY, r1.endPositionY);
        int centerx2 = RandomUtils.uniform(RANDOM, r2.startPositionX, r2.endPositionX);
        int centery2 = RandomUtils.uniform(RANDOM, r2.startPositionY, r2.endPositionY);

        Hallway hall = new Hallway(centerx1, centery1, centerx2, centery2);
        hall.drawVertical(board, centerx1, centery1, centery2);
        hall.drawHorizontal(board, centerx1, centery2, centerx2);
    }

    public void multipleHallways() {
        int size = listOfRooms.size();
        for (int i = 0; i < size - 1; i++) {
            connectTwo(listOfRooms.get(i), listOfRooms.get(i + 1));
        }
    }

    public void spawnPlayer() {
        Room f = listOfRooms.get(0);
        Room s = listOfRooms.get(1);
        Player p = new Player(f, Tileset.PLAYER);
        Player p2  = new Player(s, Tileset.TREE);
        player = p;
        player2 = p2;
        player.drawPlayer(board);
        player2.drawPlayer(board);
    }


    //uses input key to determine where to move player using direction integer
    public void movep(char input) {
        int xdir = 0;
        int ydir = 0;
        switch (input) {
            case 'w': ydir = 1;
                break;
            case 'a': xdir = -1;
                break;
            case 's': ydir = -1;
                break;
            case 'd': xdir = 1;
                break;
            default: return;
        }
        player.movePlayer(board, xdir, ydir);
    }

    public void movep2(char input) {
        int xdir = 0;
        int ydir = 0;
        switch (input) {
            case 'i': ydir = 1;
                break;
            case 'j': xdir = -1;
                break;
            case 'k': ydir = -1;
                break;
            case 'l': xdir = 1;
                break;
            default: return;
        }
        player2.movePlayer(board, xdir, ydir);
    }

    public void mouseMover() {
        int xMouse = (int) StdDraw.mouseX();
        int yMouse = (int) StdDraw.mouseY();
        try {
            if (board[xMouse][yMouse].equals(Tileset.FLOOR)) {
                StdDraw.setPenColor(Color.orange);
                StdDraw.text(8, Game.HEIGHT - 1, "floor: step on me!! ;))");
                StdDraw.show();
            } else if (board[xMouse][yMouse].equals(Tileset.WALL)) {
                StdDraw.setPenColor(Color.pink);
                StdDraw.text(15, Game.HEIGHT - 1, "wall: The barrier that separates from the void #basic");
                StdDraw.show();
            } else if (board[xMouse][yMouse] == player.avatar) {
                StdDraw.setPenColor(Color.yellow);
                StdDraw.text(11, Game.HEIGHT - 1, "player 1: the blooming L&S CS stud");
                StdDraw.show();
            } else if (board[xMouse][yMouse] == player2.avatar) {
                StdDraw.setPenColor(Color.green);
                StdDraw.text(11, Game.HEIGHT - 1, "player 2: The Green CNR student");
                StdDraw.show();
            } else if (board[xMouse][yMouse].equals(Tileset.SPIKE)){
                StdDraw.setPenColor(Color.red);
                StdDraw.text(11, Game.HEIGHT - 1, "spike: oohh pointy! Don't touch!!");
                StdDraw.show();
            } else {
                StdDraw.setPenColor(Color.white);
                StdDraw.text(8, Game.HEIGHT - 1, "");
                StdDraw.show();
            }
        } catch (ArrayIndexOutOfBoundsException e){

        }
    }

    public void genLives() {
        for (int i = 0; i < lives; i++) {
            board[Game.WIDTH - 1 - i][Game.HEIGHT - 1] = Tileset.HEART;
        }
    }

    public void genGold() {
        for (int i = 0; i < 20; i++) {
            int x = RANDOM.nextInt(Game.WIDTH - 1);
            int y = RANDOM.nextInt(Game.HEIGHT - 1);
            if (board[x][y] == floortile) {
                board[x][y] = gold;
                goldCount = goldCount + 1;
            }
        }
    }

}


