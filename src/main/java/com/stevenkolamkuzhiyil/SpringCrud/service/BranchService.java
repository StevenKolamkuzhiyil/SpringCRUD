package com.stevenkolamkuzhiyil.SpringCrud.service;

import com.stevenkolamkuzhiyil.SpringCrud.dto.BranchDTO;
import com.stevenkolamkuzhiyil.SpringCrud.dto.model.Branch;
import com.stevenkolamkuzhiyil.SpringCrud.dto.model.Employee;
import com.stevenkolamkuzhiyil.SpringCrud.exception.throwable.BranchNotFound;
import com.stevenkolamkuzhiyil.SpringCrud.repository.BranchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Service
public class BranchService {

    @Autowired
    private EmployeeService employeeService;
    private final BranchRepo branchRepo;

    @Autowired
    public BranchService(BranchRepo branchRepo) {
        this.branchRepo = branchRepo;
    }

    public List<Branch> all() {
        return branchRepo.findAll();
    }

    public Branch findById(@PositiveOrZero long id) {
        return branchRepo.findById(id)
                .orElseThrow(() -> new BranchNotFound(id));
    }

    public List<Branch> findByManager(Employee manager) {
        return branchRepo.findByManager(manager);
    }

    public List<Employee> findEmployees(Branch branch) {
        return employeeService.findByBranch(branch);
    }

    public Branch addBranch(@Valid BranchDTO branchDTO) {
        Branch branch = new Branch();
        setBranchFromDTO(branch, branchDTO);
        return branchRepo.save(branch);
    }

    public Branch updateBranch(@PositiveOrZero long id, @Valid BranchDTO branchDTO) {
        Branch branch = findById(id);
        setBranchFromDTO(branch, branchDTO);
        return branchRepo.save(branch);
    }

    public Branch updateBranch(Branch branch) {
        return branchRepo.save(branch);
    }

    public void removeBranch(@PositiveOrZero long id) {
        Branch branch = findById(id);
        List<Employee> employees = employeeService.findByBranch(branch);
        employees.forEach(e -> {
            e.setBranch(null);
            employeeService.updateEmployee(e);
        });

        branch.setManager(null);
        branchRepo.delete(branch);
    }

    private void setBranchFromDTO(Branch branch, BranchDTO branchDTO) {
        branch.setBranchName(branchDTO.getBranchName());
        if (branchDTO.getManagerId() != null) {
            Employee manager = employeeService.findById(branchDTO.getManagerId());
            branch.setManager(manager);
            branch.setMgrStartDate(branchDTO.getMgrStartDate());
        } else {
            branch.setManager(null);
            branch.setMgrStartDate(null);
        }
    }

}
