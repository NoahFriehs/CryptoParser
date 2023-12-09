package at.md;

import at.md.Util.TimeSpan;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeSpanTest {

    @Test
    void testElapsedTime() {
        TimeSpan timeSpan = new TimeSpan();

        timeSpan.start();

        // Simulate some time passing
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        double elapsedTime = timeSpan.end();

        assertEquals(100, elapsedTime, 10); // Allow a tolerance of 10 milliseconds
    }
}

