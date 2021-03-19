package com.wmes.appserver.service.impl;

import com.wmes.appserver.service.BusinessService;
import com.wmes.appserver.domain.Business;
import com.wmes.appserver.repository.BusinessRepository;
import com.wmes.appserver.service.dto.BusinessDTO;
import com.wmes.appserver.service.mapper.BusinessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Business}.
 */
@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {

    private final Logger log = LoggerFactory.getLogger(BusinessServiceImpl.class);

    private final BusinessRepository businessRepository;

    private final BusinessMapper businessMapper;

    public BusinessServiceImpl(BusinessRepository businessRepository, BusinessMapper businessMapper) {
        this.businessRepository = businessRepository;
        this.businessMapper = businessMapper;
    }

    /**
     * Save a business.
     *
     * @param businessDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BusinessDTO save(BusinessDTO businessDTO) {
        log.debug("Request to save Business : {}", businessDTO);
        Business business = businessMapper.toEntity(businessDTO);
        business = businessRepository.save(business);
        return businessMapper.toDto(business);
    }

    /**
     * Get all the businesses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BusinessDTO> findAll() {
        log.debug("Request to get all Businesses");
        return businessRepository.findAll().stream()
            .map(businessMapper::toDto)
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
    public Optional<BusinessDTO> findOne(Long id) {
        log.debug("Request to get Business : {}", id);
        return businessRepository.findById(id)
            .map(businessMapper::toDto);
    }

    /**
     * Delete the business by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Business : {}", id);
        businessRepository.deleteById(id);
    }
}
