package at.md.General;

import at.md.Transactions.CroCardTransaction;
import at.md.Util.IOHandler;
import at.md.Wallet.CroCardWallet;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;



public class CardTxApp {

    public static ArrayList<CroCardWallet> cardWallets = new ArrayList<>();
    public static ArrayList<CroCardTransaction> transactions = new ArrayList<>();

    /**
     * Can lead to some false wallets bc of Currencies TODO
     */
    public static boolean useStrictWalletType;

    CardTxApp(String filepath, boolean useStrictWallet) {
        useStrictWalletType = useStrictWallet;
        try {
            transactions = getTransactions(IOHandler.readFile(filepath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("We have " + transactions.size() + " transaction(s).");
        fillWallet(transactions);
        System.out.println("we have " + cardWallets.size() + " different transactions.");
        //CardWallet.writeAmount();
    }


    /**
     * Csv file to CroCardTransaction list
     *
     * @param input csv file as String list
     * @return CroCardTransaction list
     */
    private static ArrayList<CroCardTransaction> getTransactions(ArrayList<String> input) {
        input.remove(0);
        ArrayList<CroCardTransaction> transactions = new ArrayList<>();

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
                if (sa.length == 9) {
                    CroCardTransaction t = new CroCardTransaction(sa[0], sa[1], sa[2], (BigDecimal) decimalFormat.parse(sa[7]), (BigDecimal) decimalFormat.parse(sa[7]), (sa[1]));

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


    private static void fillWallet(ArrayList<CroCardTransaction> tr) {
        for (CroCardTransaction t : tr) {
            CroCardWallet.addTransaction(t);
        }
    }


}
