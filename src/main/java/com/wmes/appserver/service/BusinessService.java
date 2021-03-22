package com.wmes.appserver.service;

import com.wmes.appserver.service.dto.BusinessDTO;
import com.wmes.appserver.service.dto.request.BusinessRequestDto;
import com.wmes.appserver.service.dto.request.BusinessResponseDto;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.wmes.appserver.domain.Business}.
 */
public interface BusinessService {

    /**
     * Save a business.
     *
     * @param businessRequestDto the entity to save.
     * @return the persisted entity.
     */
    BusinessRequestDto save(BusinessRequestDto businessRequestDto);

    /**
     * Get all the businesses.
     *
     * @return the list of entities.
     */
    List<BusinessResponseDto> findAll();


    /**
     * Get the "id" business.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BusinessResponseDto> findOne(Long id);

    /**
     * Delete the "id" business.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
