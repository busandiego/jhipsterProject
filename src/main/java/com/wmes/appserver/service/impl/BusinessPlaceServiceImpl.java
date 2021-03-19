package com.wmes.appserver.service.impl;

import com.wmes.appserver.service.BusinessPlaceService;
import com.wmes.appserver.domain.BusinessPlace;
import com.wmes.appserver.repository.BusinessPlaceRepository;
import com.wmes.appserver.service.dto.BusinessPlaceDTO;
import com.wmes.appserver.service.mapper.BusinessPlaceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BusinessPlace}.
 */
@Service
@Transactional
public class BusinessPlaceServiceImpl implements BusinessPlaceService {

    private final Logger log = LoggerFactory.getLogger(BusinessPlaceServiceImpl.class);

    private final BusinessPlaceRepository businessPlaceRepository;

    private final BusinessPlaceMapper businessPlaceMapper;

    public BusinessPlaceServiceImpl(BusinessPlaceRepository businessPlaceRepository, BusinessPlaceMapper businessPlaceMapper) {
        this.businessPlaceRepository = businessPlaceRepository;
        this.businessPlaceMapper = businessPlaceMapper;
    }

    /**
     * Save a businessPlace.
     *
     * @param businessPlaceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BusinessPlaceDTO save(BusinessPlaceDTO businessPlaceDTO) {
        log.debug("Request to save BusinessPlace : {}", businessPlaceDTO);
        BusinessPlace businessPlace = businessPlaceMapper.toEntity(businessPlaceDTO);
        businessPlace = businessPlaceRepository.save(businessPlace);
        return businessPlaceMapper.toDto(businessPlace);
    }

    /**
     * Get all the businessPlaces.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BusinessPlaceDTO> findAll() {
        log.debug("Request to get all BusinessPlaces");
        return businessPlaceRepository.findAll().stream()
            .map(businessPlaceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one businessPlace by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessPlaceDTO> findOne(Long id) {
        log.debug("Request to get BusinessPlace : {}", id);
        return businessPlaceRepository.findById(id)
            .map(businessPlaceMapper::toDto);
    }

    /**
     * Delete the businessPlace by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusinessPlace : {}", id);
        businessPlaceRepository.deleteById(id);
    }
}
