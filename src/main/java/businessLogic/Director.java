package businessLogic;

import exceptions.EmployeeInformationException;

import static facade.Facade.truncateValue;

public class Director extends Manager {

    private String department;

    public Director(String id, String name, double grossSalary, String degree, String department) throws Exception {
        super(id, name, grossSalary, degree);
        if (department.isEmpty() && !department.equals("Human Resources") && !department.equals("Technical") && !department.equals("Business")) {
            throw new EmployeeInformationException("Department must be one of the options: Business, Human Resources or Technical.");
        } else {
            this.department = department;
        }
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String newDepartment) {
        this.department = newDepartment;
    }

    @Override
    public String toString() {
        return super.getDegree() + " " + super.getName() + "'s gross salary is " + String.format("%.2f", truncateValue(this.calculateGrossSalary(), 2)) + " SEK per month. Dept: " + this.department;
    }

    @Override
    public double getRawSalary() {
        return super.getRawSalary();
    }

    @Override
    public double calculateGrossSalary() {
        return super.calculateGrossSalary() + 5000;
    }

    @Override
    public double calculateNetSalary() {
        double netSalary;
        if (this.calculateGrossSalary() < 30000.0) {
            netSalary = this.calculateGrossSalary() - this.calculateGrossSalary() * 0.1;
        } else if (this.calculateGrossSalary() >= 30000.0 && this.calculateGrossSalary() < 50000.0) {
            netSalary = this.calculateGrossSalary() - this.calculateGrossSalary() * 0.2;
        } else {
            double salary = (this.calculateGrossSalary() - 30000.0) * 0.4 + 30000.0 * 0.2;
            netSalary = this.calculateGrossSalary() - salary;
        }
        return netSalary;
    }

}