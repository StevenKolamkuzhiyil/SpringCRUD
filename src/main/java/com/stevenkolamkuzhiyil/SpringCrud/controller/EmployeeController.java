package com.stevenkolamkuzhiyil.SpringCrud.controller;

import com.stevenkolamkuzhiyil.SpringCrud.dto.BranchDTO;
import com.stevenkolamkuzhiyil.SpringCrud.dto.EmployeeDTO;
import com.stevenkolamkuzhiyil.SpringCrud.dto.model.Employee;
import com.stevenkolamkuzhiyil.SpringCrud.exception.throwable.ValidationException;
import com.stevenkolamkuzhiyil.SpringCrud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public ResponseEntity<?> all() {
        List<EmployeeDTO> employees = employeeService.all()
                .stream()
                .map(EmployeeDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(employees);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> one(@PathVariable @PositiveOrZero long id) {
        return ResponseEntity.ok(new EmployeeDTO(employeeService.findById(id)));
    }

    @PostMapping("create")
    public ResponseEntity<?> add(@ModelAttribute("employee") @Valid EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult);

        return ResponseEntity.ok(new EmployeeDTO(employeeService.addEmployee(employeeDTO)));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable @PositiveOrZero long id,
                                    @ModelAttribute("employee") @Valid EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult);

        return ResponseEntity.ok(new EmployeeDTO(employeeService.updateEmployee(id, employeeDTO)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable @PositiveOrZero long id) {
        List<EmployeeDTO> changed = employeeService.removeEmployee(id)
                .stream().map(EmployeeDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(changed);
    }

    @GetMapping("{id}/delete")
    public ResponseEntity<?> findEntriesToChangeOnDelete(@PathVariable @PositiveOrZero long id) {
        Employee employee = employeeService.findById(id);
        List<EmployeeDTO> subordinates = employeeService.findBySupervisor(employee)
                .stream().map(EmployeeDTO::new).collect(Collectors.toList());
        List<BranchDTO> manages = employeeService.findManagingBranches(employee)
                .stream().map(BranchDTO::new).collect(Collectors.toList());

        Map<String, List<?>> entriesToChangeByEntity = new HashMap<>();
        entriesToChangeByEntity.put("Employees", subordinates);
        entriesToChangeByEntity.put("Branches", manages);

        return ResponseEntity.ok(entriesToChangeByEntity);
    }
}
