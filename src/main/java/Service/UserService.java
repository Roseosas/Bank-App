package Service;

import Enums.Role;
import models.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    boolean registerUser(String email, String password, String name, String userId, Role role);
    User login(String email, String password);
    List<User> viewUsers();
    boolean deleteUser(String userId);
    User getCurrentUser();
    Map<String, User> getUsers();
}
