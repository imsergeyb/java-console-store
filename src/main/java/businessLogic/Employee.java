package businessLogic;

import exceptions.EmployeeInformationException;

import java.util.Objects;

import static facade.Facade.truncateValue;

public class Employee implements Comparable<Employee> {

    final private String id;
    private String name;
    private double grossSalary;

    public Employee(String id, String name, double grossSalary) throws Exception {
        if (id.isEmpty()) {
            throw new EmployeeInformationException("ID cannot be blank.");
        } else if (name.isEmpty()) {
            throw new EmployeeInformationException("Name cannot be blank.");
        } else if (grossSalary <= 0) {
            throw new EmployeeInformationException("Salary must be greater than 0.");
        } else {
            this.id = id;
            this.name = name;
            this.grossSalary = grossSalary;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public double getRawSalary() {
        return this.grossSalary;
    }

    public double calculateGrossSalary() {
        return this.grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public double calculateNetSalary() {
        double netSalary = this.grossSalary - this.grossSalary * 0.1;
        return netSalary;
    }

    @Override
    public String toString() {
        return this.getName() + "'s gross salary is " + String.format("%.2f", truncateValue(this.calculateGrossSalary(), 2)) + " SEK per month.";
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) {
            return true;
        }
        if (another == null) {
            return false;
        }
        if (another instanceof Employee) {
            Employee employee = (Employee) another;
            return this.id.equals(employee.id);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Employee e) {
        if (this.calculateGrossSalary() > e.calculateGrossSalary()) {
            return 1;
        } else if (this.calculateGrossSalary() == e.calculateGrossSalary()) {
            return 0;
        }
        return -1;
    }
}