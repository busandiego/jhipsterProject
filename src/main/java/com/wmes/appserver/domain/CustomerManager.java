package com.wmes.appserver.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CustomerManager.
 */
@Entity
@Table(name = "customer_manager")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomerManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_manager_name")
    private String customerManagerName;

    @Column(name = "customer_manager_department")
    private String customerManagerDepartment;

    @Column(name = "customer_manager_number")
    private String customerManagerNumber;

    @Column(name = "customer_manger_email")
    private String customerMangerEmail;

    @ManyToOne
    @JsonIgnoreProperties("customerManagers")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerManagerName() {
        return customerManagerName;
    }

    public CustomerManager customerManagerName(String customerManagerName) {
        this.customerManagerName = customerManagerName;
        return this;
    }

    public void setCustomerManagerName(String customerManagerName) {
        this.customerManagerName = customerManagerName;
    }

    public String getCustomerManagerDepartment() {
        return customerManagerDepartment;
    }

    public CustomerManager customerManagerDepartment(String customerManagerDepartment) {
        this.customerManagerDepartment = customerManagerDepartment;
        return this;
    }

    public void setCustomerManagerDepartment(String customerManagerDepartment) {
        this.customerManagerDepartment = customerManagerDepartment;
    }

    public String getCustomerManagerNumber() {
        return customerManagerNumber;
    }

    public CustomerManager customerManagerNumber(String customerManagerNumber) {
        this.customerManagerNumber = customerManagerNumber;
        return this;
    }

    public void setCustomerManagerNumber(String customerManagerNumber) {
        this.customerManagerNumber = customerManagerNumber;
    }

    public String getCustomerMangerEmail() {
        return customerMangerEmail;
    }

    public CustomerManager customerMangerEmail(String customerMangerEmail) {
        this.customerMangerEmail = customerMangerEmail;
        return this;
    }

    public void setCustomerMangerEmail(String customerMangerEmail) {
        this.customerMangerEmail = customerMangerEmail;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerManager customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerManager)) {
            return false;
        }
        return id != null && id.equals(((CustomerManager) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CustomerManager{" +
            "id=" + getId() +
            ", customerManagerName='" + getCustomerManagerName() + "'" +
            ", customerManagerDepartment='" + getCustomerManagerDepartment() + "'" +
            ", customerManagerNumber='" + getCustomerManagerNumber() + "'" +
            ", customerMangerEmail='" + getCustomerMangerEmail() + "'" +
            "}";
    }
}
