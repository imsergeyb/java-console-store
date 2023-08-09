package businessLogic;

import exceptions.EmployeeInformationException;

import static facade.Facade.truncateValue;

public class Intern extends Employee {
    private int GPA;

    public Intern(String id, String name, double grossSalary, int GPA) throws Exception {
        super(id, name, grossSalary);
        if (GPA < 0 || GPA > 10) {
            throw new EmployeeInformationException(GPA + "outside range. Must be between 0-10.");
        } else {
            this.GPA = GPA;
        }
    }

    public int getGPA() {
        return GPA;
    }

    public void setGPA(int newGPA) {
        this.GPA = newGPA;
    }

    @Override
    public String toString() {
        return super.getName() + "'s gross salary is " + String.format("%.2f", truncateValue(this.calculateGrossSalary(), 2)) + " SEK per month. GPA: " + this.GPA;
    }

    @Override
    public double getRawSalary() {
        return super.getRawSalary();
    }

    @Override
    public double calculateGrossSalary() {
        double grossSalary = 0.0;
        if (GPA <= 5) {
            grossSalary = 0.0;
        } else if (this.GPA > 5 && this.GPA < 8) {
            grossSalary = super.calculateGrossSalary();
        } else if (this.GPA >= 8) {
            grossSalary = super.calculateGrossSalary() + 1000.0;
        }
        return grossSalary;
    }

    @Override
    public double calculateNetSalary() {
        return calculateGrossSalary();
    }
}
