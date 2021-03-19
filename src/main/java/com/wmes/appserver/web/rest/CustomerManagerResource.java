package com.wmes.appserver.web.rest;

import com.wmes.appserver.service.CustomerManagerService;
import com.wmes.appserver.web.rest.errors.BadRequestAlertException;
import com.wmes.appserver.service.dto.CustomerManagerDTO;

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

/**
 * REST controller for managing {@link com.wmes.appserver.domain.CustomerManager}.
 */
@RestController
@RequestMapping("/api")
public class CustomerManagerResource {

    private final Logger log = LoggerFactory.getLogger(CustomerManagerResource.class);

    private static final String ENTITY_NAME = "wmesCustomerManager";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerManagerService customerManagerService;

    public CustomerManagerResource(CustomerManagerService customerManagerService) {
        this.customerManagerService = customerManagerService;
    }

    /**
     * {@code POST  /customer-managers} : Create a new customerManager.
     *
     * @param customerManagerDTO the customerManagerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerManagerDTO, or with status {@code 400 (Bad Request)} if the customerManager has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-managers")
    public ResponseEntity<CustomerManagerDTO> createCustomerManager(@RequestBody CustomerManagerDTO customerManagerDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerManager : {}", customerManagerDTO);
        if (customerManagerDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerManager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerManagerDTO result = customerManagerService.save(customerManagerDTO);
        return ResponseEntity.created(new URI("/api/customer-managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-managers} : Updates an existing customerManager.
     *
     * @param customerManagerDTO the customerManagerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerManagerDTO,
     * or with status {@code 400 (Bad Request)} if the customerManagerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerManagerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-managers")
    public ResponseEntity<CustomerManagerDTO> updateCustomerManager(@RequestBody CustomerManagerDTO customerManagerDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerManager : {}", customerManagerDTO);
        if (customerManagerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerManagerDTO result = customerManagerService.save(customerManagerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerManagerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-managers} : get all the customerManagers.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerManagers in body.
     */
    @GetMapping("/customer-managers")
    public List<CustomerManagerDTO> getAllCustomerManagers() {
        log.debug("REST request to get all CustomerManagers");
        return customerManagerService.findAll();
    }

    /**
     * {@code GET  /customer-managers/:id} : get the "id" customerManager.
     *
     * @param id the id of the customerManagerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerManagerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-managers/{id}")
    public ResponseEntity<CustomerManagerDTO> getCustomerManager(@PathVariable Long id) {
        log.debug("REST request to get CustomerManager : {}", id);
        Optional<CustomerManagerDTO> customerManagerDTO = customerManagerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerManagerDTO);
    }

    /**
     * {@code DELETE  /customer-managers/:id} : delete the "id" customerManager.
     *
     * @param id the id of the customerManagerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-managers/{id}")
    public ResponseEntity<Void> deleteCustomerManager(@PathVariable Long id) {
        log.debug("REST request to delete CustomerManager : {}", id);
        customerManagerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
