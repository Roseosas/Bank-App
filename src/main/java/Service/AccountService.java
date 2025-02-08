package Service;

import models.BankAccount;
import models.User;

import java.math.BigDecimal;
import java.util.Map;

public interface AccountService {
    void checkBalance();
    boolean transferFunds(String toAccount, String fromAccount, BigDecimal amount);
    BankAccount createAccount(String UserId);
    boolean setBalance(BigDecimal amount, String password);
    User getCurrentUser();
    Map<String,BankAccount> getAccounts();
    BankAccount createAccount(String userId, String accountNumber, BigDecimal balance);
}
