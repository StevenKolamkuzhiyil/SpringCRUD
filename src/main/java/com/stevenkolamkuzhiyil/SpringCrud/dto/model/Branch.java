package com.stevenkolamkuzhiyil.SpringCrud.dto.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "branches")
public class Branch {
    @Id
    @Column(name = "branch_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long branchId;

    @Column(name = "branch_name", nullable = false, length = 40)
    private String branchName;

    @Column(name = "mgr_start_date")
    private LocalDate mgrStartDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mgr_id", referencedColumnName = "emp_id")
    private Employee manager;

    public Branch() {
    }

    public Branch(String branchName, LocalDate mgrStartDate, Employee manager) {
        this.branchName = branchName;
        this.mgrStartDate = mgrStartDate;
        this.manager = manager;
    }

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public LocalDate getMgrStartDate() {
        return mgrStartDate;
    }

    public void setMgrStartDate(LocalDate mgrStartDate) {
        this.mgrStartDate = mgrStartDate;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee employee) {
        this.manager = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch that = (Branch) o;
        return branchId == that.branchId &&
                Objects.equals(branchName, that.branchName) &&
                Objects.equals(mgrStartDate, that.mgrStartDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchId, branchName, mgrStartDate);
    }
}
