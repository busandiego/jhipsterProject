package com.wmes.appserver.service.impl;

import com.wmes.appserver.service.DepartmentDetailsService;
import com.wmes.appserver.domain.DepartmentDetails;
import com.wmes.appserver.repository.DepartmentDetailsRepository;
import com.wmes.appserver.service.dto.DepartmentDetailsDTO;
import com.wmes.appserver.service.mapper.DepartmentDetailsMapper;
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
 * Service Implementation for managing {@link DepartmentDetails}.
 */
@Service
@Transactional
public class DepartmentDetailsServiceImpl implements DepartmentDetailsService {

    private final Logger log = LoggerFactory.getLogger(DepartmentDetailsServiceImpl.class);

    private final DepartmentDetailsRepository departmentDetailsRepository;

    private final DepartmentDetailsMapper departmentDetailsMapper;

    public DepartmentDetailsServiceImpl(DepartmentDetailsRepository departmentDetailsRepository, DepartmentDetailsMapper departmentDetailsMapper) {
        this.departmentDetailsRepository = departmentDetailsRepository;
        this.departmentDetailsMapper = departmentDetailsMapper;
    }

    /**
     * Save a departmentDetails.
     *
     * @param departmentDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DepartmentDetailsDTO save(DepartmentDetailsDTO departmentDetailsDTO) {
        log.debug("Request to save DepartmentDetails : {}", departmentDetailsDTO);
        DepartmentDetails departmentDetails = departmentDetailsMapper.toEntity(departmentDetailsDTO);
        departmentDetails = departmentDetailsRepository.save(departmentDetails);
        return departmentDetailsMapper.toDto(departmentDetails);
    }

    /**
     * Get all the departmentDetails.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDetailsDTO> findAll() {
        log.debug("Request to get all DepartmentDetails");
        return departmentDetailsRepository.findAll().stream()
            .map(departmentDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the departmentDetails where Employee is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<DepartmentDetailsDTO> findAllWhereEmployeeIsNull() {
        log.debug("Request to get all departmentDetails where Employee is null");
        return StreamSupport
            .stream(departmentDetailsRepository.findAll().spliterator(), false)
            .filter(departmentDetails -> departmentDetails.getEmployee() == null)
            .map(departmentDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one departmentDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentDetailsDTO> findOne(Long id) {
        log.debug("Request to get DepartmentDetails : {}", id);
        return departmentDetailsRepository.findById(id)
            .map(departmentDetailsMapper::toDto);
    }

    /**
     * Delete the departmentDetails by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DepartmentDetails : {}", id);
        departmentDetailsRepository.deleteById(id);
    }
}
