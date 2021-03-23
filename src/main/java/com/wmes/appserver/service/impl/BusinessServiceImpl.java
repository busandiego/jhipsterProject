package com.wmes.appserver.service.impl;

import com.wmes.appserver.domain.BusinessPlace;
import com.wmes.appserver.repository.BusinessPlaceRepository;
import com.wmes.appserver.security.DomainUserDetailsService;
import com.wmes.appserver.service.BusinessService;
import com.wmes.appserver.domain.Business;
import com.wmes.appserver.repository.BusinessRepository;
import com.wmes.appserver.service.dto.BusinessDTO;
import com.wmes.appserver.service.dto.request.BusinessRequestDto;
import com.wmes.appserver.service.dto.request.BusinessResponseDto;
import com.wmes.appserver.service.mapper.BusinessMapper;
import com.wmes.appserver.service.mapper.BusinessPlaceMapper;

import com.wmes.appserver.service.mapper.BusinessResponseDtoMapper;
import com.wmes.appserver.web.rest.errors.ErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Business}.
 */
@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {

    private final Logger log = LoggerFactory.getLogger(BusinessServiceImpl.class);

    private final BusinessRepository businessRepository;

    private final BusinessPlaceRepository businessPlaceRepository;

    private final BusinessMapper businessMapper;

    private final BusinessPlaceMapper businessPlaceMapper;

    private final BusinessResponseDtoMapper businessResponseDtoMapper;

    private final DomainUserDetailsService domainUserDetailsService;
    //private final Admin

    public BusinessServiceImpl(BusinessRepository businessRepository, BusinessMapper businessMapper,
                               BusinessPlaceRepository businessPlaceRepository,
                               BusinessResponseDtoMapper businessResponseDtoMapper,
                               BusinessPlaceMapper businessPlaceMapper,
                               DomainUserDetailsService domainUserDetailsService)
    {

        this.businessResponseDtoMapper = businessResponseDtoMapper;
        this.businessRepository = businessRepository;
        this.businessMapper = businessMapper;
        this.businessPlaceRepository = businessPlaceRepository;
        this.businessPlaceMapper = businessPlaceMapper;
        this.domainUserDetailsService = domainUserDetailsService;
    }

    /**
     * Save a business.
     *
     * @return the persisted entity.
     */
    @Override
    public BusinessRequestDto save(BusinessRequestDto businessRequestDto) {
        log.debug("Request to save Business : {}", businessRequestDto);
        Business business = businessMapper.toEntity(businessRequestDto);
        //business.setAdminUserId();
        System.out.println("business: " + business);
        System.out.println("business.id: " + business.getId());
        business = businessRepository.save(business);
        // set하는 로직
        System.out.println("저장완료 business: " + business);
        Optional<Business> businessOptional = businessRepository.findTop1ByBusinessName(businessRequestDto.getBusinessName());

       // Long businessQuery = businessRepository.findTopById(business.getId());
        System.out.println("businessId: " + businessOptional.get().getId());
        Long businessId = businessOptional.get().getId();
        // .orElseThrow(ErrorMessages::) 오류 추가
        BusinessPlace businessPlace = new BusinessPlace();


        businessPlace.setBusinessId(businessId);
        businessPlace.setBpName("본사");
        businessPlace.setIsHeadquarter(true);
        businessPlace.setBpZipAddress(businessRequestDto.getBpZipAddress());
        businessPlace.setBpAddress(businessRequestDto.getBpAddress());
        businessPlace.setBpDetailAddress(businessRequestDto.getBpDetailAddress());
        businessPlace.setBpFaxNumber(businessRequestDto.getBpFaxNumber());
        businessPlace.setBpNumber(businessRequestDto.getBpNumber());
        businessPlace.setCreatedDate(ZonedDateTime.now());

        // TODO 만약에 본사가 이미 존재하면 저장 못한다

        // bp 저장
       businessPlaceRepository.save(businessPlace);


        BusinessRequestDto businessResult = businessMapper.toDto(business);
       // businessResult.setBusinessId(businessId);
        businessResult.setIsHeadquarter(true);
        businessResult.setBpName("본사");
        businessResult.setBpZipAddress(businessRequestDto.getBpZipAddress());
        businessResult.setBpAddress(businessRequestDto.getBpAddress());
        businessResult.setBpDetailAddress(businessRequestDto.getBpDetailAddress());
        businessResult.setBpFaxNumber(businessRequestDto.getBpFaxNumber());
        businessResult.setBpNumber(businessRequestDto.getBpNumber());
        businessResult.setCreatedDate(ZonedDateTime.now());




        return businessResult;
    }

    /**
     * Get all the businesses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BusinessResponseDto> findAll() {
        log.debug("Request to get all Businesses");
        return businessRepository.findAll().stream()
            .map(businessResponseDtoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one business by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessResponseDto> findOne(Long id) {
        log.debug("Request to get Business : {}", id);
        return businessRepository.findById(id)
            .map(businessResponseDtoMapper::toDto);
    }

    /**
     * Delete the business by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Business : {}", id);
        businessPlaceRepository.deleteByBusinessId(id);
        businessRepository.deleteById(id);
    }
}
