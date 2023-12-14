package at.md.Wallet;

import at.md.General.CardTxApp;
import at.md.Transactions.CroCardTransaction;

import java.math.BigDecimal;
import java.util.ArrayList;

import static at.md.General.CardTxApp.cardWallets;

public class CroCardWallet extends Wallet {

    public static ArrayList<String> tts = new ArrayList<>();
    String transactionType;
    ArrayList<CroCardTransaction> txs = new ArrayList<>();

    public CroCardWallet(String currencyType, BigDecimal amount, String transactionType) {
        super(currencyType, amount, amount);
        this.transactionType = transactionType;
        if (!tts.contains(transactionType)) {
            tts.add(transactionType);
        }
    }

    public static void addTransaction(CroCardTransaction transaction) {

        String tt = transaction.getTransactionTypeString();
        if (tts.contains(tt)) {
            CroCardWallet w = cardWallets.get(tts.indexOf(tt));
            w.addToCardWallet(transaction.getAmount());
            w.txs.add(transaction);
        } else {
            CroCardWallet w;
            if (!CardTxApp.useStrictWalletType)
            {
                w = getNonStrictWallet(tt);
                if (w == null){
                    w = new CroCardWallet("EUR", transaction.getAmount(), tt);
                    w.txs.add(transaction);
                    cardWallets.add(w);
                }
            } else {
                w = new CroCardWallet("EUR", transaction.getAmount(), tt);
                w.txs.add(transaction);
                cardWallets.add(w);
            }
            //w.addToCardWallet(transaction.getAmount());

        }
    }

    public static void writeAmount() {
        BigDecimal amountSpent = BigDecimal.ZERO;
        for (CroCardWallet w : cardWallets) {
            System.out.println("-".repeat(20));
            System.out.println(w.transactionType);
            System.out.println(w.amount);
            System.out.println(w.moneySpent);
            System.out.println("Transactions: " + w.txs.size());
            amountSpent = amountSpent.add(w.moneySpent);
        }
        System.out.println("-".repeat(20));
        System.out.println("Amount total spent: " + amountSpent);
    }

    public void addToCardWallet(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    public ArrayList<CroCardTransaction> getTxs() {
        return txs;
    }

    public String getTransactionType() {
        return transactionType;
    }

    private static CroCardWallet getNonStrictWallet(String tt){

        if (tt.equals("EUR -> EUR")) return null;   //TODO: fix Currencies

        for (CroCardWallet w : cardWallets) {
            boolean maybe = tt.contains(" ");
            if (maybe && w.transactionType.contains(tt.substring(0, tt.indexOf(" ")))) {
                return w;
            }
            if (w.transactionType.contains(tt)){
                return w;
            }
        }

        return null;
    }

    public void resetAll() {
        reset();
        txs.clear();
    }

    public static void reset() {
        tts.clear();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getMoneySpent() {
        return moneySpent;
    }
}
