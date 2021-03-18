package com.wmes.appserver.repository;

import com.wmes.appserver.domain.AdminUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long>, JpaSpecificationExecutor<AdminUser> {

    Optional<AdminUser> findByAdminLoginId(String adminLoginId);

    Integer countByAdminLoginId(String adminLoginId);

    String ADMIN_USERS_BY_LOGIN_CACHE = "adminLoginId";

    @EntityGraph(attributePaths = "roles")
    Optional<AdminUser> findOneWithRolesByAdminLoginId(String login);

    @EntityGraph(attributePaths = {"roles", "auths"})
    Optional<AdminUser> findOneWithRolesAndAuthsById(Long id);

    @EntityGraph(attributePaths = "auths")
    Optional<AdminUser> findOneWithAuthById(Long id);

    @EntityGraph(attributePaths = {"roles", "auths"})
    Page<AdminUser> findAll(Specification spec, Pageable pageable);

    @Query("SELECT a FROM AdminUser a " +
        "JOIN a.auths r ON r.auth_name = :authName")
    List<AdminUser> findByAuthName(String authName);

}
