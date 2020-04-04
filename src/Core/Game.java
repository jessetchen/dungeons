package byog.Core;


import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;


import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import edu.princeton.cs.introcs.In;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    public static int checker = 1;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        mainMenu();
        GenWorld2 genWorld2;
        char mainmenuinput = getKeyPress();
        mainmenuinput = java.lang.Character.toLowerCase(mainmenuinput);
        genWorld2 = pickNorL(mainmenuinput);
        if (genWorld2 == null) {
            playWithKeyboard();
        }
        ter.initialize(Game.WIDTH, Game.HEIGHT);
        if (java.lang.Character.toLowerCase(mainmenuinput) == 'n') {
            createWorld(genWorld2);
        }
        StdDraw.clear();
        boolean wantquit = false;
        while (true) {
            ter.renderFrame(genWorld2.board);
            genWorld2.mouseMover();
            if (StdDraw.hasNextKeyTyped()) {
                char input = StdDraw.nextKeyTyped();
                input = java.lang.Character.toLowerCase(input);
                //System.out.println(input);
                genWorld2.movep(input);
                genWorld2.movep2(input);
                if (checker == 1) {
                    checkLives();
                    //checkPoints(genWorld2);
                }
                ter.renderFrame(genWorld2.board);
                if (input == ':') {
                    wantquit = true;
                }
                if (input == 'q' && wantquit) {
                    break;
                }
                if (checker == 0) {
                    loseMenu(genWorld2.player, genWorld2.player2);
                    checker = checker - 1;
                    break;
                }
            }
        }
        saveWorld(genWorld2);
        System.exit(0);
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */


    public TETile[][] playWithInputString(String input) {
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        GenWorld2 genWorld2 = new GenWorld2(1);
        long seed = getSeed(input);
        char[] charArr = getinputs(input);
        if (charArr[0] == 'n') {
            genWorld2 = new GenWorld2(seed);
            createWorld(genWorld2);
        } else if (charArr[0] == 'l') {
            genWorld2 = loadWorld();
        }
        //ter.initialize(Game.WIDTH, Game.HEIGHT);

        for (int c = 0; c < charArr.length; c++) {
            if (charArr[c] == 'q') {
                saveWorld(genWorld2);
                return genWorld2.board;
            }
            genWorld2.movep(charArr[c]);
        }

        //ter.renderFrame(genWorld2.board);
        return genWorld2.board;
        //TETile[][] finalWorldFrame = null;
        //return finalWorldFrame;
    }

    public static long getSeed(String input) {
        char[] inputChopped = input.toCharArray();
        char[] intOnly = new char[input.length()];
        int j = 0;
        for (int i = 0; i < inputChopped.length; i++) {
            if (inputChopped[i] == ':') {
                continue;
            }
            if (inputChopped[i] < ('A')) {
                intOnly[j] = inputChopped[i];
                j++;
            }
        }
        int[] seedChopped = new int[j];
        for (int i = 0; i < j; i++) {
            seedChopped[i] = Character.getNumericValue(intOnly[i]);
        }
        double seed = 0;
        for (int i = 0; i < j; i++) {
            seed = seed + (seedChopped[i] * Math.pow(10, (j - i)));
        }
        return Math.round(seed / 10);
    }

    public char[] getinputs(String input) {
        char[] inputChopped = input.toLowerCase().toCharArray();
        char[] charOnly = new char[input.length()];
        int j = 0;
        for (int i = 0; i < inputChopped.length; i++) {
            if (inputChopped[i] == ':') {
                continue;
            }
            if (inputChopped[i] >= ('a')) {
                charOnly[j] = inputChopped[i];
                j++;
            }
        }
        char[] res = new char[j];
        System.arraycopy(charOnly, 0, res, 0, j);
        return res;
    }

    public long loadGame() {
        long seed = 0;
        In in = new In("byog/Core/seed.txt");
        seed = in.readLong();
        return seed;
    }

    public void mainMenu() {
        StdDraw.setCanvasSize(this.WIDTH * 16, this.HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.WIDTH);
        StdDraw.setYscale(0, this.HEIGHT);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT  - 4, "CS61B THE GAME");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT / 2 - 2, "New Game(N)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT / 2 - 4, "Load Game(L)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT / 2 - 6, "Quit(Q)");
        StdDraw.show();
    }

    public void displaySeedMenu() {
        StdDraw.setCanvasSize(this.WIDTH * 16, this.HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.WIDTH);
        StdDraw.setYscale(0, this.HEIGHT);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT  - 4, "Enter Seed");
        StdDraw.show();
    }

    public Long keyboardSeed() {
        String input = "";
        char x = '.';
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                x = StdDraw.nextKeyTyped();
                input = input + x;
                StdDraw.text(this.WIDTH / 2, this.HEIGHT / 2, input);
            }
            StdDraw.show(250);
            StdDraw.clear();
            StdDraw.text(this.WIDTH / 2, this.HEIGHT  - 4, "Enter Seed");
            StdDraw.show();
            if (x == 's' || x == 'S') {
                StdDraw.text(this.WIDTH / 2, this.HEIGHT / 2, input);
                StdDraw.show(1000);
                break;
            }
        }
        return getSeed(input);
    }

    public char getKeyPress() {
        while (!StdDraw.hasNextKeyTyped()) {
            continue;
        }
        return StdDraw.nextKeyTyped();
    }

    private static void saveWorld(GenWorld2 w) {
        File f = new File("./loadworld.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(w);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private static GenWorld2 loadWorld() {
        File f = new File("./loadworld.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                GenWorld2 loadWorld = (GenWorld2) os.readObject();
                os.close();
                return loadWorld;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

        /* In the case no World has been saved yet, we return a new one. */
        return new GenWorld2(9999);
    }

    public void createWorld(GenWorld2 w) {
        w.emptyBoard();
        w.genRooms();
        w.multipleHallways();
        w.genWalls();
        w.spawnPlayer();
        w.genLives();
        w.genGold();
    }

    public GenWorld2 pickNorL(char input) {
        if (input == 'n') {
            displaySeedMenu();
            Long s = keyboardSeed();
            return new GenWorld2(s);
        } else if (input == 'l') {
            return loadWorld();
        } else if (input == 'q') {
            System.exit(0);
        }
        return null;
    }

    public void checkLives() {
        if (GenWorld2.lives == 0) {
            StdDraw.setPenColor(Color.white);
            StdDraw.text(Game.WIDTH / 2, Game.HEIGHT - 2, "You Are Out of Lives.");
            StdDraw.show(3000);
            checker = checker - 1;
        }
    }

    public void checkPoints(GenWorld2 w) {
        int goldLeft = 0;
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int j = 0; j < Game.HEIGHT; j++) {
                if (w.board[i][j] == Tileset.SAND) {
                    goldLeft = goldLeft + 1;
                }
            }
        }
        GenWorld2.POINTS = GenWorld2.POINTS + ((w.goldCount - goldLeft) * 50);
    }

    public void loseMenu(Player p, Player p2) {
        StdDraw.setCanvasSize(this.WIDTH * 16, this.HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.WIDTH);
        StdDraw.setYscale(0, this.HEIGHT);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT  - 4, "GAME OVER. LOAD YOUR GAME TO CONTINUE EXPLORING!");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT / 2 - 2, "PLAYER 1 POINTS EARNED: " + p.points);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT / 2 - 4, "PLAYER 2 POINTS EARNED: " + p2.points);
        StdDraw.show(10000);
    }

}


