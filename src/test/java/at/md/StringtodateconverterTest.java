package at.md;

import at.md.Util.Converter;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class StringtodateconverterTest {


    // Returns a string in the format 'yyyy-MM-dd' when given a valid Date object
    @Test
    public void test_validDateObject_returnsFormattedString() {
        Date date = new Date();
        String result = Converter.stringToDateConverter(date);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String expected = dateFormat.format(date);
        assertEquals(expected, result);
    }

    // Returns null when given a null Date object
    @Test
    public void test_nullDateObject_returnsNull() {
        Date date = null;
        String result = Converter.stringToDateConverter(date);
        assertNull(result);
    }
}
