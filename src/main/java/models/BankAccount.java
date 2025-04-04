package models;

import java.math.BigDecimal;

public class BankAccount {
    private final String accountNumber;
    private BigDecimal balance;
    private String userId;

    public BankAccount(String userId, String accountNumber, BigDecimal balance){
        this.userId = userId;
        this.accountNumber= accountNumber;
        this.balance= balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance(){

        return balance;
    }
    public void credit(BigDecimal amount){
        this.balance= this.balance.add(amount);
    }
    public void debit(BigDecimal amount){
        this.balance= this.balance.subtract(amount);

    }
    public void setBalance(BigDecimal amount){
        this.balance= amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", userId='" + userId + '\'' +
                '}';
    }
}
