package com.wmes.appserver.web.rest;

import com.wmes.appserver.domain.AdminUser;
import com.wmes.appserver.dto.AdminUserCriteria;
import com.wmes.appserver.service.AdminUserQueryService;
import com.wmes.appserver.service.AdminUserService;
import com.wmes.appserver.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AdminUserResource {

    private final Logger log = LoggerFactory.getLogger(AdminUserResource.class);

    private static final String ENTITY_NAME = "adminUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminUserService adminUserService;

    private final AdminUserQueryService adminUserQueryService;

    public AdminUserResource(AdminUserService adminUserService, AdminUserQueryService adminUserQueryService) {
        this.adminUserService = adminUserService;
        this.adminUserQueryService = adminUserQueryService;
    }

    /**
     * {@code POST  /admin-users} : Create a new adminUser.
     *
     * @param adminUser the adminUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminUser, or with status {@code 400 (Bad Request)} if the adminUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-users")
    public ResponseEntity<AdminUser> createAdminUser(@Valid @RequestBody AdminUser adminUser) throws URISyntaxException {
        log.debug("REST request to save AdminUser : {}", adminUser);
        if (adminUser.getId() != null) {
            throw new BadRequestAlertException("A new adminUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminUser result = adminUserService.save(adminUser);
        return ResponseEntity.created(new URI("/api/admin-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-users} : Updates an existing adminUser.
     *
     * @param adminUser the adminUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminUser,
     * or with status {@code 400 (Bad Request)} if the adminUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-users")
    public ResponseEntity<AdminUser> updateAdminUser(@Valid @RequestBody AdminUser adminUser) throws URISyntaxException {
        log.debug("REST request to update AdminUser : {}", adminUser);
        if (adminUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdminUser result = adminUserService.update(adminUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /admin-users} : get all the adminUsers.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminUsers in body.
     */
    @GetMapping("/admin-users")
    public ResponseEntity<Page<AdminUser>> getAllAdminUsers(AdminUserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdminUsers by criteria: {}", criteria);
        Page<AdminUser> page = adminUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/admin-users/auth")
    public ResponseEntity<List<AdminUser>> getAuthAdminUsers(@RequestHeader(name = "authName", required = true) String authName) {
        List<AdminUser> page = adminUserService.findByAuth(authName);
        for(AdminUser adminUser : page){
            adminUser.setAdminImage(null);
        }
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /admin-users/count} : count all the adminUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/admin-users/count")
    public ResponseEntity<Long> countAdminUsers(AdminUserCriteria criteria) {
        log.debug("REST request to count AdminUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(adminUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /admin-users/:id} : get the "id" adminUser.
     *
     * @param id the id of the adminUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-users/{id}")
    public ResponseEntity<AdminUser> getAdminUser(@PathVariable Long id) {
        log.debug("REST request to get AdminUser : {}", id);
        Optional<AdminUser> adminUser = adminUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminUser);
    }

    /**
     * {@code DELETE  /admin-users/:id} : delete the "id" adminUser.
     *
     * @param id the id of the adminUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-users/{id}")
    public ResponseEntity<Void> deleteAdminUser(@PathVariable Long id) {
        log.debug("REST request to delete AdminUser : {}", id);
        adminUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
