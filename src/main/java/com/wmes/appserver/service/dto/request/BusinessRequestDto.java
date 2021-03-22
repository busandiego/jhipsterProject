package com.wmes.appserver.service.dto.request;

import com.wmes.appserver.domain.BusinessPlace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessRequestDto {

    private Long id;
    private String businessName;
    private String businessRepresentative;
    private String businessRegistrationNum;
    private String businessContactNum;
    private String businessCategory;
    private String businessType;


 //   private BusinessPlace businessPlace;

    private Boolean isHeadquarter;
    private String bpName;
    private String bpZipAddress;
    private String bpAddress;
    private String bpDetailAddress;
    private String bpFaxNumber;
    private String bpNumber;
//    private Long businessId;

    private ZonedDateTime createdDate;

}
