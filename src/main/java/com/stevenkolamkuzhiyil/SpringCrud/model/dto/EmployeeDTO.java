package com.stevenkolamkuzhiyil.SpringCrud.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stevenkolamkuzhiyil.SpringCrud.model.Employee;
import com.stevenkolamkuzhiyil.SpringCrud.validation.ValidUserRole;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class EmployeeDTO {

    private Long id;

    @NotEmpty(message = "Enter a first name.")
    private String firstName;

    @NotEmpty(message = "Enter a last name.")
    private String lastName;

    @Email
    @NotEmpty(message = "Enter an email.")
    private String email;

    @NotNull(message = "Enter an employment date.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate empDate;

    @NotNull(message = "Enter annual salary. ")
    @Min(value = 10000, message = "Salary must be at least 10000. ")
    private Integer salary;

    @Min(value = 1, message = "Invalid supervisor value.")
    private Long supervisorId;

    @Min(value = 1, message = "Invalid branch value.")
    private Long branchId;

    @NotEmpty(message = "Select a role.")
    @ValidUserRole
    private String role;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long id,
                       @NotEmpty(message = "Enter a first name.") String firstName,
                       @NotEmpty(message = "Enter a last name.") String lastName,
                       @Email @NotEmpty(message = "Enter an email.") String email,
                       @NotNull(message = "Enter an employment date.") LocalDate empDate,
                       @NotNull(message = "Enter annual salary. ")
                       @Min(value = 10000, message = "Salary must be at least 10000. ") Integer salary,
                       @Min(value = 1, message = "Invalid supervisor value.") Long supervisorId,
                       @Min(value = 1, message = "Invalid branch value.") Long branchId,
                       @NotEmpty(message = "Select a role.") String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.empDate = empDate;
        this.salary = salary;
        this.supervisorId = supervisorId;
        this.branchId = branchId;
        this.role = role;
    }

    public EmployeeDTO(Employee employee) {
        this.id = employee.getEmpId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.email = employee.getEmail();
        this.empDate = employee.getEmpDate();
        this.salary = employee.getSalary();
        this.supervisorId = employee.getSupervisor() == null ? null : employee.getSupervisor().getEmpId();
        this.branchId = employee.getBranch() == null ? null : employee.getBranch().getBranchId();
        this.role = employee.getRoles();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getEmpDate() {
        return empDate;
    }

    public void setEmpDate(LocalDate empDate) {
        this.empDate = empDate;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
