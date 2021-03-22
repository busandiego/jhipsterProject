package com.wmes.appserver.service;

import com.wmes.appserver.domain.AdminUser;

import com.wmes.appserver.domain.AdminUser_;
import com.wmes.appserver.dto.AdminUserCriteria;
import com.wmes.appserver.repository.AdminUserRepository;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AdminUserQueryService extends QueryService<AdminUser> {

    private final Logger log = LoggerFactory.getLogger(AdminUserQueryService.class);

    private final AdminUserRepository adminUserRepository;

    public AdminUserQueryService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    /**
     * Return a {@link List} of {@link AdminUser} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdminUser> findByCriteria(AdminUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdminUser> specification = createSpecification(criteria);
        return adminUserRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link AdminUser} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdminUser> findByCriteria(AdminUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdminUser> specification = createSpecification(criteria);
        return adminUserRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdminUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdminUser> specification = createSpecification(criteria);
        return adminUserRepository.count(specification);
    }

    /**
     * Function to convert {@link AdminUserCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */


    protected Specification<AdminUser> createSpecification(AdminUserCriteria criteria) {
        Specification<AdminUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdminUser_.id));
            }
            if (criteria.getAdminLoginId() != null) {
                specification = specification.or(buildStringSpecification(criteria.getAdminLoginId(), AdminUser_.adminLoginId));
            }
            if (criteria.getAdminPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdminPassword(), AdminUser_.adminPassword));
            }
            if (criteria.getAdminName() != null) {
                specification = specification.or(buildStringSpecification(criteria.getAdminName(), AdminUser_.adminName));
            }
            if (criteria.getAdminNum() != null) {
                specification = specification.or(buildStringSpecification(criteria.getAdminNum(), AdminUser_.adminNum));
            }
            if (criteria.getAdminEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdminEmail(), AdminUser_.adminEmail));
            }
            if (criteria.getAdminCreateDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdminCreateDt(), AdminUser_.adminCreateDt));
            }
            if (criteria.getAdminUpdateDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdminUpdateDt(), AdminUser_.adminUpdateDt));
            }
            if (criteria.getAdminCurrentLoginDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdminCurrentLoginDt(), AdminUser_.adminCurrentLoginDt));
            }
            if (criteria.getPushToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPushToken(), AdminUser_.pushToken));
            }
            if (criteria.getAdminState() != null) {
                specification = specification.and(buildSpecification(criteria.getAdminState(), AdminUser_.adminState));
            }
        }
        return specification;
    }
}
