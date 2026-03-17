package com.examly.springapp.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class JobOpening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    private String positionTitle;

    private String description;

    private Double salary;

    @ManyToOne
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "job_eligible_departments",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private List<Department> eligibleDepartments;

    public JobOpening(){}

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Department> getEligibleDepartments() {
        return eligibleDepartments;
    }

    public void setEligibleDepartments(List<Department> eligibleDepartments) {
        this.eligibleDepartments = eligibleDepartments;
    }
}