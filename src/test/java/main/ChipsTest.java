/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King *
 * Name: Lindsay Knupp
 * Section: 01 - 11:30am
 * Date: 11/2/20
 * Time: 12:53 PM
 *
 * Project: csci205FinalProject
 * Package: main * Class: Chips
 *
 * Description: Test class for Chips class
 *
 * ****************************************
 */
package main;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChipsTest {

    /** Chips used in every test */
    private Chips chips;

    @BeforeEach
    void setUp() { this.chips = new Chips();
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * A test to make sure amount is added to current amount of chips
     */
    @Test
    void addAmount() {
        // Test the initial state. Initial and current amount should both be zero.
        assertEquals(0,chips.getCurrAmount());
        assertEquals(0,chips.getInitAmount());

        // Add $5 worth of chips
        chips.addAmount(5);
        assertEquals(5,chips.getCurrAmount());
        assertEquals(0,chips.getInitAmount());
    }

    /**
     * A test to make sure amount is subtracted from current amount of chips
     */
    @Test
    void subtractAmount() {
        // Test the initial state. Initial and current amount should both be zero.
        assertEquals(0,chips.getCurrAmount());
        assertEquals(0,chips.getInitAmount());

        // Add $25 worth of chips
        chips.addAmount(25);

        // Subtract $13 worth of chips
        chips.subtractAmount(13);
        assertEquals(12,chips.getCurrAmount());
        assertEquals(0,chips.getInitAmount());
    }

    /**
     * A test to test toString method for current amount of chips
     */
    @Test
    void testToString() {
        // Add $35 worth of chips
        chips.addAmount(35);
        assertEquals("$35.0",chips.toString());
    }
}