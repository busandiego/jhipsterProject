package com.wmes.appserver.service;

import com.wmes.appserver.service.dto.BusinessPlaceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmes.appserver.domain.BusinessPlace}.
 */
public interface BusinessPlaceService {

    /**
     * Save a businessPlace.
     *
     * @param businessPlaceDTO the entity to save.
     * @return the persisted entity.
     */
    BusinessPlaceDTO save(BusinessPlaceDTO businessPlaceDTO);

    /**
     * Get all the businessPlaces.
     *
     * @return the list of entities.
     */
    List<BusinessPlaceDTO> findAll();


    /**
     * Get the "id" businessPlace.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BusinessPlaceDTO> findOne(Long id);

    /**
     * Delete the "id" businessPlace.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
