package exceptions;

public class EmployeeEmptyListException extends Exception {

    public EmployeeEmptyListException() throws Exception {
        super("No employees registered yet.");
    }

}
