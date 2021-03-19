package com.wmes.appserver.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CustomerKind.
 */
@Entity
@Table(name = "customer_kind")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomerKind implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_kind_name")
    private String customerKindName;

    @ManyToOne
    @JsonIgnoreProperties("customerKinds")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerKindName() {
        return customerKindName;
    }

    public CustomerKind customerKindName(String customerKindName) {
        this.customerKindName = customerKindName;
        return this;
    }

    public void setCustomerKindName(String customerKindName) {
        this.customerKindName = customerKindName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerKind customer(Customer customer) {
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
        if (!(o instanceof CustomerKind)) {
            return false;
        }
        return id != null && id.equals(((CustomerKind) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CustomerKind{" +
            "id=" + getId() +
            ", customerKindName='" + getCustomerKindName() + "'" +
            "}";
    }
}
