package at.md;

import at.md.General.TxApp;
import at.md.Transactions.Transaction;
import at.md.Transactions.TransactionType;
import at.md.Util.Converter;
import at.md.Util.CurrencyType;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;

public class TxAppTest {


    // The main happy path is to successfully read a CSV file and convert it to a list of transactions using the getTransactions method
    @Test
    public void test_happy_path() {
        // Arrange
        ArrayList<String> input = new ArrayList<>();
        input.add("Timestamp (UTC),Transaction Description,Currency,Amount,To Currency,To Amount,Native Currency,Native Amount,Native Amount (in USD),Transaction Kind,Transaction Hash");
        input.add("2022-08-06 21:38:10,Card Cashback,CRO,100.00,,,EUR,100.00,100.00,referral_card_cashback,");

        // Act
        ArrayList<Transaction> result = TxApp.getTransactions(input);

        // Assert
        assertEquals(1, result.size());

        Date expectedDate = Converter.dateConverter("2022-08-06 21:38:10");
        Date resultDate = result.get(0).getDate();
        assertEquals(expectedDate, resultDate);
        assertEquals("Card Cashback", result.get(0).getDescription());
        assertEquals("CRO", result.get(0).getCurrencyType());
        assertEquals(100.0, result.get(0).getAmount().doubleValue());
        assertEquals(100.0, result.get(0).getNativeAmount().doubleValue());
        assertEquals(TransactionType.referral_card_cashback, result.get(0).getTransactionType());
    }

    // The createWallets method should create two empty wallet lists, one for internal wallets and one for outside wallets
    @Test
    public void test_create_wallets() {
        TxApp.createWallets();

        assertEquals(0, TxApp.wallets.size());
        assertEquals(0, TxApp.outsideWallets.size());

        CurrencyType.currencys.add("EUR");
        CurrencyType.currencys.add("USD");

        TxApp.createWallets();

        assertEquals(2, TxApp.wallets.size());
        assertEquals(2, TxApp.outsideWallets.size());

        assertEquals("EUR", TxApp.wallets.get(0).getCurrencyType());
        assertEquals("USD", TxApp.wallets.get(1).getCurrencyType());

        assertEquals("EUR", TxApp.outsideWallets.get(0).getCurrencyType());
        assertEquals("USD", TxApp.outsideWallets.get(1).getCurrencyType());

        CurrencyType.currencys.clear();
        TxApp.wallets.clear();
        TxApp.outsideWallets.clear();
    }

    // The fillWallet method should add each transaction to the first wallet in the internal wallets list
    @Test
    public void test_fill_wallet() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction("2022-01-01", "Test Transaction 1", "EUR", BigDecimal.valueOf(100.00), BigDecimal.valueOf(100.00), TransactionType.crypto_purchase);
        Transaction transaction2 = new Transaction("2022-01-02", "Test Transaction 2", "EUR", BigDecimal.valueOf(200.00), BigDecimal.valueOf(200.00), TransactionType.crypto_purchase);
        transactions.add(transaction1);
        transactions.add(transaction2);
        TxApp.createWallets();

        TxApp.fillWallet(transactions);

        assertEquals(1, TxApp.wallets.size());
        assertEquals(2, TxApp.wallets.get(0).getTransactions().size());
        assertEquals(transaction1, TxApp.wallets.get(0).getTransactions().get(0));
        assertEquals(transaction2, TxApp.wallets.get(0).getTransactions().get(1));
    }

    // If the input ArrayList in getTransactions is empty, an empty ArrayList of transactions should be returned
    @Test
    public void test_empty_input() {
        ArrayList<String> input = new ArrayList<>();
        ArrayList<Transaction> result = TxApp.getTransactions(input);
        assertEquals(0, result.size());
    }

    // If the input ArrayList in getTransactions contains an invalid number of elements, a RuntimeException should be thrown
    @Test
    public void test_invalid_input() {
        ArrayList<String> input = new ArrayList<>();
        input.add("date,description,currencyType,amount,nativeAmount,transactionType");
        assertThrows(RuntimeException.class, () -> TxApp.getTransactions(input));
    }

    // If the amount or nativeAmount in a Transaction object is zero, it should be set to BigDecimal.ZERO
    @Test
    public void test_zero_amount() {
        ArrayList<String> input = new ArrayList<>();
        input.add("Timestamp (UTC),Transaction Description,Currency,Amount,To Currency,To Amount,Native Currency,Native Amount,Native Amount (in USD),Transaction Kind,Transaction Hash");
        input.add("2022-08-06 21:38:10,Card Cashback,CRO,0,,,EUR,0,0,referral_card_cashback,");

        ArrayList<Transaction> result = TxApp.getTransactions(input);

        assertEquals(1, result.size());
        assertEquals(BigDecimal.ZERO.doubleValue(), result.get(0).getAmount().doubleValue());
        assertEquals(BigDecimal.ZERO.doubleValue(), result.get(0).getNativeAmount().doubleValue());
    }

}