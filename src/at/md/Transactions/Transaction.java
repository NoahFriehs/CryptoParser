package at.md.Transactions;

import at.md.Util.Converter;
import at.md.Util.CurrencyType;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

    Date date;
    String description;
    CurrencyType currencyType;
    java.math.BigDecimal amount;
    java.math.BigDecimal nativeAmount;
    TransactionType transactionType;
    String transHash;

    CurrencyType toCurrency;

    java.math.BigDecimal toAmount;

    public Transaction(String date, String description, CurrencyType currencyType, BigDecimal amount, BigDecimal nativeAmount, TransactionType transactionType) {

        this.date = Converter.dateConverter(date);
        this.description = description;
        this.currencyType = currencyType;
        this.amount = BigDecimal.ZERO;
        this.amount = this.amount.add(amount);
        this.nativeAmount = BigDecimal.ZERO;
        this.nativeAmount = this.nativeAmount.add(nativeAmount);
        this.transactionType = transactionType;
    }

    public Date getDate() {
        return date;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public java.math.BigDecimal getNativeAmount() {
        return nativeAmount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransHash() {
        return transHash;
    }

    public CurrencyType getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(CurrencyType toCurrency) {
        this.toCurrency = toCurrency;
    }

    public BigDecimal getToAmount() {
        return toAmount;
    }

    public void setToAmount(BigDecimal toAmount) {
        this.toAmount = toAmount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", description='" + description + '\'' +
                ", currencyType=" + currencyType +
                ", amount=" + amount +
                ", nativeAmount=" + nativeAmount +
                ", transactionType=" + transactionType +
                ", transHash='" + transHash + '\'' +
                ", toCurrency=" + toCurrency +
                ", toAmount=" + toAmount +
                '}';
    }
}
