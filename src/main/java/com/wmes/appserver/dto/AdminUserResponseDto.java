package com.wmes.appserver.dto;

import com.wmes.appserver.domain.StateAdmin;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Data
public class AdminUserResponseDto {

    private Long id;

    @Size(min = 2, max = 50)
    private String adminLoginId;

    @Size(min = 4, max = 50)
    private String adminPassword;

    private String adminName;
    private String adminNum;
    private String adminEmail;
    private ZonedDateTime adminCreateDt;
    private ZonedDateTime adminUpdateDt;
    private ZonedDateTime adminCurrentLoginDt;
    private String adminMemo;
    private StateAdmin adminState;

}
