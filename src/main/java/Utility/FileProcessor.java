package Utility;

import Enums.Role;
import Service.AccountService;
import Service.Implementation.AccountServiceImplementation;
import Service.Implementation.UserServiceImplementation;
import Service.UserService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileProcessor {
    private final UserService userService = UserServiceImplementation.getInstance();
    private final AccountService accountService = AccountServiceImplementation.getInstance();
    private static FileProcessor Instance;

    private FileProcessor() {

    }

    public static FileProcessor getInstance() {
        if (Instance == null) {
            Instance = new FileProcessor();
        }
        return Instance;
    }



    public void processFile(File file, String fileType) {

        String filepath = file.getAbsolutePath();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filepath));

            if(fileType.equalsIgnoreCase("user")){
                for (int i=1;i<lines.size();i++) {
                    String[] parts = lines.get(i).split(",");
                    String userId = parts[0].trim();
                    String name = parts[1].trim();
                    String email = parts[2].trim();
                    String password = parts[3].trim();
                    String role = parts[4].trim();
                    userService.registerUser(email,password,name,userId, Role.valueOf(role));
                }
            }
            if (fileType.equalsIgnoreCase("account")) {
                for (int i=1;i<lines.size();i++) {
                    String[] parts = lines.get(i).split(",");
                    String userId = parts[0].trim();
                    String accountId = parts[1].trim();
                    BigDecimal balance = BigDecimal.valueOf(Double.parseDouble(parts[2].trim()));
                    accountService.createAccount(userId,accountId,balance);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading initial data: " + e.getMessage());
        }
    }
}
