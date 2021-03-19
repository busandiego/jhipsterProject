package com.wmes.appserver.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @NotNull
    @Column(name = "customer_representative", nullable = false)
    private String customerRepresentative;

    @Column(name = "customer_registration_num")
    private String customerRegistrationNum;

    @Column(name = "customer_representative_num")
    private String customerRepresentativeNum;

    @Column(name = "customer_type")
    private String customerType;

    @Column(name = "customer_category")
    private String customerCategory;

    @Column(name = "customer_number")
    private String customerNumber;

    @Column(name = "customer_email")
    private String customerEmail;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BusinessPlace> businessPlaces = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CustomerManager> customerManagers = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CustomerKind> customerKinds = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Item> items = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Material> materials = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Customer customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerRepresentative() {
        return customerRepresentative;
    }

    public Customer customerRepresentative(String customerRepresentative) {
        this.customerRepresentative = customerRepresentative;
        return this;
    }

    public void setCustomerRepresentative(String customerRepresentative) {
        this.customerRepresentative = customerRepresentative;
    }

    public String getCustomerRegistrationNum() {
        return customerRegistrationNum;
    }

    public Customer customerRegistrationNum(String customerRegistrationNum) {
        this.customerRegistrationNum = customerRegistrationNum;
        return this;
    }

    public void setCustomerRegistrationNum(String customerRegistrationNum) {
        this.customerRegistrationNum = customerRegistrationNum;
    }

    public String getCustomerRepresentativeNum() {
        return customerRepresentativeNum;
    }

    public Customer customerRepresentativeNum(String customerRepresentativeNum) {
        this.customerRepresentativeNum = customerRepresentativeNum;
        return this;
    }

    public void setCustomerRepresentativeNum(String customerRepresentativeNum) {
        this.customerRepresentativeNum = customerRepresentativeNum;
    }

    public String getCustomerType() {
        return customerType;
    }

    public Customer customerType(String customerType) {
        this.customerType = customerType;
        return this;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerCategory() {
        return customerCategory;
    }

    public Customer customerCategory(String customerCategory) {
        this.customerCategory = customerCategory;
        return this;
    }

    public void setCustomerCategory(String customerCategory) {
        this.customerCategory = customerCategory;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public Customer customerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
        return this;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Customer customerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Set<BusinessPlace> getBusinessPlaces() {
        return businessPlaces;
    }

    public Customer businessPlaces(Set<BusinessPlace> businessPlaces) {
        this.businessPlaces = businessPlaces;
        return this;
    }

    public Customer addBusinessPlace(BusinessPlace businessPlace) {
        this.businessPlaces.add(businessPlace);
        businessPlace.setCustomer(this);
        return this;
    }

    public Customer removeBusinessPlace(BusinessPlace businessPlace) {
        this.businessPlaces.remove(businessPlace);
        businessPlace.setCustomer(null);
        return this;
    }

    public void setBusinessPlaces(Set<BusinessPlace> businessPlaces) {
        this.businessPlaces = businessPlaces;
    }

    public Set<CustomerManager> getCustomerManagers() {
        return customerManagers;
    }

    public Customer customerManagers(Set<CustomerManager> customerManagers) {
        this.customerManagers = customerManagers;
        return this;
    }

    public Customer addCustomerManager(CustomerManager customerManager) {
        this.customerManagers.add(customerManager);
        customerManager.setCustomer(this);
        return this;
    }

    public Customer removeCustomerManager(CustomerManager customerManager) {
        this.customerManagers.remove(customerManager);
        customerManager.setCustomer(null);
        return this;
    }

    public void setCustomerManagers(Set<CustomerManager> customerManagers) {
        this.customerManagers = customerManagers;
    }

    public Set<CustomerKind> getCustomerKinds() {
        return customerKinds;
    }

    public Customer customerKinds(Set<CustomerKind> customerKinds) {
        this.customerKinds = customerKinds;
        return this;
    }

    public Customer addCustomerKind(CustomerKind customerKind) {
        this.customerKinds.add(customerKind);
        customerKind.setCustomer(this);
        return this;
    }

    public Customer removeCustomerKind(CustomerKind customerKind) {
        this.customerKinds.remove(customerKind);
        customerKind.setCustomer(null);
        return this;
    }

    public void setCustomerKinds(Set<CustomerKind> customerKinds) {
        this.customerKinds = customerKinds;
    }

    public Set<Item> getItems() {
        return items;
    }

    public Customer items(Set<Item> items) {
        this.items = items;
        return this;
    }

    public Customer addItem(Item item) {
        this.items.add(item);
        item.setCustomer(this);
        return this;
    }

    public Customer removeItem(Item item) {
        this.items.remove(item);
        item.setCustomer(null);
        return this;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<Material> getMaterials() {
        return materials;
    }

    public Customer materials(Set<Material> materials) {
        this.materials = materials;
        return this;
    }

    public Customer addMaterial(Material material) {
        this.materials.add(material);
        material.setCustomer(this);
        return this;
    }

    public Customer removeMaterial(Material material) {
        this.materials.remove(material);
        material.setCustomer(null);
        return this;
    }

    public void setMaterials(Set<Material> materials) {
        this.materials = materials;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", customerName='" + getCustomerName() + "'" +
            ", customerRepresentative='" + getCustomerRepresentative() + "'" +
            ", customerRegistrationNum='" + getCustomerRegistrationNum() + "'" +
            ", customerRepresentativeNum='" + getCustomerRepresentativeNum() + "'" +
            ", customerType='" + getCustomerType() + "'" +
            ", customerCategory='" + getCustomerCategory() + "'" +
            ", customerNumber='" + getCustomerNumber() + "'" +
            ", customerEmail='" + getCustomerEmail() + "'" +
            "}";
    }
}
