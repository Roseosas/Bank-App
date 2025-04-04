package Service.Implementation;

import Enums.Role;
import Service.FileService;
import Service.UserService;
import models.Admin;
import models.Customer;
import models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class UserServiceImplementation implements UserService {
    private Map<String, User> users = new HashMap<>();
    private User currentUser;
    private static final FileService fileService = new FileServiceImplementation("src/main/resources/CSVFolder/Users", "src/main/resources/CSVFolder/Accounts");


    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

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
        if (!validEmail(email)) {
            System.out.println("Invalid Email");
            return false;
        }

        if (!validPassword(password)){
            System.out.println("Invalid password");
            return false;
        }
        for (User user : users.values()){
            if(user.getEmail().equals(email)){
                System.out.println("User already exist");
                return false;
            }
        }
        User newUser;
        if(role.equals(Role.ADMIN)){
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
        if(!currentUser.getRole().equals(Role.ADMIN)){
            System.out.println("You are not an Admin");
            return null;
        }
        return users.values().stream().toList();
    }



    public void deleteUser(String userId) {
        if (currentUser.getRole().equals(Role.ADMIN)) {
            if (users.get(userId) != null) {
                users.remove(userId);
                System.out.println("User deleted");
                return;
            }
            System.out.println("User does not exist");
            return;
        }
        System.out.println("You are not an Amin");
    }
    private boolean validEmail(String email){
        return email != null && Pattern.matches(EMAIL_REGEX, email);

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

    @Override
    public User getUserByEmail(String email) {
        fileService.loadInitialUsers();
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}
