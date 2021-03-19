package com.wmes.appserver.service;

import com.wmes.appserver.service.dto.DepartmentDetailsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmes.appserver.domain.DepartmentDetails}.
 */
public interface DepartmentDetailsService {

    /**
     * Save a departmentDetails.
     *
     * @param departmentDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    DepartmentDetailsDTO save(DepartmentDetailsDTO departmentDetailsDTO);

    /**
     * Get all the departmentDetails.
     *
     * @return the list of entities.
     */
    List<DepartmentDetailsDTO> findAll();
    /**
     * Get all the DepartmentDetailsDTO where Employee is {@code null}.
     *
     * @return the list of entities.
     */
    List<DepartmentDetailsDTO> findAllWhereEmployeeIsNull();


    /**
     * Get the "id" departmentDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DepartmentDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" departmentDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
