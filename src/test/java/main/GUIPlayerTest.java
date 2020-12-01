package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test the functionality of the PlayerCopy poker actions.
 */
class GUIPlayerTest {

    GUIPlayer testPlayer1;
    GUIPlayer testPlayer2;
    GUIPlayer testPlayer3 ;

    /**
     * Example test bets of various sizes.
     */
    private double smallBet;
    private double largeBet;
    private double illegalBet;

    @BeforeEach
    void setUp(){
        testPlayer1 = new GUIPlayer(1);
        testPlayer2 = new GUIPlayer(2);
        testPlayer3 = new GUIPlayer(3);

        testPlayer1.setChips(100);
        testPlayer2.setChips(1000);
        testPlayer3.setChips(10000);

        smallBet = 10;
        largeBet = 1000;
        illegalBet = -10;
    }

    @Test
    void makeBetMove() {
        testPlayer1.makeBetMove(smallBet);
        testPlayer2.makeBetMove(smallBet);
        testPlayer3.makeBetMove(largeBet);

        assertEquals(testPlayer1.getPlayerAction(),"Bet");
        assertEquals(testPlayer2.getPlayerAction(), "Bet");
        assertEquals(testPlayer3.getPlayerAction(), "Bet");
    }

    @Test
    void makeBetMoveException() {
        assertThrows(IllegalArgumentException.class,
                () -> testPlayer1.makeBetMove(largeBet));
        assertThrows(IllegalArgumentException.class,
                () -> testPlayer3.makeBetMove(illegalBet));
}

    @Test
    void makeCheckMove() {
        testPlayer2.makeCheckMove();
        assertEquals("Check", testPlayer2.getPlayerAction());
        assertNotEquals("Check",testPlayer3.getPlayerAction());
    }

    @Test
    void makeFoldMove() {
        testPlayer2.makeFoldMove();
        assertEquals("Fold", testPlayer2.getPlayerAction());
        assertNotEquals(testPlayer2.getPlayerAction(),testPlayer3.getPlayerAction());
    }
}