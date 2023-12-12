package at.md;

import at.md.General.Application;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

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
}
