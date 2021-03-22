package com.wmes.appserver.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessResponseDto {

    private Long id;
    private String businessName;
    private String businessRepresentative;
    private String businessRegistrationNum;
    private String businessContactNum;
    private String businessCategory;
    private String businessType;




}
