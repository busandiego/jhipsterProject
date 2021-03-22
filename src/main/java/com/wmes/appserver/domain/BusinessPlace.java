package com.wmes.appserver.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A BusinessPlace.
 */
@Entity
@Table(name = "business_place")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
public class BusinessPlace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "bp_name", nullable = false)
    private String bpName;

    @Column(name = "is_headquarter")
    private Boolean isHeadquarter;

    @Column(name = "bp_zip_address")
    private String bpZipAddress;

    @Column(name = "bp_address")
    private String bpAddress;

    @Column(name = "bp_detail_address")
    private String bpDetailAddress;

    @Column(name = "bp_fax_number")
    private String bpFaxNumber;

    @Column(name = "bp_number")
    private String bpNumber;

    @Column(name = "business_id")
    private Long businessId;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    // @ManyToOne
    //    @JsonIgnoreProperties("businessPlaces")
    //    private Business business;

    @ManyToOne
    @JsonIgnoreProperties("businessPlaces")
    private Customer customer;


    public BusinessPlace() {

    }

    public Boolean getHeadquarter() {
        return isHeadquarter;
    }

    public void setHeadquarter(Boolean headquarter) {
        isHeadquarter = headquarter;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBpName() {
        return bpName;
    }

    public BusinessPlace bpName(String bpName) {
        this.bpName = bpName;
        return this;
    }

    public void setBpName(String bpName) {
        this.bpName = bpName;
    }

    public Boolean isIsHeadquarter() {
        return isHeadquarter;
    }

    public BusinessPlace isHeadquarter(Boolean isHeadquarter) {
        this.isHeadquarter = isHeadquarter;
        return this;
    }

    public void setIsHeadquarter(Boolean isHeadquarter) {
        this.isHeadquarter = isHeadquarter;
    }

    public String getBpZipAddress() {
        return bpZipAddress;
    }

    public BusinessPlace bpZipAddress(String bpZipAddress) {
        this.bpZipAddress = bpZipAddress;
        return this;
    }

    public void setBpZipAddress(String bpZipAddress) {
        this.bpZipAddress = bpZipAddress;
    }

    public String getBpAddress() {
        return bpAddress;
    }

    public BusinessPlace bpAddress(String bpAddress) {
        this.bpAddress = bpAddress;
        return this;
    }

    public void setBpAddress(String bpAddress) {
        this.bpAddress = bpAddress;
    }

    public String getBpDetailAddress() {
        return bpDetailAddress;
    }

    public BusinessPlace bpDetailAddress(String bpDetailAddress) {
        this.bpDetailAddress = bpDetailAddress;
        return this;
    }

    public void setBpDetailAddress(String bpDetailAddress) {
        this.bpDetailAddress = bpDetailAddress;
    }

    public String getBpFaxNumber() {
        return bpFaxNumber;
    }

    public BusinessPlace bpFaxNumber(String bpFaxNumber) {
        this.bpFaxNumber = bpFaxNumber;
        return this;
    }

    public void setBpFaxNumber(String bpFaxNumber) {
        this.bpFaxNumber = bpFaxNumber;
    }

    public String getBpNumber() {
        return bpNumber;
    }

    public BusinessPlace bpNumber(String bpNumber) {
        this.bpNumber = bpNumber;
        return this;
    }

    public void setBpNumber(String bpNumber) {
        this.bpNumber = bpNumber;
    }



    public Customer getCustomer() {
        return customer;
    }

    public BusinessPlace customer(Customer customer) {
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
        if (!(o instanceof BusinessPlace)) {
            return false;
        }
        return id != null && id.equals(((BusinessPlace) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BusinessPlace{" +
            "id=" + getId() +
            ", bpName='" + getBpName() + "'" +
            ", isHeadquarter='" + isIsHeadquarter() + "'" +
            ", bpZipAddress='" + getBpZipAddress() + "'" +
            ", bpAddress='" + getBpAddress() + "'" +
            ", bpDetailAddress='" + getBpDetailAddress() + "'" +
            ", bpFaxNumber='" + getBpFaxNumber() + "'" +
            ", bpNumber='" + getBpNumber() + "'" +
            "}";
    }
}
