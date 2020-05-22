package com.stevenkolamkuzhiyil.SpringCrud.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stevenkolamkuzhiyil.SpringCrud.dto.model.Branch;
import com.stevenkolamkuzhiyil.SpringCrud.validation.Dependent;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Dependent(independent = "managerId", fields = "mgrStartDate")
public class BranchDTO {

    private Long id;
    @NotEmpty(message = "Enter branch name")
    private String branchName;
    @Min(value = 1, message = "Must be greater or equal to 1")
    private Long managerId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate mgrStartDate;

    public BranchDTO() {
    }

    public BranchDTO(Long id, @NotEmpty(message = "Enter branch name") String branchName,
                     @Min(value = 1, message = "Must be greater or equal to 1") Long managerId, LocalDate mgrStartDate) {
        this.id = id;
        this.branchName = branchName;
        this.managerId = managerId;
        this.mgrStartDate = mgrStartDate;
    }

    public BranchDTO(Branch branch) {
        this.id = branch.getBranchId();
        this.branchName = branch.getBranchName();
        if (branch.getManager() != null) {
            this.managerId = branch.getManager().getEmpId();
            this.mgrStartDate = branch.getMgrStartDate();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public LocalDate getMgrStartDate() {
        return mgrStartDate;
    }

    public void setMgrStartDate(LocalDate mgrStartDate) {
        this.mgrStartDate = mgrStartDate;
    }

}
