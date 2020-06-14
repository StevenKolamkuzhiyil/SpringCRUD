package com.stevenkolamkuzhiyil.SpringCrud.config;

import com.stevenkolamkuzhiyil.SpringCrud.model.Branch;
import com.stevenkolamkuzhiyil.SpringCrud.model.Employee;
import com.stevenkolamkuzhiyil.SpringCrud.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static com.stevenkolamkuzhiyil.SpringCrud.model.UserRoles.*;

@Configuration
public class InitDatabase implements CommandLineRunner {

    private final EmployeeRepo employeeRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitDatabase(EmployeeRepo employeeRepo, PasswordEncoder passwordEncoder) {
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        employeeRepo.deleteAll();

        Branch bran0 = new Branch("B_ONE", LocalDate.now(ZoneOffset.UTC), null);
        Branch bran1 = new Branch("B_TWO", LocalDate.now(ZoneOffset.UTC), null);
        Branch bran2 = new Branch("B_THREE", null, null);

        String password = "password";
        String encodedPassword1 = passwordEncoder.encode(password);
        String encodedPassword2 = passwordEncoder.encode(password);
        String encodedPassword3 = passwordEncoder.encode(password);
        String encodedPassword4 = passwordEncoder.encode(password);

        Employee emp0 = new Employee("First", "First", LocalDate.now(ZoneOffset.UTC), 10000, null, bran0, "first@email.com", encodedPassword1, ADMIN.name(), ADMIN.getGrantedAuthorities());
        Employee emp1 = new Employee("Second", "Second", LocalDate.now(ZoneOffset.UTC), 20000, null, bran1, "second@email.com", encodedPassword2, MANAGER.name(), MANAGER.getGrantedAuthorities());
        Employee emp2 = new Employee("Third", "Third", LocalDate.now(ZoneOffset.UTC), 30000, emp0, bran0, "third@email.com", encodedPassword3, USER.name(), USER.getGrantedAuthorities());
        Employee emp3 = new Employee("Fourth", "Fourth", LocalDate.now(ZoneOffset.UTC), 40000, null, bran2, "fourth@email.com", encodedPassword4, USER.name(), USER.getGrantedAuthorities());

        bran0.setManager(emp0);
        bran1.setManager(emp1);

        List<Employee> emps = List.of(emp0, emp1, emp2, emp3);
        employeeRepo.saveAll(emps);

    }
}
