package at.md.Transactions;

import at.md.Util.CurrencyType;

import java.math.BigDecimal;

public class CardTX extends Transaction {

    String transactionType;

    public CardTX(String date, String description, CurrencyType currencyType, BigDecimal amount, BigDecimal nativeAmount, String transactionType) {
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
