package at.md;

import at.md.General.Application;
import at.md.General.CardTxApp;
import at.md.Transactions.CroCardTransaction;
import at.md.Wallet.CroCardWallet;
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
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userInstructionsCardTxApp();

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

    @Test
    public void testUserInstructionsTxApp() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Application.userInstructionsTxApp();

        System.setOut(System.out);
        String expectedOutput = """
                Press 0 to exit
                Press 1 to get all transactions from 1 wallet
                Press 2 to get transactions by transaction type
                Press 3 to get transaction by amount
                Press 4 to get transaction by native amount
                Press 5 to get transaction by transaction hash
                Press 6 to get transaction by date
                Press 7 to get all wallets
                Press 8 to get outside Wallet by name
                Press 9 for help
                """;

        assertEquals(outputStream.toString(), expectedOutput);

    }

    @Test
    public void testuserInterfaceCardTxApp0() {
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        System.setIn(in);
        Application.scanner = new java.util.Scanner(in);
        Application.userInterfaceCardTxApp();
        System.setIn(originalIn);
        System.setOut(originalOut);
        assert true;    //looks like bs but it s not, bc we want to end the program

        CroCardWallet.tts.clear();
        CardTxApp.cardWallets.clear();
        CardTxApp.transactions.clear();
    }

    @Test
    public void testuserInterfaceCardTxApp1() {
        String input = """
                1
                t
                0
                """;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        System.setIn(in);
        Application.scanner = new java.util.Scanner(in);

        CroCardWallet w = new CroCardWallet("test", BigDecimal.ZERO, "test");
        CardTxApp.cardWallets.add(w);
        CroCardWallet.addTransaction(new CroCardTransaction("01-02-2000 00:00:00", "test", "test", BigDecimal.ZERO, BigDecimal.ZERO, "test"));

        CardTxApp.cardWallets.add(w);

        Application.userInterfaceCardTxApp();
        assert true;    //looks like bs but it s not, bc we want to end the program
        System.setIn(originalIn);
        System.setOut(originalOut);

        CroCardWallet.tts.clear();
        CardTxApp.cardWallets.clear();
        CardTxApp.transactions.clear();
    }

    @Test
    public void testuserInterfaceCardTxApp2() {
        String input = """
                2
                0
                0
                """;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        System.setIn(in);
        Application.scanner = new java.util.Scanner(in);

        CroCardWallet w = new CroCardWallet("test", BigDecimal.ZERO, "test");
        CroCardTransaction tx = new CroCardTransaction("01-02-2000 00:00:00", "test", "test", BigDecimal.ZERO, BigDecimal.ZERO, "test");
        CardTxApp.cardWallets.add(w);
        CardTxApp.transactions.add(tx);

        CroCardWallet.addTransaction(tx);

        CardTxApp.cardWallets.add(w);

        Application.userInterfaceCardTxApp();
        assert true;    //looks like bs but it s not, bc we want to end the program
        System.setIn(originalIn);
        System.setOut(originalOut);

        CroCardWallet.tts.clear();
        CardTxApp.cardWallets.clear();
        CardTxApp.transactions.clear();
    }
}
