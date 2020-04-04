package byog.Core;

import byog.TileEngine.TETile;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestWorld {

    @Test
    public void testdifInputs() {
        Game game = new Game();
        TETile[][] worldState = game.playWithInputString("N999SDDDWWWDDD");
        //game = new Game();
        TETile[][] f1 = game.playWithInputString("N999SDDD:Q");
        f1 = game.playWithInputString("LWWWDDD");

        TETile[][] f2 = game.playWithInputString("N999SDDD:Q");
        f2 = game.playWithInputString("LWWW:Q");
        f2 = game.playWithInputString("LDDD:Q");

        TETile[][] f3 = game.playWithInputString("N999SDDD:Q");
        f3 = game.playWithInputString("L:Q");
        f3 = game.playWithInputString("L:Q");
        f3 = game.playWithInputString("LWWWDDD");

        assertArrayEquals(f1, worldState);
        assertArrayEquals(f2, worldState);
        assertArrayEquals(f3, worldState);
    }
}

