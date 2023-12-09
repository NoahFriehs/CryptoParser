package at.md;

import at.md.Transactions.TransactionType;
import at.md.Util.Converter;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TtConverterTest {


    // Given a valid string input representing a transaction type, the method should return the corresponding TransactionType enum value.
    @Test
    public void test_valid_string_input() {
        String input = "crypto_purchase";
        TransactionType expected = TransactionType.crypto_purchase;
        TransactionType result = Converter.ttConverter(input);
        assertEquals(expected, result);
    }

    // The method should be case-insensitive, meaning that it should be able to handle both upper and lower case inputs.
    @Test
    public void test_case_insensitive_input() {
        String input = "SuPeRcHaRgEr_DePoSiT";
        TransactionType expected = TransactionType.supercharger_deposit;
        TransactionType result = Converter.ttConverter(input);
        assertEquals(expected, result);
    }

    // The method should trim the input string before processing it.
    @Test
    public void test_trim_input_string() {
        String input = "  rewards_platform_deposit_credited  ";
        TransactionType expected = TransactionType.rewards_platform_deposit_credited;
        TransactionType result = Converter.ttConverter(input);
        assertEquals(expected, result);
    }

    // Given an empty string input, the method should return null.
    @Test
    public void test_empty_string_input() {
        String input = "";
        TransactionType result = Converter.ttConverter(input);
        assertNull(result);
    }

    // Given a string input that does not match any of the TransactionType enum values, the method should return null.
    @Test
    public void test_invalid_string_input() {
        String input = "invalid_transaction_type";
        TransactionType result = Converter.ttConverter(input);
        assertNull(result);
    }

    // Given a null input, the method should return null.
    @Test
    public void test_null_input() {
        String input = null;
        TransactionType result = Converter.ttConverter(input);
        assertNull(result);
    }

}

