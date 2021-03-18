package com.wmes.appserver.dto;

import com.wmes.appserver.config.Constants;
import com.wmes.appserver.domain.AdminUser;
import com.wmes.appserver.domain.Auths;
import com.wmes.appserver.domain.Roles;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AdminUserDto {

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String adminLoginId;

    @Size(max = 50)
    private String adminPassword;

    @Size(max = 50)
    private String adminName;

    @Size(max = 50)
    private String adminNick;

    @Size(max = 50)
    private String adminNum;

    @Email
    @Size(min = 5, max = 254)
    private String adminEmail;

    private ZonedDateTime adminCreateDt;

    private ZonedDateTime adminUpdateDt;

    private String pushToken;

    private String adminState;

    private Set<String> roles;

    private Set<String> auths;

    public AdminUserDto() {
        // Empty constructor needed for Jackson.
    }

    public AdminUserDto(AdminUser adminUser) {
        this.id = adminUser.getId();
        this.adminLoginId = adminUser.getAdminLoginId();
        this.adminName = adminUser.getAdminName();
        this.adminNum = adminUser.getAdminNum();
        this.adminEmail = adminUser.getAdminEmail();
        this.adminCreateDt = adminUser.getAdminCreateDt();
        this.adminUpdateDt = adminUser.getAdminUpdateDt();
        this.pushToken = adminUser.getPushToken();
        this.adminState = adminUser.getAdminState().toString();
        this.roles = adminUser.getRoles().stream().map(Roles::getName).collect(Collectors.toSet());
        this.auths = adminUser.getAuths().stream().map(Auths::getName).collect(Collectors.toSet());
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdminLoginId() {
        return adminLoginId;
    }

    public void setAdminLoginId(String adminLoginId) {
        this.adminLoginId = adminLoginId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminNick() {
        return adminNick;
    }

    public void setAdminNick(String adminNick) {
        this.adminNick = adminNick;
    }

    public String getAdminNum() {
        return adminNum;
    }

    public void setAdminNum(String adminNum) {
        this.adminNum = adminNum;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public ZonedDateTime getAdminCreateDt() {
        return adminCreateDt;
    }

    public void setAdminCreateDt(ZonedDateTime adminCreateDt) {
        this.adminCreateDt = adminCreateDt;
    }

    public ZonedDateTime getAdminUpdateDt() {
        return adminUpdateDt;
    }

    public void setAdminUpdateDt(ZonedDateTime adminUpdateDt) {
        this.adminUpdateDt = adminUpdateDt;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public String getAdminState() {
        return adminState;
    }

    public void setAdminState(String adminState) {
        this.adminState = adminState;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getAuths() {
        return auths;
    }

    public void setAuths(Set<String> auths) {
        this.auths = auths;
    }
}
