package input;

import java.util.Scanner;

public class UserInput {

    public static Scanner userInput = new Scanner(System.in);

    public static int readInt(String message) {
        System.out.println(message);
        int intValue = userInput.nextInt();
        userInput.nextLine();
        return intValue;
    }

    public static double readDouble(String message) {
        System.out.println(message);
        double doubleValue = userInput.nextDouble();
        userInput.nextLine();
        return doubleValue;
    }

    public static String readString(String message) {
        System.out.println(message);
        String lineValue = userInput.nextLine();
        return lineValue;
    }

    public static String printInfo(String message) {
        System.out.println(message);
        return message;
    }
}