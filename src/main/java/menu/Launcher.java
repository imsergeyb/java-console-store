package menu;

import input.UserInput;
import facade.Facade;

public class Launcher {

    public static void main(String[] args) throws Exception {
        Launcher.mainMenu();
    }

    static final String EOL = System.lineSeparator();
    static final Facade facade = new Facade();

    public static void mainMenu() throws Exception {

        String MAIN_MENU = "Main Menu: Please choose among the options below." + Launcher.EOL +
                "0. Close system." + Launcher.EOL +
                "1. Open Item options." + Launcher.EOL +
                "2. Open Review options." + Launcher.EOL +
                "3. Open Transaction History options." + Launcher.EOL +
                "4. Open Employee options." + Launcher.EOL;

        UserInput.printInfo(MAIN_MENU);

        int userAnswer;
        userAnswer = UserInput.readInt("Input option Number: ");

        switch (userAnswer) {
            case 0:
                UserInput.printInfo("The system has been closed.");
                break;
            case 1:
                ItemMenu.itemMenu();
                break;
            case 2:
                ReviewsMenu.reviewsMenu();
                break;
            case 3:
                TransactionsMenu.transactionsMenu();
                break;
            case 4:
                EmployeeOptions.employeeOptions();
                break;
            default:
                UserInput.printInfo("Invalid menu option. Please type another option: ");
                mainMenu();
                break;
        }
    }
}
