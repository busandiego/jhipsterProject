package com.wmes.appserver.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Department.
 */
@Entity
@Table(name = "department")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name")
    private String departmentName;

    @OneToMany(mappedBy = "department")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DepartmentDetails> departmentDetails = new HashSet<>();

    @OneToOne(mappedBy = "department")
    @JsonIgnore
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Department departmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Set<DepartmentDetails> getDepartmentDetails() {
        return departmentDetails;
    }

    public Department departmentDetails(Set<DepartmentDetails> departmentDetails) {
        this.departmentDetails = departmentDetails;
        return this;
    }

    public Department addDepartmentDetails(DepartmentDetails departmentDetails) {
        this.departmentDetails.add(departmentDetails);
        departmentDetails.setDepartment(this);
        return this;
    }

    public Department removeDepartmentDetails(DepartmentDetails departmentDetails) {
        this.departmentDetails.remove(departmentDetails);
        departmentDetails.setDepartment(null);
        return this;
    }

    public void setDepartmentDetails(Set<DepartmentDetails> departmentDetails) {
        this.departmentDetails = departmentDetails;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Department employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Department)) {
            return false;
        }
        return id != null && id.equals(((Department) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Department{" +
            "id=" + getId() +
            ", departmentName='" + getDepartmentName() + "'" +
            "}";
    }
}
