package com.wmes.appserver.web.rest;

import com.wmes.appserver.service.CustomerKindService;
import com.wmes.appserver.web.rest.errors.BadRequestAlertException;
import com.wmes.appserver.service.dto.CustomerKindDTO;

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
 * REST controller for managing {@link com.wmes.appserver.domain.CustomerKind}.
 */
@RestController
@RequestMapping("/api")
public class CustomerKindResource {

    private final Logger log = LoggerFactory.getLogger(CustomerKindResource.class);

    private static final String ENTITY_NAME = "wmesCustomerKind";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerKindService customerKindService;

    public CustomerKindResource(CustomerKindService customerKindService) {
        this.customerKindService = customerKindService;
    }

    /**
     * {@code POST  /customer-kinds} : Create a new customerKind.
     *
     * @param customerKindDTO the customerKindDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerKindDTO, or with status {@code 400 (Bad Request)} if the customerKind has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-kinds")
    public ResponseEntity<CustomerKindDTO> createCustomerKind(@RequestBody CustomerKindDTO customerKindDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerKind : {}", customerKindDTO);
        if (customerKindDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerKind cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerKindDTO result = customerKindService.save(customerKindDTO);
        return ResponseEntity.created(new URI("/api/customer-kinds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-kinds} : Updates an existing customerKind.
     *
     * @param customerKindDTO the customerKindDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerKindDTO,
     * or with status {@code 400 (Bad Request)} if the customerKindDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerKindDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-kinds")
    public ResponseEntity<CustomerKindDTO> updateCustomerKind(@RequestBody CustomerKindDTO customerKindDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerKind : {}", customerKindDTO);
        if (customerKindDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerKindDTO result = customerKindService.save(customerKindDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerKindDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-kinds} : get all the customerKinds.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerKinds in body.
     */
    @GetMapping("/customer-kinds")
    public List<CustomerKindDTO> getAllCustomerKinds() {
        log.debug("REST request to get all CustomerKinds");
        return customerKindService.findAll();
    }

    /**
     * {@code GET  /customer-kinds/:id} : get the "id" customerKind.
     *
     * @param id the id of the customerKindDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerKindDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-kinds/{id}")
    public ResponseEntity<CustomerKindDTO> getCustomerKind(@PathVariable Long id) {
        log.debug("REST request to get CustomerKind : {}", id);
        Optional<CustomerKindDTO> customerKindDTO = customerKindService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerKindDTO);
    }

    /**
     * {@code DELETE  /customer-kinds/:id} : delete the "id" customerKind.
     *
     * @param id the id of the customerKindDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-kinds/{id}")
    public ResponseEntity<Void> deleteCustomerKind(@PathVariable Long id) {
        log.debug("REST request to delete CustomerKind : {}", id);
        customerKindService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
