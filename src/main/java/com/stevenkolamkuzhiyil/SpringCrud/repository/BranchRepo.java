package com.stevenkolamkuzhiyil.SpringCrud.repository;

import com.stevenkolamkuzhiyil.SpringCrud.model.Branch;
import com.stevenkolamkuzhiyil.SpringCrud.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepo extends JpaRepository<Branch, Long> {
    Optional<Branch> findByBranchName(String branchName);

    List<Branch> findByManager(Employee manager);
}
