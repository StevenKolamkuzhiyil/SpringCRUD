package com.stevenkolamkuzhiyil.SpringCrud.repository;

import com.stevenkolamkuzhiyil.SpringCrud.model.Branch;
import com.stevenkolamkuzhiyil.SpringCrud.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends UserBaseRepository<Employee> {
    List<Employee> findBySupervisor(Employee supervisor);

    List<Employee> findByBranch(Branch branch);
}
