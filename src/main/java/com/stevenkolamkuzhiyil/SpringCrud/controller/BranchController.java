package com.stevenkolamkuzhiyil.SpringCrud.controller;

import com.stevenkolamkuzhiyil.SpringCrud.exception.throwable.ValidationException;
import com.stevenkolamkuzhiyil.SpringCrud.model.Branch;
import com.stevenkolamkuzhiyil.SpringCrud.model.dto.BranchDTO;
import com.stevenkolamkuzhiyil.SpringCrud.model.dto.EmployeeDTO;
import com.stevenkolamkuzhiyil.SpringCrud.service.BranchService;
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
@RequestMapping("api/branches")
public class BranchController {

    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("")
    public ResponseEntity<?> all() {
        List<BranchDTO> branches = branchService.all()
                .stream()
                .map(BranchDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(branches);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> one(@PathVariable @PositiveOrZero long id) {
        return ResponseEntity.ok(new BranchDTO(branchService.findById(id)));
    }

    @PostMapping("create")
    public ResponseEntity<?> add(@ModelAttribute("branch") @Valid BranchDTO branchDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult);

        return ResponseEntity.ok(new BranchDTO(branchService.addBranch(branchDTO)));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable @PositiveOrZero long id,
                                    @ModelAttribute("branch") @Valid BranchDTO branchDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult);

        return ResponseEntity.ok(new BranchDTO(branchService.updateBranch(id, branchDTO)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable @PositiveOrZero long id) {
        branchService.removeBranch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/delete")
    public ResponseEntity<?> findEntriesToChangeOnDelete(@PathVariable @PositiveOrZero long id) {
        Branch branch = branchService.findById(id);
        List<EmployeeDTO> employees = branchService.findEmployees(branch)
                .stream().map(EmployeeDTO::new).collect(Collectors.toList());

        Map<String, List<EmployeeDTO>> entriesToChangeByEntity = new HashMap<>();
        entriesToChangeByEntity.put("Employees", employees);

        return ResponseEntity.ok(entriesToChangeByEntity);
    }
}
