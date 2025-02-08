package models;

import java.math.BigDecimal;

public class BankAccount {
    private final String accountNumber;
    private BigDecimal balance;

    public BankAccount(String accountNumber, BigDecimal balance){
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


    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}
