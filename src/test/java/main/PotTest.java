/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Lindsay Knupp
 * Section: 01 - 11:30am
 * Date: 11/3/2020
 * Time: 1:00 PM
 *
 * Project: csci205FinalProject
 * Package: main
 * Class: Pot
 *
 * Description: A test class for the Pot
 * object
 *
 * ****************************************
 */
package main;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PotTest {

    /** Pot used in every test */
    private Pot pot;

    @BeforeEach
    void setUp() { this.pot = new Pot();
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * A test to make sure amount is being added to Pot
     */
    @Test
    void addToPot() {
        // Test the initial state, the total amount should be zero.
        assertEquals(0,pot.getTotalAmount());

        // Add $25 to pot
        pot.addToPot(25);
        assertEquals(25,pot.getTotalAmount());
    }

    /**
     * A test to make sure Pot is empty when no bets have been added
     */
    @Test
    void isEmpty() {
        // The initial state should be empty
        assertTrue(pot.isEmpty());

        // Add bet amount to Pot
        pot.addToPot(10);
        assertFalse(pot.isEmpty());
    }

    /**
     * A test to make sure the total amount in the Pot is correct
     */
    @Test
    void getTotalAmount(){
        // Add $5 to the pot
        pot.addToPot(5);

        assertEquals(5,pot.getTotalAmount());
    }

    /**
     * A test to test toString method for total pot amount
     */
    @Test
    void testToString() {
        assertEquals("Total Pot Amount is 0.0",pot.toString());
    }
}