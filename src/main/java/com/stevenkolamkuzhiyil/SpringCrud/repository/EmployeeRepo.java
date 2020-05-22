package com.stevenkolamkuzhiyil.SpringCrud.repository;

import com.stevenkolamkuzhiyil.SpringCrud.dto.model.Branch;
import com.stevenkolamkuzhiyil.SpringCrud.dto.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    List<Employee> findBySupervisor(Employee supervisor);

    List<Employee> findByBranch(Branch branch);
}
