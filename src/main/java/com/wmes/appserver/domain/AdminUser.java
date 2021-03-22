package com.wmes.appserver.domain;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "admin_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@EnableJpaAuditing
public class AdminUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "admin_login_id", nullable = false)
    private String adminLoginId;

    @Column(name = "admin_password", nullable = false)
    private String adminPassword;

    // adminBusinessId


    @Column(name = "admin_name")
    private String adminName;


    @Column(name = "admin_num")
    private String adminNum;


    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "admin_create_dt")
    @CreatedDate
    private ZonedDateTime adminCreateDt;

    @Column(name = "admin_update_dt")
    @LastModifiedDate
    private ZonedDateTime adminUpdateDt;

    @Column(name = "admin_current_login_dt")
    private ZonedDateTime adminCurrentLoginDt;

    @Column(name = "push_token")
    private String pushToken;

    @Column(name = "admin_memo")
    private String adminMemo;

    @Lob
    @Column(name = "admin_image")
    private String adminImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "admin_state", nullable = false)
    private StateAdmin adminState;




    @ManyToMany
    @JoinTable(
        name = "admin_user_role",
        joinColumns = {@JoinColumn(name = "admin_user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "role_name")})
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Roles> roles = new HashSet<>();

    public Set<Roles> getRoles() {
        return roles;
    }


    @ManyToMany
    @JoinTable(
        name = "admin_user_auth",
        joinColumns = {@JoinColumn(name = "admin_user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "auth_name", referencedColumnName = "auth_name")})
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Auths> auths = new HashSet<>();

    public Set<Auths> getAuths() {
        return auths;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public void setAuths(Set<Auths> auths) {
        this.auths = auths;
    }


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getAdminCurrentLoginDt() {
        return adminCurrentLoginDt;
    }

    public void setAdminCurrentLoginDt(ZonedDateTime adminCurrentLoginDt) {
        this.adminCurrentLoginDt = adminCurrentLoginDt;
    }




    public String getAdminImage() {
        return adminImage;
    }

    public void setAdminImage(String adminImage) {
        this.adminImage = adminImage;
    }

    public String getAdminMemo() {
        return adminMemo;
    }

    public void setAdminMemo(String adminMemo) {
        this.adminMemo = adminMemo;
    }

    public String getAdminLoginId() {
        return adminLoginId;
    }

    public AdminUser adminLoginId(String adminLoginId) {
        this.adminLoginId = adminLoginId;
        return this;
    }

    public void setAdminLoginId(String adminLoginId) {
        this.adminLoginId = adminLoginId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public AdminUser adminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
        return this;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminName() {
        return adminName;
    }

    public AdminUser adminName(String adminName) {
        this.adminName = adminName;
        return this;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }


    public String getAdminNum() {
        return adminNum;
    }

    public AdminUser adminNum(String adminNum) {
        this.adminNum = adminNum;
        return this;
    }

    public void setAdminNum(String adminNum) {
        this.adminNum = adminNum;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public AdminUser adminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
        return this;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public ZonedDateTime getAdminCreateDt() {
        return adminCreateDt;
    }

    public AdminUser adminCreateDt(ZonedDateTime adminCreateDt) {
        this.adminCreateDt = adminCreateDt;
        return this;
    }

    public void setAdminCreateDt(ZonedDateTime adminCreateDt) {
        this.adminCreateDt = adminCreateDt;
    }

    public ZonedDateTime getAdminUpdateDt() {
        return adminUpdateDt;
    }

    public AdminUser adminUpdateDt(ZonedDateTime adminUpdateDt) {
        this.adminUpdateDt = adminUpdateDt;
        return this;
    }

    public void setAdminUpdateDt(ZonedDateTime adminUpdateDt) {
        this.adminUpdateDt = adminUpdateDt;
    }

    public String getPushToken() {
        return pushToken;
    }

    public AdminUser pushToken(String pushToken) {
        this.pushToken = pushToken;
        return this;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public StateAdmin getAdminState() {
        return adminState;
    }

    public AdminUser adminState(StateAdmin adminState) {
        this.adminState = adminState;
        return this;
    }


    public void setAdminState(StateAdmin adminState) {
        this.adminState = adminState;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminUser)) {
            return false;
        }
        return id != null && id.equals(((AdminUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
            "id=" + id +
            ", adminLoginId='" + adminLoginId + '\'' +
            ", adminPassword='" + adminPassword + '\'' +
            ", adminName='" + adminName + '\'' +
            ", adminNum='" + adminNum + '\'' +
            ", adminEmail='" + adminEmail + '\'' +
            ", adminCreateDt=" + adminCreateDt +
            ", adminUpdateDt=" + adminUpdateDt +
            ", adminCurrentLoginDt=" + adminCurrentLoginDt +
            ", pushToken='" + pushToken + '\'' +
            ", adminMemo='" + adminMemo + '\'' +
            ", adminImage='" + adminImage + '\'' +
            ", adminState=" + adminState +
            ", roles=" + roles +
            ", auths=" + auths +
            '}';
    }
}
