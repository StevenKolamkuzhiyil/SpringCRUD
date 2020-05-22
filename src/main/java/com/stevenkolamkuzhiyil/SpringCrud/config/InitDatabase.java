package com.stevenkolamkuzhiyil.SpringCrud.config;

import com.stevenkolamkuzhiyil.SpringCrud.dto.model.Branch;
import com.stevenkolamkuzhiyil.SpringCrud.dto.model.Employee;
import com.stevenkolamkuzhiyil.SpringCrud.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Configuration
public class InitDatabase implements CommandLineRunner {

    private final EmployeeRepo employeeRepo;

    @Autowired
    public InitDatabase(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public void run(String... args) {
        employeeRepo.deleteAll();

        Branch bran0 = new Branch("B_ONE", LocalDate.now(ZoneOffset.UTC), null);
        Branch bran1 = new Branch("B_TWO", LocalDate.now(ZoneOffset.UTC), null);
        Branch bran2 = new Branch("B_THREE", null, null);

        Employee emp0 = new Employee("First", "First", LocalDate.now(ZoneOffset.UTC), 10000, null, bran0);
        emp0.setBranch(bran0);
        Employee emp1 = new Employee("Second", "Second", LocalDate.now(ZoneOffset.UTC), 20000, null, bran1);
        Employee emp2 = new Employee("Third", "Third", LocalDate.now(ZoneOffset.UTC), 30000, emp0, bran0);
        Employee emp3 = new Employee("Fourth", "Fourth", LocalDate.now(ZoneOffset.UTC), 40000, null, bran2);

        bran0.setManager(emp0);
        bran1.setManager(emp1);

        List<Employee> emps = List.of(emp0, emp1, emp2, emp3);
        employeeRepo.saveAll(emps);
    }
}
