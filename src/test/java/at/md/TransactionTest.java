package at.md;


import at.md.Transactions.Transaction;
import at.md.Transactions.TransactionType;
import at.md.Util.Converter;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.math.BigDecimal;

public class TransactionTest {

    @Test
    public void testTransactionConstructor() {
        // Given
        String date = "2023-01-01";
        String description = "Test Transaction";
        String currencyType = "USD";
        BigDecimal amount = new BigDecimal("100.00");
        BigDecimal nativeAmount = new BigDecimal("75.00");
        TransactionType transactionType = TransactionType.crypto_deposit;

        // When
        Transaction transaction = new Transaction(date, description, currencyType, amount, nativeAmount, transactionType);

        // Then
        Assert.assertEquals(transaction.getDate(), Converter.dateConverter(date));
        Assert.assertEquals(transaction.getDescription(), description);
        Assert.assertEquals(transaction.getCurrencyType(), currencyType);
        Assert.assertEquals(transaction.getAmount(), amount);
        Assert.assertEquals(transaction.getNativeAmount(), nativeAmount);
        Assert.assertEquals(transaction.getTransactionType(), transactionType);
    }

    @Test
    public void testSettersAndGetters() {
        // Given
        Transaction transaction = new Transaction("2023-01-01", "Test Transaction", "USD", BigDecimal.ZERO, BigDecimal.ZERO, TransactionType.crypto_earn_interest_paid);

        // When
        transaction.setTransactionType(TransactionType.crypto_purchase);
        transaction.setTransHash("hash123");
        transaction.setToCurrency("EUR");
        transaction.setToAmount(new BigDecimal("50.00"));

        // Then
        Assert.assertEquals(transaction.getTransactionType(), TransactionType.crypto_purchase);
        Assert.assertEquals(transaction.getTransHash(), "hash123");
        Assert.assertEquals(transaction.getToCurrency(), "EUR");
        Assert.assertEquals(transaction.getToAmount(), new BigDecimal("50.00"));
    }

    @Test
    public void testToStringMethod() {
        // Given
        Transaction transaction = new Transaction("2023-01-01", "Test Transaction", "USD", BigDecimal.ZERO, BigDecimal.ZERO, TransactionType.crypto_earn_interest_paid);

        // When
        String toStringResult = transaction.toString();

        // Then
        Assert.assertTrue(toStringResult.contains("Transaction{"));
        Assert.assertTrue(toStringResult.contains("date=" + transaction.getDate()));
        Assert.assertTrue(toStringResult.contains("description='" + transaction.getDescription() + "'"));
        Assert.assertTrue(toStringResult.contains("currencyType=" + transaction.getCurrencyType()));
        Assert.assertTrue(toStringResult.contains("amount=" + transaction.getAmount()));
        Assert.assertTrue(toStringResult.contains("nativeAmount=" + transaction.getNativeAmount()));
        Assert.assertTrue(toStringResult.contains("transactionType=" + transaction.getTransactionType()));
        Assert.assertTrue(toStringResult.contains("transHash='" + transaction.getTransHash() + "'"));
        Assert.assertTrue(toStringResult.contains("toCurrency=" + transaction.getToCurrency()));
        Assert.assertTrue(toStringResult.contains("toAmount=" + transaction.getToAmount()));
    }

    @DataProvider(name = "amountValues")
    public Object[][] amountValues() {
        return new Object[][] {
                {"2023-01-01", "Min Amount Transaction", "USD", BigDecimal.ZERO, BigDecimal.ZERO, TransactionType.crypto_purchase},
                {"2023-01-02", "Max Amount Transaction", "USD", BigDecimal.valueOf(Long.MAX_VALUE), BigDecimal.ZERO, TransactionType.crypto_deposit}
        };
    }

    @Test(dataProvider = "amountValues")
    public void testTransactionAmountValues(String date, String description, String currencyType,
                                            BigDecimal amount, BigDecimal nativeAmount, TransactionType transactionType) {
        // When
        Transaction transaction = new Transaction(date, description, currencyType, amount, nativeAmount, transactionType);

        // Then
        Assert.assertEquals(transaction.getDate(), Converter.dateConverter(date));
        Assert.assertEquals(transaction.getDescription(), description);
        Assert.assertEquals(transaction.getCurrencyType(), currencyType);
        Assert.assertEquals(transaction.getAmount(), amount);
        Assert.assertEquals(transaction.getNativeAmount(), nativeAmount);
        Assert.assertEquals(transaction.getTransactionType(), transactionType);
    }
}
