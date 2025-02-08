package models;

import Enums.Role;

public class Admin  extends User{

    public Admin(String userId, String name, String email, String password){
        super(userId,name,email, password, Role.Admin);
    }
}
