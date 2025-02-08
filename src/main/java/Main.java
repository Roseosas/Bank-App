import Enums.Role;
import Service.AccountService;
import Service.FileService;
import Service.Implementation.AccountServiceImplementation;
import Service.Implementation.FileServiceImplementation;
import Service.Implementation.UserServiceImplementation;
import Service.UserService;
import models.User;

import java.math.BigDecimal;
import java.util.Scanner;

import static Enums.Role.Admin;

public class Main {

    private static  final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = UserServiceImplementation.getInstance();
    private static final AccountService accountService = AccountServiceImplementation.getInstance();
    private static final FileService fileService = new FileServiceImplementation("src/main/resources/CSVFolder/Users", "src/main/resources/CSVFolder/Accounts");
    private static int x =1;
    private static int choice;



    public static void main(String[] args) {
        //fileService.loadInitialData();
        mainMenu();

    }


    private static void registerUser () {
        System.out.println("Enter your name");
        String name = scanner.nextLine();

        System.out.println("Enter your email");
        String email = scanner.nextLine();

        System.out.println("Enter a valid password");
        String password = scanner.nextLine();

        System.out.println("Enter your ID");
        String userId = scanner.nextLine();

        System.out.println("Enter your role");
        String role = scanner.nextLine();

        userService.registerUser(email, password, name, userId, Role.valueOf(role));
    }

    private static void login () {
        System.out.println("Enter your email");
        String email = scanner.nextLine();

        System.out.println("Enter your password");
        String password = scanner.nextLine();

        userService.login(email, password);
        if (userService.getCurrentUser().getRole() == Admin) {
            x = 3;
            showAdminMenu();
        } else {
            x = 1;
            showCustomerMenu();
        }
    }
    private static void setBalance () {
        System.out.println("Enter amount");
        BigDecimal amount = BigDecimal.valueOf(scanner.nextInt());
        System.out.println("Enter your password");
        scanner.nextLine();
        String password = scanner.nextLine();
        accountService.setBalance(amount, password);
    }

    public static void transferFunds () {
        System.out.println(("Enter sender's account number"));
        String senderAccount = scanner.nextLine();
        System.out.println("Enter beneficiary account number");
        String beneficiaryAccount = scanner.nextLine();
        System.out.println("Enter the amount");
        BigDecimal amount = BigDecimal.valueOf(scanner.nextInt());
        AccountServiceImplementation.getInstance().transferFunds(senderAccount, beneficiaryAccount, amount);
    }

    // Admin functionality: View all users
    private static void viewUsers () {
        System.out.println("All users:");
        for (User user : userService.viewUsers()) {
            System.out.println(user.getName() + " (" + user.getRole() + ")");
        }
    }

    // Admin functionality: Delete a user
    private static void deleteUser () {
        System.out.print("Enter user ID to delete: ");
        String userId = scanner.nextLine();

        userService.deleteUser(userId);
    }

    private static void createAccount () {
        System.out.println("Enter your User ID: ");
        String userId = scanner.nextLine();
        accountService.createAccount(userId);
    }


    // Customer Menu Options
    private static void showCustomerMenu () {
        while (x==1){
            System.out.println("1. Login as a user");
            System.out.println("2. Transfer Funds");
            System.out.println("3. Check balance");
            System.out.println("4. Create an account");
            System.out.println("5. Set balance");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    transferFunds();
                    break;
                case 3:
                    accountService.checkBalance();
                    break;
                case 4:
                    createAccount();
                    break;
                case 5:
                    setBalance();
                case 6:
                    logout();
                default:
                    System.out.println("Invalid option.");
                    break;
        }

        }

    }

    public static void mainMenu(){
        while (x == 2) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("Choose an option:");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Thanks for banking with us. Have a nice day!");
                    return;
            }
        }
    }

    // Admin Menu Options
    private static void showAdminMenu () {
        while (x==3){
            System.out.println("1. Register new user");
            System.out.println("2. Login user");
            System.out.println("3. View All Users");
            System.out.println("4. Delete User");
            System.out.println("5. Reload data");
            System.out.println("6. Export data");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    viewUsers();
                    break;
                case 4:
                    deleteUser();
                case 5:
                    fileService.loadInitialData();
                case 6:
                    fileService.exportData();
                case 7:
                    logout();
                default:
                    System.out.println("Invalid option.");
                    break;
            }


        }
    }

    public static void logout(){
        x=1;
    }
}