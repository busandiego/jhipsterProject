package com.wmes.appserver.service.impl;

import com.wmes.appserver.service.CustomerKindService;
import com.wmes.appserver.domain.CustomerKind;
import com.wmes.appserver.repository.CustomerKindRepository;
import com.wmes.appserver.service.dto.CustomerKindDTO;
import com.wmes.appserver.service.mapper.CustomerKindMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CustomerKind}.
 */
@Service
@Transactional
public class CustomerKindServiceImpl implements CustomerKindService {

    private final Logger log = LoggerFactory.getLogger(CustomerKindServiceImpl.class);

    private final CustomerKindRepository customerKindRepository;

    private final CustomerKindMapper customerKindMapper;

    public CustomerKindServiceImpl(CustomerKindRepository customerKindRepository, CustomerKindMapper customerKindMapper) {
        this.customerKindRepository = customerKindRepository;
        this.customerKindMapper = customerKindMapper;
    }

    /**
     * Save a customerKind.
     *
     * @param customerKindDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerKindDTO save(CustomerKindDTO customerKindDTO) {
        log.debug("Request to save CustomerKind : {}", customerKindDTO);
        CustomerKind customerKind = customerKindMapper.toEntity(customerKindDTO);
        customerKind = customerKindRepository.save(customerKind);
        return customerKindMapper.toDto(customerKind);
    }

    /**
     * Get all the customerKinds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerKindDTO> findAll() {
        log.debug("Request to get all CustomerKinds");
        return customerKindRepository.findAll().stream()
            .map(customerKindMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customerKind by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerKindDTO> findOne(Long id) {
        log.debug("Request to get CustomerKind : {}", id);
        return customerKindRepository.findById(id)
            .map(customerKindMapper::toDto);
    }

    /**
     * Delete the customerKind by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerKind : {}", id);
        customerKindRepository.deleteById(id);
    }
}
