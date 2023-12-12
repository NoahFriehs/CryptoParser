package at.md;

import at.md.General.CardTxApp;
import at.md.Transactions.CroCardTransaction;
import at.md.Wallet.CroCardWallet;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CroCardWalletTest {

    @BeforeMethod
    public void setUp() {
        // Initialize any necessary setup before each test method
        CardTxApp.useStrictWalletType = true; // Set to true for testing strict wallet type
        CroCardWallet.reset();
        CardTxApp.cardWallets.clear();
        CardTxApp.transactions.clear();
    }

    @Test
    public void testAddTransaction() {
        // Given
        CroCardTransaction transaction = new CroCardTransaction("2023-01-01", "Test Transaction", "USD", BigDecimal.TEN, BigDecimal.ZERO, "TestType");

        // When
        CroCardWallet.addTransaction(transaction);

        // Then
        Assert.assertEquals(CardTxApp.cardWallets.size(), 1);

        CroCardWallet wallet = CardTxApp.cardWallets.get(0);
        Assert.assertEquals(wallet.getAmount(), BigDecimal.TEN);
        Assert.assertEquals(wallet.getMoneySpent(), BigDecimal.TEN);
        Assert.assertEquals(wallet.getTxs().size(), 1);
        Assert.assertEquals(wallet.getTxs().get(0), transaction);
    }

    @Test
    public void testWriteAmount() {
        // Given
        CroCardTransaction transaction1 = new CroCardTransaction("2023-01-01", "Transaction 1", "USD", BigDecimal.TEN, BigDecimal.ZERO, "Type1");
        CroCardTransaction transaction2 = new CroCardTransaction("2023-01-02", "Transaction 2", "USD", BigDecimal.valueOf(20), BigDecimal.ZERO, "Type2");
        CroCardWallet.addTransaction(transaction1);
        CroCardWallet.addTransaction(transaction2);

        // When
        ArrayList<String> consoleOutput = captureConsoleOutput(CroCardWallet::writeAmount);
        String all = consoleOutput.toString();

        // Then
        Assert.assertTrue(all.contains("-------------"));
        Assert.assertTrue(all.contains("Type1"));
        Assert.assertTrue(all.contains("Type2"));
        Assert.assertTrue(all.contains("30")); // Total amount spent
    }

    @Test
    public void testAddTransactionWithExistingWallet() {
        // Given
        CroCardTransaction transaction1 = new CroCardTransaction("2023-01-01", "Transaction 1", "USD", BigDecimal.TEN, BigDecimal.ZERO, "Type1");
        CroCardTransaction transaction2 = new CroCardTransaction("2023-01-02", "Transaction 2", "USD", BigDecimal.valueOf(20), BigDecimal.ZERO, "Type1");
        CroCardWallet.addTransaction(transaction1);

        // When
        CroCardWallet.addTransaction(transaction2);

        // Then
        Assert.assertEquals(CardTxApp.cardWallets.size(), 1);
        CroCardWallet wallet = CardTxApp.cardWallets.get(0);
        Assert.assertEquals(wallet.getAmount(), BigDecimal.valueOf(30));
        Assert.assertEquals(wallet.getMoneySpent(), BigDecimal.TEN);
        Assert.assertEquals(wallet.getTxs().size(), 2);
        Assert.assertEquals(wallet.getTxs().get(1), transaction2);
    }

    private ArrayList<String> captureConsoleOutput(Runnable action) {

        ArrayList<String> consoleOutput = new ArrayList<>();
        System.setOut(new PrintStream(new ByteArrayOutputStream() {
            @Override
            public void write(byte[] b, int off, int len) {
                consoleOutput.add(new String(b, off, len));
            }
        }));
        action.run();
        return consoleOutput;
    }
}
