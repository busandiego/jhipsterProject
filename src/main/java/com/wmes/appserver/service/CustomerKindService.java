package com.wmes.appserver.service;

import com.wmes.appserver.service.dto.CustomerKindDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmes.appserver.domain.CustomerKind}.
 */
public interface CustomerKindService {

    /**
     * Save a customerKind.
     *
     * @param customerKindDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerKindDTO save(CustomerKindDTO customerKindDTO);

    /**
     * Get all the customerKinds.
     *
     * @return the list of entities.
     */
    List<CustomerKindDTO> findAll();


    /**
     * Get the "id" customerKind.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerKindDTO> findOne(Long id);

    /**
     * Delete the "id" customerKind.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
