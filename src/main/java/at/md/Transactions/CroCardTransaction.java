package at.md.Transactions;

import java.math.BigDecimal;

public class CroCardTransaction extends Transaction {

    String transactionType;

    public CroCardTransaction(String date, String description, String currencyType, BigDecimal amount, BigDecimal nativeAmount, String transactionType) {
        super(date, description, currencyType, amount, nativeAmount, TransactionType.STRING);
        this.transactionType = transactionType;
    }


    public String getTransactionTypeString() {
        return transactionType;
    }

    @Override
    public String toString() {
        return "CardTransaction{" +
                "date=" + date +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", transactionType=" + transactionType +
                ", toCurrency=" + toCurrency +
                ", toAmount=" + toAmount +
                '}';
    }
}
