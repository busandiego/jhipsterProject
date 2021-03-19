package com.wmes.appserver.service;

import com.wmes.appserver.service.dto.MaterialDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmes.appserver.domain.Material}.
 */
public interface MaterialService {

    /**
     * Save a material.
     *
     * @param materialDTO the entity to save.
     * @return the persisted entity.
     */
    MaterialDTO save(MaterialDTO materialDTO);

    /**
     * Get all the materials.
     *
     * @return the list of entities.
     */
    List<MaterialDTO> findAll();


    /**
     * Get the "id" material.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MaterialDTO> findOne(Long id);

    /**
     * Delete the "id" material.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
