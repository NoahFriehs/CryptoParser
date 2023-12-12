package at.md;


import at.md.Transactions.CroCardTransaction;
import at.md.Util.Converter;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.math.BigDecimal;


public class CroCardTransactionTest {

    @DataProvider(name = "transactionTypeValues")
    public Object[][] transactionTypeValues() {
        return new Object[][] {
                {"2023-01-01", "Edge Case Transaction", "USD", BigDecimal.ZERO, BigDecimal.ZERO, "Normal"},
                {"2023-01-02", "Edge Case Transaction", "EUR", BigDecimal.valueOf(Long.MAX_VALUE), BigDecimal.ZERO, "Special"}
        };
    }

    @Test(dataProvider = "transactionTypeValues")
    public void testCroCardTransaction(String date, String description, String currencyType,
                                       BigDecimal amount, BigDecimal nativeAmount, String transactionType) {
        // When
        CroCardTransaction croCardTransaction = new CroCardTransaction(date, description, currencyType, amount, nativeAmount, transactionType);

        // Then
        Assert.assertEquals(croCardTransaction.getDate(), Converter.dateConverter(date));
        Assert.assertEquals(croCardTransaction.getDescription(), description);
        Assert.assertEquals(croCardTransaction.getCurrencyType(), currencyType);
        Assert.assertEquals(croCardTransaction.getAmount(), amount);
        Assert.assertEquals(croCardTransaction.getNativeAmount(), nativeAmount);
        Assert.assertEquals(croCardTransaction.getTransactionTypeString(), transactionType);
    }

    @Test(dataProvider = "transactionTypeValues")
    public void testCroCardTransactionToString(String date, String description, String currencyType,
                                               BigDecimal amount, BigDecimal nativeAmount, String transactionType) {
        // When
        CroCardTransaction croCardTransaction = new CroCardTransaction(date, description, currencyType, amount, nativeAmount, transactionType);
        String toStringResult = croCardTransaction.toString();

        // Then
        Assert.assertTrue(toStringResult.contains("CardTransaction{"));
        Assert.assertTrue(toStringResult.contains("date=" + croCardTransaction.getDate()));
        Assert.assertTrue(toStringResult.contains("description='" + croCardTransaction.getDescription() + "'"));
        Assert.assertTrue(toStringResult.contains("amount=" + croCardTransaction.getAmount()));
        Assert.assertTrue(toStringResult.contains("transactionType=" + croCardTransaction.getTransactionTypeString()));
        Assert.assertTrue(toStringResult.contains("toCurrency=" + croCardTransaction.getToCurrency()));
        Assert.assertTrue(toStringResult.contains("toAmount=" + croCardTransaction.getToAmount()));
    }
}
