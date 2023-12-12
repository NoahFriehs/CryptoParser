package at.md;

import at.md.General.Application;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static at.md.General.Application.userInstructionsCardTxApp;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testng.Assert.assertEquals;

public class App2Test {

    @Test
    void testReadNumber() {
        System.out.println("App2Test.testReadNumber");
        String input = "20";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        System.setIn(in);
        BigDecimal expected = new BigDecimal("20");
        assertEquals(Application.readNumber("Enter number: "), expected);
        System.setIn(originalIn);
        System.setOut(originalOut);
    }


    @Test
    public void testUserInstructionsCardTxApp() {
        // Redirecting System.out to access it later
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Execute userInstructionsCardTxApp
        userInstructionsCardTxApp();

        // Check if the output is correct
        String expectedOutput0 = "Press 0 to exit";
        String expectedOutput1 = "Press 1 to get all transactions from 1 type";
        String expectedOutput2 = "Press 2 to get transaction by amount";
        String expectedOutput3 = "Press 3 to get transaction by date";
        String expectedOutput4 = "Press 4 to get all different transactions with amount";
        String expectedOutput9 = "Press 9 for help";

        assertTrue(outContent.toString().contains(expectedOutput0));
        assertTrue(outContent.toString().contains(expectedOutput1));
        assertTrue(outContent.toString().contains(expectedOutput2));
        assertTrue(outContent.toString().contains(expectedOutput3));
        assertTrue(outContent.toString().contains(expectedOutput4));
        assertTrue(outContent.toString().contains(expectedOutput9));
    }
}
