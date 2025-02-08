package models;

import Enums.Role;

public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private Role role;


    public  User(String userId, String name, String email, String password, Role role){
        this.userId= userId;
        this.name=name;
        this.email= email;
        this.password = password;
        this.role= role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole (Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}'+ System.lineSeparator();
    }

}
