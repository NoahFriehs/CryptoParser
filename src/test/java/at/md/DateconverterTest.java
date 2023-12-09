package at.md;

import at.md.Util.Converter;
import org.testng.annotations.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class DateconverterTest {


    // Converts a valid date string in the format "yyyy-MM-dd HH:mm:ss" to a Date object
    @Test
    public void test_valid_date_string() {
        String dateString = "2022-12-31 23:59:59";
        Date expectedDate = new Date(122, 11, 31, 23, 59, 59);
        Date actualDate = Converter.dateConverter(dateString);
        assertEquals(expectedDate, actualDate);
    }

    // Returns null when given a null input
    @Test
    public void test_null_input() {
        String dateString = null;
        Date actualDate = Converter.dateConverter(dateString);
        assertNull(actualDate);
    }

    // Returns null when given an empty string input
    @Test
    public void test_empty_string_input() {
        String dateString = "";
        Date actualDate = Converter.dateConverter(dateString);
        assertNull(actualDate);
    }
}