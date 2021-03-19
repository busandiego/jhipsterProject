package com.wmes.appserver.service;

import com.wmes.appserver.service.dto.BusinessDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmes.appserver.domain.Business}.
 */
public interface BusinessService {

    /**
     * Save a business.
     *
     * @param businessDTO the entity to save.
     * @return the persisted entity.
     */
    BusinessDTO save(BusinessDTO businessDTO);

    /**
     * Get all the businesses.
     *
     * @return the list of entities.
     */
    List<BusinessDTO> findAll();


    /**
     * Get the "id" business.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BusinessDTO> findOne(Long id);

    /**
     * Delete the "id" business.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
