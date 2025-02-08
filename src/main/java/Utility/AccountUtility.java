package Utility;

import java.util.Random;

public class AccountUtility {
    public static String generateAccountNumber(){
        int length = 10;
        Random random = new Random();
        StringBuilder AccountNumber = new StringBuilder();

        for (int i = 0; i < 10; i++){
            AccountNumber.append(random.nextInt(10));
        }
        return AccountNumber.toString();
    }
}
