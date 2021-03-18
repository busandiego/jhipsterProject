package com.wmes.appserver.service;

import com.wmes.appserver.domain.AdminUser;
import com.wmes.appserver.domain.StateAdmin;
import com.wmes.appserver.dto.AdminUserDto;
import com.wmes.appserver.repository.AdminUserRepository;
import com.wmes.appserver.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class AdminUserService {

    private static final String ENTITY_NAME = "adminUser";

    private final Logger log = LoggerFactory.getLogger(AdminUserService.class);

    private final AdminUserRepository adminUserRepository;

    private final PasswordEncoder passwordEncoder;


    public AdminUserService(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public Optional<AdminUser> findOneAdminUser(String name) {
        return adminUserRepository.findByAdminLoginId(name);
    }


    /**
     * Save a adminUser.
     *
     * @param adminUser the entity to save.
     * @return the persisted entity.
     */
    public AdminUser save(AdminUser adminUser) {
        log.debug("Request to save AdminUser : {}", adminUser);
        if(adminUserRepository.countByAdminLoginId(adminUser.getAdminLoginId()) != 0){
            throw new BadRequestAlertException("A new adminUser cannot already have an loginID", ENTITY_NAME, "idexists");
        }
        adminUser.setAdminCreateDt(ZonedDateTime.now());
        adminUser.setAdminPassword(passwordEncoder.encode(adminUser.getAdminPassword()));
        adminUser.setAdminState(StateAdmin.NORMAL);
        return adminUserRepository.save(adminUser);
    }

    public AdminUser update(AdminUser adminUser) {
        log.debug("Request to save AdminUser : {}", adminUser);
        Optional<AdminUser> findAdmin = adminUserRepository.findById(adminUser.getId());
        if (findAdmin.isPresent()) {
            adminUser.setAdminUpdateDt(ZonedDateTime.now());
            adminUser.setAdminCreateDt(findAdmin.get().getAdminCreateDt());
            adminUser.setAdminPassword(findAdmin.get().getAdminPassword());
            // adminUser.setPushToken(findAdmin.get().getPushToken());
        }
        return adminUserRepository.save(adminUser);
    }

    public void updateLoginDt(String userName){
        Optional<AdminUser> findAdmin = adminUserRepository.findByAdminLoginId(userName);
        if(findAdmin.isPresent()){
            findAdmin.get().setAdminCurrentLoginDt(ZonedDateTime.now());
            adminUserRepository.save(findAdmin.get());
        }
    }

    /**
     * Get all the adminUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminUserDto> findAll(Pageable pageable) {
        log.debug("Request to get all AdminUsers");
        return adminUserRepository.findAll(pageable).map(AdminUserDto::new);
    }


    /**
     * Get all the adminUsers where Affiliate is {@code null}.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdminUser> findAllWhereAffiliateIsNull() {
        log.debug("Request to get all adminUsers where Affiliate is null");
        return StreamSupport
            .stream(adminUserRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * Get all the adminUsers where Accommodation is {@code null}.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdminUser> findAllWhereAccommodationIsNull() {
        log.debug("Request to get all adminUsers where Accommodation is null");
        return StreamSupport
            .stream(adminUserRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * Get all the adminUsers where FaqBoard is {@code null}.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdminUser> findAllWhereFaqBoardIsNull() {
        log.debug("Request to get all adminUsers where FaqBoard is null");
        return StreamSupport
            .stream(adminUserRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Get one adminUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdminUser> findOne(Long id) {
        log.debug("Request to get AdminUser : {}", id);
        return adminUserRepository.findOneWithRolesAndAuthsById(id);
    }

    /**
     * Delete the adminUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdminUser : {}", id);
        adminUserRepository.deleteById(id);
    }


    public List<AdminUser> findByAuth(String authName){
        return adminUserRepository.findByAuthName(authName);
    }
}
