package com.stevenkolamkuzhiyil.SpringCrud.dto.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "emp_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long empId;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(name = "emp_date", nullable = false)
    private LocalDate empDate;

    @Column(name = "salary", nullable = false)
    private int salary;

    @OneToOne
    @JoinColumn(name = "super_id", referencedColumnName = "emp_id")
    private Employee supervisor;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Branch.class)
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")
    private Branch branch;

    public Employee() {
    }

    public Employee(String firstName, String lastName, LocalDate empDate, int salary, Employee supervisor, Branch branch) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.empDate = empDate;
        this.salary = salary;
        this.supervisor = supervisor;
        this.branch = branch;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName(String name) {
        return this.firstName + " " + this.lastName;
    }

    public LocalDate getEmpDate() {
        return empDate;
    }

    public void setEmpDate(LocalDate empDate) {
        this.empDate = empDate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee that = (Employee) o;
        return empId == that.empId &&
                salary == that.salary &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(empDate, that.empDate) &&
                Objects.equals(supervisor, that.supervisor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, firstName, lastName, empDate, salary, supervisor);
    }
}
