package com.wmes.appserver.service.impl;

import com.wmes.appserver.service.CustomerManagerService;
import com.wmes.appserver.domain.CustomerManager;
import com.wmes.appserver.repository.CustomerManagerRepository;
import com.wmes.appserver.service.dto.CustomerManagerDTO;
import com.wmes.appserver.service.mapper.CustomerManagerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CustomerManager}.
 */
@Service
@Transactional
public class CustomerManagerServiceImpl implements CustomerManagerService {

    private final Logger log = LoggerFactory.getLogger(CustomerManagerServiceImpl.class);

    private final CustomerManagerRepository customerManagerRepository;

    private final CustomerManagerMapper customerManagerMapper;

    public CustomerManagerServiceImpl(CustomerManagerRepository customerManagerRepository, CustomerManagerMapper customerManagerMapper) {
        this.customerManagerRepository = customerManagerRepository;
        this.customerManagerMapper = customerManagerMapper;
    }

    /**
     * Save a customerManager.
     *
     * @param customerManagerDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerManagerDTO save(CustomerManagerDTO customerManagerDTO) {
        log.debug("Request to save CustomerManager : {}", customerManagerDTO);
        CustomerManager customerManager = customerManagerMapper.toEntity(customerManagerDTO);
        customerManager = customerManagerRepository.save(customerManager);
        return customerManagerMapper.toDto(customerManager);
    }

    /**
     * Get all the customerManagers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerManagerDTO> findAll() {
        log.debug("Request to get all CustomerManagers");
        return customerManagerRepository.findAll().stream()
            .map(customerManagerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customerManager by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerManagerDTO> findOne(Long id) {
        log.debug("Request to get CustomerManager : {}", id);
        return customerManagerRepository.findById(id)
            .map(customerManagerMapper::toDto);
    }

    /**
     * Delete the customerManager by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerManager : {}", id);
        customerManagerRepository.deleteById(id);
    }
}
