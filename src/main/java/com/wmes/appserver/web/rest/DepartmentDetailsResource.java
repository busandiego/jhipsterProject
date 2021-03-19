package com.wmes.appserver.web.rest;

import com.wmes.appserver.service.DepartmentDetailsService;
import com.wmes.appserver.web.rest.errors.BadRequestAlertException;
import com.wmes.appserver.service.dto.DepartmentDetailsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.wmes.appserver.domain.DepartmentDetails}.
 */
@RestController
@RequestMapping("/api")
public class DepartmentDetailsResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentDetailsResource.class);

    private static final String ENTITY_NAME = "wmesDepartmentDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartmentDetailsService departmentDetailsService;

    public DepartmentDetailsResource(DepartmentDetailsService departmentDetailsService) {
        this.departmentDetailsService = departmentDetailsService;
    }

    /**
     * {@code POST  /department-details} : Create a new departmentDetails.
     *
     * @param departmentDetailsDTO the departmentDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departmentDetailsDTO, or with status {@code 400 (Bad Request)} if the departmentDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/department-details")
    public ResponseEntity<DepartmentDetailsDTO> createDepartmentDetails(@RequestBody DepartmentDetailsDTO departmentDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save DepartmentDetails : {}", departmentDetailsDTO);
        if (departmentDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new departmentDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepartmentDetailsDTO result = departmentDetailsService.save(departmentDetailsDTO);
        return ResponseEntity.created(new URI("/api/department-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /department-details} : Updates an existing departmentDetails.
     *
     * @param departmentDetailsDTO the departmentDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departmentDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the departmentDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departmentDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/department-details")
    public ResponseEntity<DepartmentDetailsDTO> updateDepartmentDetails(@RequestBody DepartmentDetailsDTO departmentDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update DepartmentDetails : {}", departmentDetailsDTO);
        if (departmentDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepartmentDetailsDTO result = departmentDetailsService.save(departmentDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departmentDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /department-details} : get all the departmentDetails.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departmentDetails in body.
     */
    @GetMapping("/department-details")
    public List<DepartmentDetailsDTO> getAllDepartmentDetails(@RequestParam(required = false) String filter) {
        if ("employee-is-null".equals(filter)) {
            log.debug("REST request to get all DepartmentDetailss where employee is null");
            return departmentDetailsService.findAllWhereEmployeeIsNull();
        }
        log.debug("REST request to get all DepartmentDetails");
        return departmentDetailsService.findAll();
    }

    /**
     * {@code GET  /department-details/:id} : get the "id" departmentDetails.
     *
     * @param id the id of the departmentDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departmentDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/department-details/{id}")
    public ResponseEntity<DepartmentDetailsDTO> getDepartmentDetails(@PathVariable Long id) {
        log.debug("REST request to get DepartmentDetails : {}", id);
        Optional<DepartmentDetailsDTO> departmentDetailsDTO = departmentDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(departmentDetailsDTO);
    }

    /**
     * {@code DELETE  /department-details/:id} : delete the "id" departmentDetails.
     *
     * @param id the id of the departmentDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/department-details/{id}")
    public ResponseEntity<Void> deleteDepartmentDetails(@PathVariable Long id) {
        log.debug("REST request to delete DepartmentDetails : {}", id);
        departmentDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
