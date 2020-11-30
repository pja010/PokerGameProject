package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerCopyTest {

    PlayerCopy testPlayer1;
    PlayerCopy testPlayer2;
    PlayerCopy testPlayer3 ;

    @BeforeEach
    void setUp(){
        testPlayer1 = new PlayerCopy(1);
        testPlayer2 = new PlayerCopy(2);
        testPlayer3 = new PlayerCopy(3);
    }
    @Test
    void makeBetMove() {
        testPlayer1.setChips(100);
        testPlayer2.setChips(1000);
        testPlayer3.setChips(10000);

        double smallBet = 10;
        double largeBet = 1000;
        double illegalBet = -10;

//        testPlayer1.makeBetMove(largeBet);
        testPlayer2.makeBetMove(smallBet);
//        testPlayer3.makeBetMove(illegalBet);
        assertEquals(testPlayer2.getPlayerAction(), PlayerAction.BET);

//        Exception e = assertThrows(IllegalArgumentException.class, () -> {
//            testPlayer1.makeBetMove(largeBet);
//        });
//        String expectedExceptionMessage = "Illegal bet amount entered.";
//        String actualExceptionMessage = e.getMessage();
//        assertTrue(actualExceptionMessage.contains(expectedExceptionMessage));
//
//        assertNotEquals(testPlayer1.getPlayerAction(), PlayerAction.BET);
//        assertNotEquals(testPlayer3.getPlayerAction(), PlayerAction.BET);
    }

    @Test
    void makeCheckMove() {
        testPlayer2.makeCheckMove();
        assertEquals(PlayerAction.CHECK, testPlayer2.getPlayerAction());
        assertNotEquals(PlayerAction.CHECK,testPlayer3.getPlayerAction());
    }

    @Test
    void makeFoldMove() {
        testPlayer2.makeFoldMove();
        assertEquals(PlayerAction.FOLD, testPlayer2.getPlayerAction());
        assertNotEquals(testPlayer2.getPlayerAction(),testPlayer3.getPlayerAction());
    }
}