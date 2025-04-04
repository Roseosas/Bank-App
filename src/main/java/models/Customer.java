package models;

import Enums.Role;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private final List<BankAccount> account;

    public Customer(String userId, String name, String email, String password) {
        super(userId, name, email, password, Role.CUSTOMER);
        this.account = new ArrayList<>();
    }
    public List<BankAccount> getAccount() {
        return account;
    }
}
