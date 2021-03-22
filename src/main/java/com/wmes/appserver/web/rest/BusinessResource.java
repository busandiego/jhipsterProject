package com.wmes.appserver.web.rest;

import com.wmes.appserver.service.BusinessPlaceService;
import com.wmes.appserver.service.BusinessService;
import com.wmes.appserver.service.dto.BusinessPlaceDTO;
import com.wmes.appserver.service.dto.request.BusinessRequestDto;
import com.wmes.appserver.service.dto.request.BusinessResponseDto;
import com.wmes.appserver.service.impl.BusinessServiceImpl;
import com.wmes.appserver.web.rest.errors.BadRequestAlertException;
import com.wmes.appserver.service.dto.BusinessDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.wmes.appserver.domain.Business}.
 */
@RestController
@RequestMapping("/api")
public class BusinessResource {

    private final Logger log = LoggerFactory.getLogger(BusinessResource.class);

    private static final String ENTITY_NAME = "wmesBusiness";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessService businessService;

    private final BusinessServiceImpl businessServiceImpl;

    private final BusinessPlaceService businessPlaceService;

    public BusinessResource(BusinessService businessService, BusinessPlaceService businessPlaceService,
                            BusinessServiceImpl businessServiceImpl) {
        this.businessPlaceService = businessPlaceService;
        this.businessService = businessService;
        this.businessServiceImpl = businessServiceImpl;
    }

    /**
     * {@code POST  /businesses} : Create a new business.
     *
     *
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessDTO, or with status {@code 400 (Bad Request)} if the business has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/businesses")
    public ResponseEntity<BusinessRequestDto> createBusiness(@Valid @RequestBody BusinessRequestDto businessRequestDto, BusinessPlaceDTO businessPlaceDTO) throws URISyntaxException {
        log.debug("REST request to save BusinessRequestDto : {}", businessRequestDto, businessPlaceDTO);
        if (businessRequestDto.getId() != null) {
            throw new BadRequestAlertException("A new business cannot already have an ID", ENTITY_NAME, "idexists");
        }
       // BusinessPlaceDTO businessPlaceResult = businessPlaceService.save();
        // BusinessRequestDto result = businessService.save(businessRequestDto);
        BusinessRequestDto result = businessServiceImpl.save(businessRequestDto);
        return ResponseEntity.created(new URI("/api/businesses"))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /businesses} : Updates an existing business.
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessDTO,
     * or with status {@code 400 (Bad Request)} if the businessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/businesses")
    public ResponseEntity<BusinessRequestDto> updateBusiness(@Valid @RequestBody BusinessRequestDto businessRequestDto) throws URISyntaxException {
        log.debug("REST request to update BusinessRequestDto : {}", businessRequestDto);
        if (businessRequestDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessRequestDto result = businessServiceImpl.save(businessRequestDto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessRequestDto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /businesses} : get all the businesses.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businesses in body.
     */
    @GetMapping("/businesses")
    public List<BusinessResponseDto> getAllBusinesses() {
        log.debug("REST request to get all Businesses");
        return businessService.findAll();
    }

    /**
     * {@code GET  /businesses/:id} : get the "id" business.
     *
     * @param id the id of the businessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/businesses/{id}")
    public ResponseEntity<BusinessResponseDto> getBusiness(@PathVariable Long id) {
        log.debug("REST request to get Business : {}", id);
        Optional<BusinessResponseDto> businessResponseDto = businessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessResponseDto);
    }

    /**
     * {@code DELETE  /businesses/:id} : delete the "id" business.
     *
     * @param id the id of the businessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/businesses/{id}")
    public ResponseEntity<Void> deleteBusiness(@PathVariable Long id) {
        log.debug("REST request to delete Business : {}", id);
        businessService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
