package at.md.General;

import at.md.Transactions.Transaction;
import at.md.Transactions.TransactionType;
import at.md.Util.CurrencyType;
import at.md.Util.IOHandler;
import at.md.Util.TimeSpan;
import at.md.Wallet.Wallet;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;

import static at.md.Util.Converter.ttConverter;


public class TxApp {

    public static ArrayList<Wallet> wallets = new ArrayList<>();
    public static ArrayList<Wallet> outsideWallets = new ArrayList<>();
    public static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        try {
            TimeSpan timeSpan = new TimeSpan();
            timeSpan.start();
            transactions = getTransactions(IOHandler.readFile(args[0]));
            System.out.println( "getTransactions msecs: " + timeSpan.end());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("We have " + transactions.size() + " transaction(s).");
        TimeSpan timeSpan = new TimeSpan();
        timeSpan.start();
        createWallets();
        System.out.println( "createWallets msecs: " + timeSpan.end());
        timeSpan.start();
        fillWallet(transactions);
        System.out.println( "fillWallet msecs: " + timeSpan.end());

    }


    /**
     * Csv file to Transaction list
     *
     * @param input csv file as String list
     * @return Transactions list
     */
    public static ArrayList<Transaction> getTransactions(ArrayList<String> input) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        if (input == null || input.isEmpty()) return transactions;
        if (input.get(0).startsWith("Timestamp (UTC)"))
            input.remove(0);
        else {
            throw new RuntimeException("Wrong file");
        }

        // Create a DecimalFormat that fits your requirements
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        for (String transaction : input) {
            try {
                String[] sa = transaction.split(",");
                if (sa.length == 10 || sa.length == 11) {
                    Transaction t;
                    if (Double.parseDouble(sa[3]) == 0) {
                        if (Double.parseDouble(sa[7]) == 0) {
                            t = new Transaction(sa[0], sa[1], sa[2], BigDecimal.ZERO, BigDecimal.ZERO, ttConverter(sa[9]));
                        } else {
                            t = new Transaction(sa[0], sa[1], sa[2], BigDecimal.ZERO, (BigDecimal) decimalFormat.parse(sa[7]), ttConverter(sa[9]));
                        }
                    } else {
                        t = new Transaction(sa[0], sa[1], sa[2], (BigDecimal) decimalFormat.parse(sa[3]), (BigDecimal) decimalFormat.parse(sa[7]), ttConverter(sa[9]));
                    }
                    if (sa.length ==  11) t.setTransHash(sa[10]);
                    if (ttConverter(sa[9]) == TransactionType.viban_purchase) {
                        t.setToCurrency(sa[4]);
                        t.setToAmount(BigDecimal.valueOf(Double.parseDouble(sa[5])));
                    }
                    transactions.add(t);

                } else {
                    System.out.println(Arrays.toString(sa));
                    System.out.println(sa.length);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return transactions;
    }

    public static void createWallets() {
        for (String t : CurrencyType.currencys) {
            wallets.add(new Wallet(t, BigDecimal.ZERO, BigDecimal.ZERO));
        }
        for (String t : CurrencyType.currencys) {
            outsideWallets.add(new Wallet(t, BigDecimal.ZERO, BigDecimal.ZERO));
        }
    }

    public static void fillWallet(ArrayList<Transaction> tr) {
        for (Transaction t : tr) {
            wallets.get(0).addTransaction(t);
        }
    }

}
