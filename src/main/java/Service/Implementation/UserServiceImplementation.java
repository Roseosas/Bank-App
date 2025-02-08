package Service.Implementation;

import Enums.Role;
import Service.UserService;
import models.Admin;
import models.Customer;
import models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImplementation implements UserService {
    private Map<String, User> users = new HashMap<>();
    private User currentUser;

    private static UserServiceImplementation Instance;

    private UserServiceImplementation(){
        this.users = new HashMap<>();
    }
    public static  UserServiceImplementation getInstance(){
        if (Instance == null){
            Instance = new UserServiceImplementation();
        }
        return Instance;
    }

    public boolean registerUser(String email, String password, String name, String userId, Role role){
        if (!validEmail(email) || validPassword(password)){
            System.out.println("Invalid email or password");
            return false;
        }
        for (User user : users.values()){
            if(user.getEmail().equals(email)){
                System.out.println("User already exist");
                return false;
            }
        }
        User newUser;
        if(role.equals(Role.Admin)){
            newUser = new Admin(userId, name, email, password);
        }else{
            newUser = new Customer(userId, name, email, password);
        }
        users.put(newUser.getUserId(), newUser);
        return true;
    }

    public User login(String email, String password){
        for (User user : users.values()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)){
                currentUser= user;
                System.out.println("Login successful");
            }

        }
        return currentUser;
    }
    @Override
    public List<User> viewUsers(){
        if(!currentUser.getRole().equals(Role.Admin)){
            System.out.println("You are not an Admin");
            return null;
        }
        System.out.println(users.values().stream().toList());
        return users.values().stream().toList();
    }



    public boolean deleteUser(String userId) {
        if (currentUser.getRole().equals(Role.Admin)) {
            if (users.get(userId) != null) {
                users.remove(userId);
                System.out.println("User deleted");
                return true;
            }
            System.out.println("User does not exist");
            return false;
        }
        System.out.println("You are not an Amin");
        return false;
    }
    private boolean validEmail(String email){
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(-+)$");

    }
    private boolean validPassword(String password){
        return password != null && password.length() >= 6;
    }
    @Override
    public User getCurrentUser(){
        return currentUser;
    }
    @Override
    public Map<String, User> getUsers(){
        return  users;
    }
}
