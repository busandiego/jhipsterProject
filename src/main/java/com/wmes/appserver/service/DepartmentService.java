package com.wmes.appserver.service;

import com.wmes.appserver.service.dto.DepartmentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmes.appserver.domain.Department}.
 */
public interface DepartmentService {

    /**
     * Save a department.
     *
     * @param departmentDTO the entity to save.
     * @return the persisted entity.
     */
    DepartmentDTO save(DepartmentDTO departmentDTO);

    /**
     * Get all the departments.
     *
     * @return the list of entities.
     */
    List<DepartmentDTO> findAll();
    /**
     * Get all the DepartmentDTO where Employee is {@code null}.
     *
     * @return the list of entities.
     */
    List<DepartmentDTO> findAllWhereEmployeeIsNull();


    /**
     * Get the "id" department.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DepartmentDTO> findOne(Long id);

    /**
     * Delete the "id" department.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
