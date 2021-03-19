package com.wmes.appserver.service.impl;

import com.wmes.appserver.service.PositionService;
import com.wmes.appserver.domain.Position;
import com.wmes.appserver.repository.PositionRepository;
import com.wmes.appserver.service.dto.PositionDTO;
import com.wmes.appserver.service.mapper.PositionMapper;
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
 * Service Implementation for managing {@link Position}.
 */
@Service
@Transactional
public class PositionServiceImpl implements PositionService {

    private final Logger log = LoggerFactory.getLogger(PositionServiceImpl.class);

    private final PositionRepository positionRepository;

    private final PositionMapper positionMapper;

    public PositionServiceImpl(PositionRepository positionRepository, PositionMapper positionMapper) {
        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
    }

    /**
     * Save a position.
     *
     * @param positionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PositionDTO save(PositionDTO positionDTO) {
        log.debug("Request to save Position : {}", positionDTO);
        Position position = positionMapper.toEntity(positionDTO);
        position = positionRepository.save(position);
        return positionMapper.toDto(position);
    }

    /**
     * Get all the positions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PositionDTO> findAll() {
        log.debug("Request to get all Positions");
        return positionRepository.findAll().stream()
            .map(positionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the positions where Employee is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<PositionDTO> findAllWhereEmployeeIsNull() {
        log.debug("Request to get all positions where Employee is null");
        return StreamSupport
            .stream(positionRepository.findAll().spliterator(), false)
            .filter(position -> position.getEmployee() == null)
            .map(positionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one position by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PositionDTO> findOne(Long id) {
        log.debug("Request to get Position : {}", id);
        return positionRepository.findById(id)
            .map(positionMapper::toDto);
    }

    /**
     * Delete the position by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Position : {}", id);
        positionRepository.deleteById(id);
    }
}
