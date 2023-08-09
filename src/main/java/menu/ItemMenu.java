package menu;

import facade.Facade;
import input.UserInput;

public class ItemMenu {

    static final String EOL = System.lineSeparator();
    static final Facade facade = Launcher.facade;

    public static void itemMenu() throws Exception {

        final String ITEM_MENU = "Item options menu: " + EOL +
                "0. Return to Main Menu." + EOL +
                "1. Create an Item." + EOL +
                "2. Remove an Item." + EOL +
                "3. Print all registered Items." + EOL +
                "4. Buy an Item." + EOL +
                "5. Update an item’s name." + EOL +
                "6. Update an item’s price." + EOL +
                "7. Print a specific item." + EOL;

        int userAnswer;

        do {
            UserInput.printInfo(ITEM_MENU);
            userAnswer = UserInput.readInt("Input option Number: ");

            switch (userAnswer) {

                case 0:
                    Launcher.mainMenu();
                    break;

                case 1: // create item
                    String itemID = UserInput.readString("Type item id: ");
                    String itemName = UserInput.readString("Type item name: ");
                    double unitPrice = UserInput.readDouble("Type price per unit: ");
                    facade.createItem(itemID, itemName, unitPrice);
                    break;

                case 2: // remove item
                    String idToRemove = UserInput.readString("Type id of item to remove: ");
                    facade.removeItem(idToRemove);
                    break;

                case 3: // print all
                    facade.printAllItems();
                    break;

                case 4: //buy an item
                    String idToBuy = UserInput.readString("Type an id of item to buy: ");
                    int amount = UserInput.readInt("Type the amount: ");
                    double resultBuy = Launcher.facade.buyItem(idToBuy, amount);
                    System.out.println(resultBuy);
                    facade.removeItem(idToBuy);
                    break;

                case 5: // update name
                    String idToChangeName = UserInput.readString("Type id of an item to update name:");
                    String nameToUpdate = UserInput.readString("Type a name of an item to update:");
                    String newName = UserInput.readString("Type new name of an item:");
                    facade.updateItemName(idToChangeName, newName);
                    break;

                case 6: // update price
                    String idToChangePrice = UserInput.readString("Type id of an item to update price:");
                    double newPrice = UserInput.readDouble("Type new price of an item:");
                    facade.updateItemPrice(idToChangePrice, newPrice);
                    break;

                case 7: // print a specific item
                    String idToPrint = UserInput.readString("Type id of an item to print: ");
                    facade.printItem(idToPrint);
                    break;

                default:
                    UserInput.printInfo("Invalid menu option. Please type another option: ");
                    itemMenu();
            }
        } while (userAnswer != 0);
    }
}


