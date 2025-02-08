package Service.Implementation;

import Service.AccountService;
import Service.FileService;
import Service.UserService;
import Utility.FileProcessor;
import models.BankAccount;
import models.User;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileServiceImplementation implements FileService {

    private static final int numOfThreads = 10;
    private final ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
    private final String userFilesPath;
    private final String accountFilesPath;
    private final FileProcessor fileProcessor = FileProcessor.getInstance();
    private final UserService userService = UserServiceImplementation.getInstance();
    private final AccountService accountService = AccountServiceImplementation.getInstance();


    public FileServiceImplementation(String userFilesPath, String accountFilesPath) {
        this.userFilesPath = userFilesPath;
        this.accountFilesPath = accountFilesPath;
    }


    @Override
    public void loadInitialData() {
        File[] accountFiles = getCSVFiles(accountFilesPath);
        File[] userFiles = getCSVFiles(userFilesPath);
        for (File file : accountFiles) {
            executorService.submit(() -> fileProcessor.processFile(file, "account"));
        }
        for (File file : userFiles) {
            executorService.submit(() -> fileProcessor.processFile(file, "user"));
        }
    }

    public void exportData(){
        writeAccountFiles();
        writeUserFiles();
    }


    private File[] getCSVFiles(String folderPath) {

        // Create a File object representing the folder
        File folder = new File(folderPath);

        // Use a FilenameFilter to filter only CSV files
        FilenameFilter csvFilter = (dir, name) -> name.toLowerCase().endsWith(".csv");

        // Get an array of all .csv files
        return folder.listFiles(csvFilter);
    }

    public void writeAccountFiles(){
        String filePath = "src/main/resources/CSVFolder/Accounts/account.csv";
        try(FileWriter writer = new FileWriter(filePath)){
            for (Map.Entry<String, BankAccount> entry : accountService.getAccounts().entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + System.lineSeparator());
            }
        }catch (IOException e){
            System.err.println(e);
        }
    }
    public void writeUserFiles(){
        String filePath = "src/main/resources/CSVFolder/Users/User.csv";
        try(FileWriter writer = new FileWriter(filePath)){
            for (Map.Entry<String, User> entry : userService.getUsers().entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + System.lineSeparator());
            }
        }catch (IOException e){
            System.err.println(e);
        }
    }
}
