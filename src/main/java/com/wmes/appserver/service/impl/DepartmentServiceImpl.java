package com.wmes.appserver.service.impl;

import com.wmes.appserver.service.DepartmentService;
import com.wmes.appserver.domain.Department;
import com.wmes.appserver.repository.DepartmentRepository;
import com.wmes.appserver.service.dto.DepartmentDTO;
import com.wmes.appserver.service.mapper.DepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Department}.
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    /**
     * Save a department.
     *
     * @param departmentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        log.debug("Request to save Department : {}", departmentDTO);
        Department department = departmentMapper.toEntity(departmentDTO);
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    /**
     * Get all the departments.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> findAll() {
        log.debug("Request to get all Departments");
        return departmentRepository.findAll().stream()
            .map(departmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the departments where Employee is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<DepartmentDTO> findAllWhereEmployeeIsNull() {
        log.debug("Request to get all departments where Employee is null");
        return StreamSupport
            .stream(departmentRepository.findAll().spliterator(), false)
            .filter(department -> department.getEmployee() == null)
            .map(departmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one department by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentDTO> findOne(Long id) {
        log.debug("Request to get Department : {}", id);
        return departmentRepository.findById(id)
            .map(departmentMapper::toDto);
    }

    /**
     * Delete the department by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Department : {}", id);
        departmentRepository.deleteById(id);
    }
}
