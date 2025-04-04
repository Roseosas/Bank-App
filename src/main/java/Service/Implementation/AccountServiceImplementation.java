package Service.Implementation;

import Service.AccountService;
import Utility.AccountUtility;
import models.BankAccount;
import models.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class AccountServiceImplementation implements AccountService {
    private BankAccount currentAccount;
    private User currentUser;
    private final Map<String, BankAccount> account;
    private static AccountServiceImplementation Instance;

    private AccountServiceImplementation() {
        this.account = new HashMap<>();
    }
    public static AccountServiceImplementation getInstance(){
        if (Instance==null){
            Instance = new AccountServiceImplementation();
        }
        return Instance;
    }
    @Override
    public void checkBalance(){
        System.out.println("Your current balance is: " + account.get(getCurrentUser().getUserId()).getBalance());
    }

    @Override
    public boolean transferFunds(String toAccount, String fromAccount, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(0.0)) > 0){
            BankAccount sender = account.values().stream().filter(account -> account.getAccountNumber().equals(fromAccount)).findFirst().orElse(null);
            BankAccount receiver = account.values().stream().filter(account -> account.getAccountNumber().equals(toAccount)).findFirst().orElse(null);
            if (sender!= null && receiver != null && sender.getBalance().compareTo(amount)>0) {
                sender.debit(amount);
                receiver.credit(amount);
                System.out.println("Transfer successful");
                System.out.println("Balance = " + sender.getBalance());
                System.out.println("Balance = " + receiver.getBalance());
                return true;
            }

        }
        System.out.println("Insufficient funds or invalid account.");
        return false;
    }

    @Override
    public BankAccount createAccount(String userId){
        currentUser = getCurrentUser();
        if (currentUser.getUserId().equals(userId)){
            BankAccount newAccount = new BankAccount(userId, AccountUtility.generateAccountNumber(), BigDecimal.valueOf(0.0));
            currentAccount = newAccount;
            account.put(userId, newAccount);
            System.out.println("Account created successfully");
            System.out.println(account);
            return currentAccount;
        }else {
            System.out.println("Invalid UserID");
            return null;
        }

    }

    @Override
    public boolean setBalance(BigDecimal amount, String password){
        if (currentUser.getPassword().equals(password)){
            currentAccount.setBalance(amount);
            System.out.println("Your balance has been set to " +amount);
            return true;
        }
        System.out.println("Failed to set Balance");
        return false;
    }
    @Override
    public User getCurrentUser(){
        return UserServiceImplementation.getInstance().getCurrentUser();
    }
    @Override
    public Map<String, BankAccount> getAccounts(){
        return account;
    }
    @Override
    public BankAccount createAccount(String userId, String accountNumber, BigDecimal balance){
        BankAccount newAccount = new BankAccount(userId, accountNumber, balance);
        account.put(userId, newAccount);
        return newAccount;

    }

}
