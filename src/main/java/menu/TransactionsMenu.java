package menu;

import facade.Facade;
import input.UserInput;

public class TransactionsMenu {

    static final String EOL = System.lineSeparator();
    static final Facade facade = Launcher.facade;

    public static void transactionsMenu() throws Exception {

        final String TRANSACTIONS_MENU = "Transaction History options menu:" + EOL +
                "0. Return to Main Menu." + EOL +
                "1. Print total profit from all item purchases." + EOL +
                "2. Print total units sold from all item purchases." + EOL +
                "3. Print the total number of item transactions made." + EOL +
                "4. Print all transactions made." + EOL +
                "5. Print the total profit of a specific item." + EOL +
                "6. Print the number of units sold of a specific item." + EOL +
                "7. Print all transactions of a specific item." + EOL +
                "8. Print item with the highest profit." + EOL;

        int userAnswer;

        do {
            UserInput.printInfo(TRANSACTIONS_MENU);
            userAnswer = UserInput.readInt("Input option Number: ");

            switch (userAnswer) {
                case 0:
                    Launcher.mainMenu();
                    break;
                case 1: // Print total profit from all item purchases
                    facade.getTotalProfit();
                    break;
                case 2: // Print total units sold from all item purchases
                    facade.getTotalUnitsSold();
                    break;
                case 3: // Print the total number of item transactions made
                    facade.getTotalTransactions();
                    break;
                case 4: // Print all transactions made
                    facade.printAllTransactions();
                    break;
                case 5: // Print the total profit of a specific item
                    String idToGetProfit = UserInput.readString("Type id of an item to print total profit: ");
                    facade.getProfit(idToGetProfit);
                    break;
                case 6: // Print the number of units sold of a specific item
                    String idToPrintUnitsSold = UserInput.readString("Type id of an item to print the number of units sold: ");
                    facade.getUnitsSolds(idToPrintUnitsSold);
                    break;
                case 7: // Print all transactions of a specific item
                    String idToPrintTransactions = UserInput.readString("Type id of an item to print all transactions: ");
                    facade.printItemTransactions(idToPrintTransactions);
                    break;
                case 8: // Print item with the highest profit
                    System.out.println(facade.printMostProfitableItems());
                    break;
                default:
                    UserInput.printInfo("Invalid menu option. Please type another option: ");
                    transactionsMenu();
            }
        } while (userAnswer != 0);
    }
}