package menu;

import exceptions.EmployeeEmptyListException;
import exceptions.EmployeeInformationException;
import exceptions.EmployeeNotFoundException;
import facade.Facade;
import input.UserInput;

public class EmployeeOptions {

    static final String EOL = System.lineSeparator();
    static final Facade facade = Launcher.facade;

    public static void employeeOptions() throws Exception {

        final String EMPLOYEES_MENU = "Employee options menu:" + EOL +
                "0. Return to Main Menu." + EOL +
                "1. Create an employee (Regular Employee)." + EOL +
                "2. Create an employee (Manager)." + EOL +
                "3. Create an employee (Director)." + EOL +
                "4. Create an employee (Intern)." + EOL +
                "5. Remove an employee." + EOL +
                "6. Print specific employee." + EOL +
                "7. Print all registered employees." + EOL +
                "8. Print the total expense with net salary." + EOL +
                "9. Print all employees sorted by gross salary." + EOL;


        int userAnswer;

        do {
            UserInput.printInfo(EMPLOYEES_MENU);
            userAnswer = UserInput.readInt("Input option Number: ");

            switch (userAnswer) {
                case 0:
                    Launcher.mainMenu();
                    break;
                case 1: //Create an employee (Regular Employee)
                    String regEmpId = UserInput.readString("Enter new employee's ID: ");
                    String regEmpName = UserInput.readString("Enter new employee's name: ");
                    double grossSalary = UserInput.readDouble("Enter new employee's salary before taxes: ");
                    try {
                        facade.createEmployee(regEmpId, regEmpName, grossSalary);
                    } catch (EmployeeInformationException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        employeeOptions();
                    }
                    break;
                case 2: // Create an employee (Manager)

                    String managerId = UserInput.readString("Enter new manager's ID: ");
                    String managerName = UserInput.readString("Enter new manager's name: ");
                    double managerSalary = UserInput.readDouble("Enter new manager's salary before taxes: ");
                    String managerDegree = UserInput.readString("Enter new manager's degree: ");
                    try {
                        facade.createEmployee(managerId, managerName, managerSalary, managerDegree);
                    } catch (EmployeeInformationException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        employeeOptions();
                    }
                    break;
                case 3: // Create an employee (Director)
                    String directorId = UserInput.readString("Enter new director's ID: ");
                    String directorName = UserInput.readString("Enter new director's name: ");
                    double directorSalary = UserInput.readDouble("Enter new director's salary before taxes: ");
                    String directorDegree = UserInput.readString("Enter new director's degree: ");
                    String directorDept = UserInput.readString("Enter new director's department: ");
                    try {
                        facade.createEmployee(directorId, directorName, directorSalary, directorDegree, directorDept);
                    } catch (EmployeeInformationException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        employeeOptions();
                    }
                    break;
                case 4: // Create an employee (Intern)
                    String internId = UserInput.readString("Enter new intern's ID: ");
                    String internName = UserInput.readString("Enter new intern's name: ");
                    double internSalary = UserInput.readDouble("Enter new intern's salary before taxes: ");
                    int internGPA = UserInput.readInt("Enter new intern's GPA: ");
                    try {
                        facade.createEmployee(internId, internName, internSalary, internGPA);
                    } catch (EmployeeInformationException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        employeeOptions();
                    }
                    break;
                case 5: // Remove an employee
                    String empIdtoRemove = UserInput.readString("Enter an ID of employee to be removed: ");
                    try {
                        facade.removeEmployee(empIdtoRemove);
                    } catch (EmployeeNotFoundException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        employeeOptions();
                    }
                    break;
                case 6: // Print specific employee
                    String empIdToPrint = UserInput.readString("Enter an ID of employee to be printed: ");
                    try {
                        facade.printEmployee(empIdToPrint);
                    } catch (EmployeeNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7: // Print all registered employees
                    try {
                        facade.printAllEmployees();
                    } catch (EmployeeEmptyListException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        employeeOptions();
                    }
                    break;
                case 8: // Print the total expense with net salary
                    try {
                        facade.getTotalNetSalary();
                    } catch (EmployeeEmptyListException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        employeeOptions();
                    }
                    break;
                case 9: // Print all employees sorted by gross salary
                    try {
                        facade.printSortedEmployees();
                    } catch (EmployeeEmptyListException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        employeeOptions();
                    }
                    break;
                default:
                    UserInput.printInfo("Invalid menu option. Please type another option: ");
                    employeeOptions();
            }
        } while (userAnswer != 0);
    }
}
