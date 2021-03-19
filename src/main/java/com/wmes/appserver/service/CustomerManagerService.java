package com.wmes.appserver.service;

import com.wmes.appserver.service.dto.CustomerManagerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmes.appserver.domain.CustomerManager}.
 */
public interface CustomerManagerService {

    /**
     * Save a customerManager.
     *
     * @param customerManagerDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerManagerDTO save(CustomerManagerDTO customerManagerDTO);

    /**
     * Get all the customerManagers.
     *
     * @return the list of entities.
     */
    List<CustomerManagerDTO> findAll();


    /**
     * Get the "id" customerManager.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerManagerDTO> findOne(Long id);

    /**
     * Delete the "id" customerManager.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
