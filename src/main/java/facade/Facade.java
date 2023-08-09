package facade;

import businessLogic.*;
import exceptions.EmployeeEmptyListException;
import exceptions.EmployeeInformationException;
import exceptions.EmployeeNotFoundException;
import input.UserInput;
import menu.Launcher;

import java.util.*;

public class Facade {

    static final String EOL = System.lineSeparator();

    private ArrayList<Item> items;
    private ArrayList<Transaction> transactions;
    public ArrayList<Employee> employees;


    // This class only has the skeleton of the methods used by the test.
    // You must fill in this class with your own code. You can (and should) create more classes
    // that implement the functionalities listed in the Facade and in the Test Cases.

    public Facade() {
        this.items = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    //////////////////////////////// methods that were added to the initial Facade class//////////////////////////

    public static double truncateValue(double value, int decimal) {
        double truncated = ((int) (value * Math.pow(10, decimal)) / Math.pow(10, decimal));
        return truncated;
    }

    public Item retrieveItem(String itemID) {
        Item neededItem = null;
        for (Item currentItem : items) {
            if (currentItem.getItemId().equals(itemID)) {
                neededItem = currentItem;
            }
        }
        return neededItem;
    }

    public int getAllReviews() {
        int allReviewsAmount = 0;
        for (Item currentItem : items) {
            allReviewsAmount += getNumberOfReviews(currentItem.getItemId());
        }
        return allReviewsAmount;
    }

    public boolean containsTransaction(String itemID) {
        boolean hasTransaction = false;
        for (Transaction transaction : transactions) {
            if (transaction.getItemID().equals(itemID)) {
                hasTransaction = true;
            }
        }
        return hasTransaction;
    }

    public boolean containsEmployee(String id) {
        boolean hasEmployee = false;
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                hasEmployee = true;
            }
        }
        return hasEmployee;
    }

    public Employee retrieveEmployee(String id) {
        Employee neededEmployee = null;
        for (Employee currentEmployee : employees) {
            if (currentEmployee.getId().equals(id)) {
                neededEmployee = currentEmployee;
            }
        }
        return neededEmployee;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public String createItem(String itemID, String itemName, double unitPrice) {
        if (itemID.isEmpty() || itemName.isEmpty() || unitPrice <= 0) {
            return "Invalid data for item.";
        } else {
            if (containsItem(itemID)) {
                return "An item with ID " + itemID + " is occupied. Try ID that differs from " + itemID;
            } else {
                items.add(new Item(itemID, itemName, unitPrice));
                return "Item " + itemID + " was registered successfully.";
            }
        }
    }

    public String printItem(String itemID) {
        String message;
        Item itemToPrint = retrieveItem(itemID);
        if (items.contains(itemToPrint)) {
            message = itemToPrint.toString();
        } else {
            message = "Item " + itemID + " was not registered yet.";
        }
        return message;
    }

    public String removeItem(String itemID) {
        String message;
        if (containsItem(itemID)) {
            items.remove(retrieveItem(itemID));
            message = "Item " + itemID + " was successfully removed.";
        } else {
            message = "Item " + itemID + " could not be removed.";
        }
        return message;
    }

    public boolean containsItem(String itemID) {
        boolean hasItem = false;
        if (items.contains(retrieveItem(itemID))) {
            hasItem = true;
        }
        return hasItem;
    }

    public double buyItem(String itemID, int amount) {
        Item itemToBuy = retrieveItem(itemID);
        int DISCOUNT_THRESHOLD = 4;
        double totalValue = -1.0;
        if (items.contains(itemToBuy) && amount > 0) {
            if (amount > DISCOUNT_THRESHOLD) {
                totalValue = truncateValue(((amount - DISCOUNT_THRESHOLD) * itemToBuy.getUnitPrice() * 0.7
                        + itemToBuy.getUnitPrice() * DISCOUNT_THRESHOLD), 2);
                transactions.add(new Transaction(itemID, amount, totalValue));
            } else if (amount < 4) {
                totalValue = truncateValue((itemToBuy.getUnitPrice() * amount), 2);
                transactions.add(new Transaction(itemID, amount, totalValue));
            }
        }
        return totalValue;
    }

    public String reviewItem(String itemID, String reviewComment, int reviewGrade) {
        String message = "";
        Item itemToReview = retrieveItem(itemID);
        if (items.contains(itemToReview) && (reviewGrade >= 1 && reviewGrade <= 5)) {
            itemToReview.addReview(new Review(reviewComment, reviewGrade));
            message = "Your item review was registered successfully.";
        } else if (reviewGrade < 1 || reviewGrade > 5) {
            message = "Grade values must be between 1 and 5.";
        } else {
            message = "Item " + itemID + " was not registered yet.";
        }
        return message;
    }

    public String reviewItem(String itemID, int reviewGrade) {
        return reviewItem(itemID, "", reviewGrade);
    }

    public String getItemCommentsPrinted(String itemID) {
        return getItemComments(itemID).toString();
    }

    public List<String> getItemComments(String itemID) {
        Item itemToGetComments = retrieveItem(itemID);
        ArrayList<String> comments = new ArrayList<>();
        for (int i = 0; i < itemToGetComments.getReviews().size(); i++) {
            if (!itemToGetComments.getReview(i).getReviewComment().isEmpty()) {
                String comment = itemToGetComments.getReview(i).getReviewComment();
                comments.add(comment);
            }
        }
        return comments;
    }

    public double getItemMeanGrade(String itemID) {
        int sum = 0;
        for (int i = 0; i < retrieveItem(itemID).numOfReviews(); i++) {
            sum += retrieveItem(itemID).getReview(i).getReviewGrade();
        }
        return truncateValue((double) sum / retrieveItem(itemID).numOfReviews(), 1);
    }

    public int getNumberOfReviews(String itemID) {
        if (containsItem(itemID)) {
            return retrieveItem(itemID).numOfReviews();
        }
        return 0;
    }

    public String getPrintedItemReview(String itemID, int reviewNumber) {
        String message;
        int correctIndex = reviewNumber - 1;
        Item itemToPrintReview = retrieveItem(itemID);
        if (!items.contains(itemToPrintReview)) {
            message = "Item " + itemID + " was not registered yet.";
        } else if (itemToPrintReview.getReviews().isEmpty()) {
            message = "Item " + itemToPrintReview.getItemName() + " has not been reviewed yet.";
        } else if (reviewNumber >= 1 && reviewNumber <= getNumberOfReviews(itemID)) {
            message = itemToPrintReview.getReviews().get(correctIndex).toString();
        } else {
            message = "Invalid review number. Choose between 1 and " + getNumberOfReviews(itemID) + ".";
        }
        return message;
    }

    public String getPrintedReviews(String itemID) {
        String printedList = "";
        Item item = retrieveItem(itemID);
        if (!items.contains(item)) {
            printedList = "Item " + itemID + " was not registered yet.";
        } else if (items.contains(item) && item.getReviews().isEmpty()) {
            printedList = "Review(s) for " + item.toString() + EOL +
                    "The item " + item.getItemName() + " has not been reviewed yet.";
        } else {
            String listOfReviews = "";
            for (int i = 0; i < item.getReviews().size(); i++) {
                listOfReviews += item.getReviews().get(i) + EOL;
            }
            printedList = "Review(s) for " + item.toString() + EOL + listOfReviews;
        }
        return printedList;
    }

    public String printMostReviewedItems() {
        String printedList;
        if (items.isEmpty()) {
            printedList = "No items registered yet.";
        } else if (getAllReviews() == 0) {
            printedList = "No items were reviewed yet.";
        } else {
            printedList = "Most reviews: " + retrieveItem(getMostReviewedItems().get(0)).numOfReviews() + " review(s) each." + EOL;
            for (String currentItem : getMostReviewedItems()) {
                printedList += retrieveItem(currentItem).toString() + EOL;
            }
        }
        return printedList;
    }

    public List<String> getMostReviewedItems() {
        ArrayList<String> mostReviewed = new ArrayList<>();
        if (items.size() != 0) {
            int high = 0;
            for (Item currentItem : items) {
                if (currentItem.numOfReviews() >= high && currentItem.numOfReviews() != 0) {
                    high = currentItem.numOfReviews();
                }
            }
            for (Item currentItem : items) {
                if (currentItem.numOfReviews() == high && currentItem.numOfReviews() != 0) {
                    mostReviewed.add(currentItem.getItemId());
                }
            }
        }
        return mostReviewed;
    }

    public List<String> getLeastReviewedItems() {
        ArrayList<String> leastReviewed = new ArrayList<>();
        if (items.size() != 0) {
            int low = items.get(0).numOfReviews();
            for (Item currentItem : items) {
                if (currentItem.numOfReviews() <= low && currentItem.numOfReviews() != 0) {
                    low = currentItem.numOfReviews();
                }
            }
            for (Item currentItem : items) {
                if (currentItem.numOfReviews() == low && currentItem.numOfReviews() != 0) {
                    leastReviewed.add(currentItem.getItemId());
                }
            }
        }
        return leastReviewed;
    }

    public String printLeastReviewedItems() {
        String printedList;
        if (items.isEmpty()) {
            printedList = "No items registered yet.";
        } else {
            int reviewsNumber = 0;
            for (Item currentItem : items) {
                reviewsNumber += getNumberOfReviews(currentItem.getItemId());
            }
            if (reviewsNumber != 0) {
                printedList = "Least reviews: " + retrieveItem(getLeastReviewedItems().get(0)).numOfReviews() + " review(s) each." + EOL;
                for (String item : getLeastReviewedItems()) {
                    printedList += retrieveItem(item).toString() + EOL;
                }
            } else {
                printedList = "No items were reviewed yet.";
            }
        }
        return printedList;
    }

    public double getTotalProfit() {
        double totalProfit = 0.0;
        if (!transactions.isEmpty()) {
            for (Item currentItem : items) {
                totalProfit += getProfit(currentItem.getItemId());
            }
            totalProfit = truncateValue(totalProfit, 2);
        }
        return totalProfit;
    }

    public String printItemTransactions(String itemID) {
        String printedList;
        if (!containsItem(itemID)) {
            printedList = "Item " + itemID + " was not registered yet.";
        } else if (!containsTransaction(itemID)) {
            printedList = "Transactions for item: " + retrieveItem(itemID).toString() + EOL +
                    "No transactions have been registered for item " + retrieveItem(itemID).getItemId() + " yet.";
        } else {
            printedList = "Transactions for item: " + retrieveItem(itemID).toString() + EOL;
            for (Transaction transaction : transactions) {
                if (transaction.getItemID().equals(itemID)) {
                    printedList += transaction.toString() + EOL;
                }
            }
        }
        return printedList;
    }

    public int getTotalUnitsSold() {
        int totalUnitsSold = 0;
        if (!transactions.isEmpty()) {
            for (Transaction transaction : transactions) {
                totalUnitsSold += transaction.getAmount();
            }
        }
        return totalUnitsSold;
    }

    public int getTotalTransactions() {
        return transactions.size();
    }

    public double getProfit(String itemID) {
        double profit = 0.0;
        if (containsTransaction(itemID)) {
            for (Transaction transaction : transactions) {
                if (transaction.getItemID().equals(itemID)) {
                    profit += transaction.getPurchasePrice();
                }
            }
            profit = truncateValue(profit, 2);
        }
        return profit;
    }

    public int getUnitsSolds(String itemID) {
        int unitsSold = 0;
        if (containsTransaction(itemID)) {
            for (Transaction transaction : transactions) {
                if (transaction.getItemID().equals(itemID)) {
                    unitsSold += transaction.getAmount();
                }
            }
        }
        return unitsSold;
    }

    public String printAllTransactions() {
        String output;
        if (items.isEmpty()) {
            output = "All purchases made: " + EOL +
                    "Total profit: 0.00 SEK" + EOL +
                    "Total items sold: 0 units" + EOL +
                    "Total purchases made: 0 transactions" + EOL +
                    "------------------------------------" + EOL +
                    "------------------------------------" + EOL;
        } else {
            if (transactions.size() == 0) {
                output = "All purchases made: " + EOL +
                        "Total profit: 0.00 SEK" + EOL +
                        "Total items sold: 0 units" + EOL +
                        "Total purchases made: 0 transactions" + EOL +
                        "------------------------------------" + EOL +
                        "------------------------------------" + EOL;
            } else {
                output = "All purchases made: " + EOL +
                        "Total profit: " + String.format("%.2f", truncateValue(getTotalProfit(), 2)) + " SEK" + EOL +
                        "Total items sold: " + getTotalUnitsSold() + " units" + EOL +
                        "Total purchases made: " + getTotalTransactions() + " transactions" + EOL +
                        "------------------------------------" + EOL;
                for (Transaction transaction : transactions) {
                    output += transaction.toString() + EOL;
                }
                output = output + "------------------------------------" + EOL;
            }
        }
        return output;
    }

    public String printWorseReviewedItems() {
        String printedList;
        if (items.isEmpty()) {
            printedList = "No items registered yet.";
        } else if (!getWorseReviewedItems().isEmpty()) {
            printedList = "Items with worst mean reviews:" + EOL +
                    "Grade: " + getItemMeanGrade(getWorseReviewedItems().get(0)) + EOL;
            for (String item : getWorseReviewedItems()) {
                printedList += retrieveItem(item).toString() + EOL;
            }
        } else {
            printedList = "No items were reviewed yet.";
        }
        return printedList;
    }

    public String printBestReviewedItems() {
        String printedList;
        if (items.isEmpty()) {
            printedList = "No items registered yet.";
        } else if (!getBestReviewedItems().isEmpty()) {
            printedList = "Items with best mean reviews:" + EOL +
                    "Grade: " + getItemMeanGrade(getBestReviewedItems().get(0)) + EOL;
            for (String item : getBestReviewedItems()) {
                printedList += retrieveItem(item).toString() + EOL;
            }
        } else {
            printedList = "No items were reviewed yet.";
        }
        return printedList;
    }

    public List<String> getWorseReviewedItems() {
        ArrayList<String> worseReviewedItems = new ArrayList<>();
        if (getAllReviews() != 0 && !items.isEmpty()) {
            double worstValue = getItemMeanGrade(items.get(0).getItemId());
            for (Item currentItem : items) {
                if (getItemMeanGrade(currentItem.getItemId()) <= worstValue
                        && getItemMeanGrade(currentItem.getItemId()) != 0) {
                    worstValue = getItemMeanGrade(currentItem.getItemId());
                }
            }
            for (Item currentItem : items) {
                if (getItemMeanGrade(currentItem.getItemId()) == worstValue
                        && getItemMeanGrade(currentItem.getItemId()) != 0) {
                    worseReviewedItems.add(currentItem.getItemId());
                }
            }
        }
        return worseReviewedItems;
    }

    public List<String> getBestReviewedItems() {
        ArrayList<String> bestReviewedItems = new ArrayList<>();
        double bestValue = 0.0;
        if (getAllReviews() != 0 && !items.isEmpty()) {
            for (Item currentItem : items) {
                if (getItemMeanGrade(currentItem.getItemId()) >= bestValue
                        && getItemMeanGrade(currentItem.getItemId()) != 0) {
                    bestValue = getItemMeanGrade(currentItem.getItemId());
                }
            }
            for (Item currentItem : items) {
                if (getItemMeanGrade(currentItem.getItemId()) == bestValue
                        && getItemMeanGrade(currentItem.getItemId()) != 0) {
                    bestReviewedItems.add(currentItem.getItemId());
                }
            }
        }
        return bestReviewedItems;
    }

    public String printAllReviews() {
        String printedList;
        if (!items.isEmpty()) {
            printedList = "All registered reviews:" + EOL +
                    "------------------------------------" + EOL;
            for (Item currentItem : items) {
                if (!currentItem.getReviews().isEmpty()) {
                    printedList += getPrintedReviews(currentItem.getItemId()) + "------------------------------------" + EOL;
                }
            }
            if (!printedList.equals("All registered reviews:" + EOL + "------------------------------------" + EOL)) {
                return printedList;
            }
            return "No items were reviewed yet.";
        }
        return "No items registered yet.";
    }

    public String updateItemName(String itemID, String newName) {
        String message;
        Item itemToUpdateName = retrieveItem(itemID);
        if (!items.contains(itemToUpdateName)) {
            message = "Item " + itemID + " was not registered yet.";
        } else if (!itemID.isEmpty() && !newName.isEmpty()) {
            itemToUpdateName.setItemName(newName);
            message = "Item " + itemID + " was updated successfully.";
        } else {
            message = "Invalid data for item.";
        }
        return message;
    }

    public String updateItemPrice(String itemID, double newPrice) {
        String message;
        Item itemToUpdatePrice = retrieveItem(itemID);
        if (!items.contains(itemToUpdatePrice)) {
            message = "Item " + itemID + " was not registered yet.";
        } else if (!itemID.isEmpty() && newPrice > 0) {
            itemToUpdatePrice.setUnitPrice(newPrice);
            message = "Item " + itemID + " was updated successfully.";
        } else {
            message = "Invalid data for item.";
        }
        return message;
    }

    public String printAllItems() {
        String printedList;
        if (items.isEmpty()) {
            printedList = "No items registered yet.";
        } else {
            printedList = "All registered items:" + EOL;
            for (Item currentItem : items) {
                printedList += currentItem.toString() + EOL;
            }
        }
        return printedList;
    }

    public String printMostProfitableItems() {
        String printedList;
        if (items.isEmpty()) {
            printedList = "No items registered yet.";
        } else if (transactions.size() == 0) {
            printedList = "No items were bought yet.";
        } else {
            double highValue = 0.0;
            for (Item currentItem : items) {
                if (getProfit(currentItem.getItemId()) >= highValue) {
                    highValue = getProfit(currentItem.getItemId());
                }
            }
            printedList = "Most profitable items: " + EOL + "Total profit: " + String.format("%.2f", truncateValue(highValue, 2)) + " SEK" + EOL;
            for (Item currentItem : items) {
                if (getProfit(currentItem.getItemId()) == highValue) {
                    printedList += currentItem.toString() + EOL;
                }
            }
        }
        return printedList;
    }

    public String createEmployee(String employeeID, String employeeName, double grossSalary) throws Exception {
        String message;
        if (!containsEmployee(employeeID)) {
            Employee newEmployee = new Employee(employeeID, employeeName, grossSalary);
            employees.add(newEmployee);
            message = "Employee " + employeeID + " was registered successfully.";
        } else {
            message = "Employee " + employeeID + " already exists.";
        }
        return message;
    }

    public String printEmployee(String employeeID) throws Exception {
        Employee empToPrint = retrieveEmployee(employeeID);
        if (!containsEmployee(employeeID) || empToPrint == null) {
            throw new EmployeeNotFoundException(employeeID);
        } else {
            return empToPrint.toString();
        }
    }

    public String createEmployee(String employeeID, String employeeName, double grossSalary, String degree) throws Exception {
        String message;
        if (!containsEmployee(employeeID)) {
            Employee manager = new Manager(employeeID, employeeName, truncateValue(grossSalary, 2), degree);
            employees.add(manager);
            message = "Employee " + employeeID + " was registered successfully.";
        } else {
            message = "Employee " + employeeID + " already exists.";
        }
        return message;
    }

    public String createEmployee(String employeeID, String employeeName, double grossSalary, int gpa) throws Exception {
        String message;
        if (!containsEmployee(employeeID)) {
            Employee intern = new Intern(employeeID, employeeName, grossSalary, gpa);
            employees.add(new Intern(employeeID, employeeName, grossSalary, gpa));
            message = "Employee " + employeeID + " was registered successfully.";
        } else {
            message = "Employee " + employeeID + " already exists.";
        }
        return message;
    }

    public double getNetSalary(String employeeID) throws Exception {
        Employee empToGetNetSalary = retrieveEmployee(employeeID);
        if (!containsEmployee(employeeID) || empToGetNetSalary == null) {
            throw new EmployeeNotFoundException(employeeID);
        } else {
            return empToGetNetSalary.calculateNetSalary();
        }
    }

    public String createEmployee(String employeeID, String employeeName, double grossSalary, String degree, String
            dept) throws Exception {
        String message;
        if (!containsEmployee(employeeID)) {
            Employee director = new Director(employeeID, employeeName, grossSalary, degree, dept);
            employees.add(director);
            message = "Employee " + employeeID + " was registered successfully.";
        } else {
            message = "Employee " + employeeID + " already exists.";
        }
        return message;
    }

    public String removeEmployee(String empID) throws Exception {
        String message;
        Employee empToRemove = retrieveEmployee(empID);
        if (!containsEmployee(empID) || empToRemove == null) {
            throw new EmployeeNotFoundException(empID);
        } else {
            employees.remove(empToRemove);
            message = "Employee " + empID + " was successfully removed.";
        }
        return message;
    }

    public String printAllEmployees() throws Exception {
        String printedList;
        if (employees.size() > 0) {
            printedList = "All registered employees:" + EOL;
            for (Employee employee : employees) {
                printedList += employee.toString() + EOL;
            }
        } else {
            throw new EmployeeEmptyListException();
        }
        return printedList;
    }

    public double getTotalNetSalary() throws Exception {
        double total = 0.0;
        if (employees.size() > 0) {
            for (Employee employee : employees) {
                total += employee.calculateNetSalary();
            }
        } else {
            throw new EmployeeEmptyListException();
        }
        return truncateValue(total, 2);
    }

    public String printSortedEmployees() throws Exception {
        if (employees.isEmpty()) {
            throw new EmployeeEmptyListException();
        } else {
            String printedList = "Employees sorted by gross salary (ascending order):" + EOL;
            employees.sort(Comparator.comparing(Employee::calculateGrossSalary));
            for (Employee employee : employees) {
                printedList += employee.toString() + EOL;
            }
            return printedList;
        }
    }


    public String updateEmployeeName(String empID, String newName) throws Exception {
        String message;
        Employee empToUpdateName = retrieveEmployee(empID);
        if (!containsEmployee(empID) || empToUpdateName == null) {
            throw new EmployeeNotFoundException(empID);
        } else if (newName.isBlank()) {
            throw new EmployeeInformationException("Name cannot be blank.");
        } else {
            empToUpdateName.setName(newName);
            message = "Employee " + empID + " was updated successfully";
        }
        return message;
    }

    public String updateInternGPA(String empID, int newGPA) throws Exception {
        String message;
        Employee empToChangeGPA = retrieveEmployee(empID);
        if (!containsEmployee(empID) || empToChangeGPA == null) {
            throw new EmployeeNotFoundException(empID);
        } else if (newGPA < 0 || newGPA > 10) {
            throw new EmployeeInformationException(newGPA + " outside range. Must be between 0-10.");
        } else {
            Intern intern = (Intern) empToChangeGPA;
            intern.setGPA(newGPA);
            message = "Employee " + empID + " was updated successfully";
        }
        return message;
    }

    public String updateManagerDegree(String empID, String newDegree) throws Exception {
        String message;
        Employee empToChangeDegree = retrieveEmployee(empID);
        if (!containsEmployee(empID) || empToChangeDegree == null) {
            throw new EmployeeNotFoundException(empID);
        } else if (newDegree.isEmpty() && !newDegree.equals("BSc") && !newDegree.equals("MSc") && newDegree.equals("PhD")) {
            throw new EmployeeInformationException("Degree must be one of the options: PhD, MSc or PhD.");
        } else {
            Manager manager = (Manager) empToChangeDegree;
            manager.setDegree(newDegree);
            message = "Employee " + empID + " was updated successfully";
        }
        return message;
    }

    public String updateDirectorDept(String empID, String newDepartment) throws Exception {
        String message;
        Employee empToChangeDept = retrieveEmployee(empID);
        if (!containsEmployee(empID) || empToChangeDept == null) {
            throw new EmployeeNotFoundException(empID);
        } else if (newDepartment.isEmpty() && !newDepartment.equals("Human Resources") && !newDepartment.equals("Technical") && !newDepartment.equals("Business")) {
            throw new EmployeeInformationException("Department must be one of the options: Business, Human Resources or Technical.");
        } else {
            Director director = (Director) empToChangeDept;
            director.setDepartment(newDepartment);
            message = "Employee " + empID + " was updated successfully";
        }
        return message;
    }

    public String updateGrossSalary(String empID, double newSalary) throws Exception {
        String message;
        Employee empToChangeSalary = retrieveEmployee(empID);
        if (!containsEmployee(empID) || empToChangeSalary == null) {
            throw new EmployeeNotFoundException(empID);
        } else if (newSalary < 0) {
            throw new EmployeeInformationException("Salary must be greater than 0.");
        } else {
            empToChangeSalary.setGrossSalary(newSalary);
            message = "Employee " + empID + " was updated successfully";
        }
        return message;
    }

    public Map<String, Integer> mapEachDegree() throws Exception {
        if (employees.isEmpty()) {
            throw new EmployeeEmptyListException();
        } else {
            int bachelors = 0;
            int masters = 0;
            int phds = 0;
            for (Employee employee : employees) {
                if (employee instanceof Manager) {
                    String degree = ((Manager) employee).getDegree();
                    if (degree.equals("BSc")) {
                        bachelors++;
                    }
                    if (degree.equals("MSc")) {
                        masters++;
                    }
                    if (degree.equals("PhD")) {
                        phds++;
                    }
                } else if (employee instanceof Director) {
                    String degree = ((Director) employee).getDegree();
                    if (degree.equals("BSc")) {
                        bachelors++;
                    }
                    if (degree.equals("MSc")) {
                        masters++;
                    }
                    if (degree.equals("PhD")) {
                        phds++;
                    }
                }
            }
            HashMap<String, Integer> degrees = new HashMap<>();
            if (bachelors > 0) {
                degrees.put("BSc", bachelors);
            }
            if (masters > 0) {
                degrees.put("MSc", masters);
            }
            if (phds > 0) {
                degrees.put("PhD", phds);
            }
            return degrees;
        }
    }

    public String promoteToManager(String empID, String degree) throws Exception {
        String message;
        Employee empToManager = retrieveEmployee(empID);
        if (!containsEmployee(empID) || empToManager == null) {
            throw new EmployeeNotFoundException(empID);
        } else {
            String name = empToManager.getName();
            double salary = empToManager.getRawSalary();
            employees.remove(empToManager);
            employees.add(new Manager(empID, name, salary, degree));
            message = empID + " promoted successfully to Manager.";
        }
        return message;
    }

    public String promoteToDirector(String empID, String degree, String department) throws Exception {
        String message;
        Employee empToDirector = retrieveEmployee(empID);
        if (!containsEmployee(empID) || empToDirector == null) {
            throw new EmployeeNotFoundException(empID);
        } else {
            String name = empToDirector.getName();
            double salary = empToDirector.getRawSalary();
            employees.remove(empToDirector);
            employees.add(new Director(empID, name, salary, degree, department));
            message = empID + " promoted successfully to Director.";
        }
        return message;
    }

    public String promoteToIntern(String empID, int gpa) throws Exception {
        String message;
        Employee empToIntern = retrieveEmployee(empID);
        if (!containsEmployee(empID) || empToIntern == null) {
            throw new EmployeeNotFoundException(empID);
        } else {
            String name = empToIntern.getName();
            double salary = empToIntern.getRawSalary();
            employees.remove(empToIntern);
            employees.add(new Intern(empID, name, salary, gpa));
            message = empID + " promoted successfully to Intern.";
        }
        return message;
    }
}