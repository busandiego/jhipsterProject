package com.wmes.appserver.web.rest;

import com.wmes.appserver.service.BusinessPlaceService;
import com.wmes.appserver.web.rest.errors.BadRequestAlertException;
import com.wmes.appserver.service.dto.BusinessPlaceDTO;

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
 * REST controller for managing {@link com.wmes.appserver.domain.BusinessPlace}.
 */
@RestController
@RequestMapping("/api")
public class BusinessPlaceResource {

    private final Logger log = LoggerFactory.getLogger(BusinessPlaceResource.class);

    private static final String ENTITY_NAME = "wmesBusinessPlace";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessPlaceService businessPlaceService;

    public BusinessPlaceResource(BusinessPlaceService businessPlaceService) {
        this.businessPlaceService = businessPlaceService;
    }

    /**
     * {@code POST  /business-places} : Create a new businessPlace.
     *
     * @param businessPlaceDTO the businessPlaceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessPlaceDTO, or with status {@code 400 (Bad Request)} if the businessPlace has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-places")
    public ResponseEntity<BusinessPlaceDTO> createBusinessPlace(@Valid @RequestBody BusinessPlaceDTO businessPlaceDTO) throws URISyntaxException {
        log.debug("REST request to save BusinessPlace : {}", businessPlaceDTO);
        if (businessPlaceDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessPlace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessPlaceDTO result = businessPlaceService.save(businessPlaceDTO);
        return ResponseEntity.created(new URI("/api/business-places/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-places} : Updates an existing businessPlace.
     *
     * @param businessPlaceDTO the businessPlaceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessPlaceDTO,
     * or with status {@code 400 (Bad Request)} if the businessPlaceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessPlaceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-places")
    public ResponseEntity<BusinessPlaceDTO> updateBusinessPlace(@Valid @RequestBody BusinessPlaceDTO businessPlaceDTO) throws URISyntaxException {
        log.debug("REST request to update BusinessPlace : {}", businessPlaceDTO);
        if (businessPlaceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessPlaceDTO result = businessPlaceService.save(businessPlaceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessPlaceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /business-places} : get all the businessPlaces.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessPlaces in body.
     */
    @GetMapping("/business-places")
    public List<BusinessPlaceDTO> getAllBusinessPlaces() {
        log.debug("REST request to get all BusinessPlaces");
        return businessPlaceService.findAll();
    }

    /**
     * {@code GET  /business-places/:id} : get the "id" businessPlace.
     *
     * @param id the id of the businessPlaceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessPlaceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-places/{id}")
    public ResponseEntity<BusinessPlaceDTO> getBusinessPlace(@PathVariable Long id) {
        log.debug("REST request to get BusinessPlace : {}", id);
        Optional<BusinessPlaceDTO> businessPlaceDTO = businessPlaceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessPlaceDTO);
    }

    /**
     * {@code DELETE  /business-places/:id} : delete the "id" businessPlace.
     *
     * @param id the id of the businessPlaceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-places/{id}")
    public ResponseEntity<Void> deleteBusinessPlace(@PathVariable Long id) {
        log.debug("REST request to delete BusinessPlace : {}", id);
        businessPlaceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
