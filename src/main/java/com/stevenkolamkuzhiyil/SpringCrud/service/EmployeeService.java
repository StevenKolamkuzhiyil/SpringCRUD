package com.stevenkolamkuzhiyil.SpringCrud.service;

import com.stevenkolamkuzhiyil.SpringCrud.exception.throwable.EmployeeNotFound;
import com.stevenkolamkuzhiyil.SpringCrud.model.Branch;
import com.stevenkolamkuzhiyil.SpringCrud.model.Employee;
import com.stevenkolamkuzhiyil.SpringCrud.model.UserRoles;
import com.stevenkolamkuzhiyil.SpringCrud.model.dto.EmployeeDTO;
import com.stevenkolamkuzhiyil.SpringCrud.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EmployeeService {

    @Autowired
    private BranchService branchService;
    private final EmployeeRepo employeeRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo, PasswordEncoder passwordEncoder) {
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Employee> all() {
        return employeeRepo.findAll();
    }

    public Employee findById(@PositiveOrZero long id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new EmployeeNotFound(id));
    }

    public List<Employee> findByBranch(Branch branch) {
        return employeeRepo.findByBranch(branch);
    }

    public List<Employee> findBySupervisor(Employee supervisor) {
        return employeeRepo.findBySupervisor(supervisor);
    }

    public List<Branch> findManagingBranches(Employee manager) {
        return branchService.findByManager(manager);
    }

    public Employee addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        setEmployeeFromDTO(employee, employeeDTO);
        /*
         * How this should actually be implemented: Generate a random n character password which will be
         * sent to the employee by email. The default password should be changed by the employee afterwards.
         *
         * The current implementation is only a simple substitute.
         */
        String password = passwordEncoder.encode("password");
        employee.setPassword(password);

        return employeeRepo.save(employee);
    }

    public Employee updateEmployee(@PositiveOrZero long id, @Valid EmployeeDTO employeeDTO) {
        Employee employee = findById(id);
        setEmployeeFromDTO(employee, employeeDTO);
        return employeeRepo.save(employee);
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public List<Employee> removeEmployee(@PositiveOrZero long id) {
        Employee employee = findById(id);
        List<Employee> subordinates = findBySupervisor(employee);
        subordinates.forEach(s -> {
            s.setSupervisor(null);
            employeeRepo.save(s);
        });

        List<Branch> managing = branchService.findByManager(employee);
        managing.forEach(b -> {
            b.setManager(null);
            b.setMgrStartDate(null);
            branchService.updateBranch(b);
        });

        employee.setBranch(null);
        employee.setSupervisor(null);
        employeeRepo.delete(employee);

        return subordinates;
    }

    private void setEmployeeFromDTO(Employee employee, EmployeeDTO employeeDTO) {
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setEmpDate(employeeDTO.getEmpDate());
        employee.setSalary(employeeDTO.getSalary());
        Employee supervisor = employeeDTO.getSupervisorId() != null ? findById(employeeDTO.getSupervisorId()) : null;
        employee.setSupervisor(supervisor);
        Branch branch = employeeDTO.getBranchId() != null ? branchService.findById(employeeDTO.getBranchId()) : null;
        employee.setBranch(branch);
        employee.setRoles(employeeDTO.getRole());
        employee.setPermissions(UserRoles.roleFromName(employeeDTO.getRole()).getGrantedAuthorities());
    }

    public List<String> getRoles() {
        return Stream.of(UserRoles.values())
                .map(UserRoles::name)
                .collect(Collectors.toList());
    }
}
