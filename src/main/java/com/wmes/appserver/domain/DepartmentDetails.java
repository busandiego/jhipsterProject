package com.wmes.appserver.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A DepartmentDetails.
 */
@Entity
@Table(name = "department_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DepartmentDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_details_name")
    private String departmentDetailsName;

    @OneToOne(mappedBy = "departmentDetails")
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties("departmentDetails")
    private Department department;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentDetailsName() {
        return departmentDetailsName;
    }

    public DepartmentDetails departmentDetailsName(String departmentDetailsName) {
        this.departmentDetailsName = departmentDetailsName;
        return this;
    }

    public void setDepartmentDetailsName(String departmentDetailsName) {
        this.departmentDetailsName = departmentDetailsName;
    }

    public Employee getEmployee() {
        return employee;
    }

    public DepartmentDetails employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Department getDepartment() {
        return department;
    }

    public DepartmentDetails department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartmentDetails)) {
            return false;
        }
        return id != null && id.equals(((DepartmentDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DepartmentDetails{" +
            "id=" + getId() +
            ", departmentDetailsName='" + getDepartmentDetailsName() + "'" +
            "}";
    }
}
