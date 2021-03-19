package com.wmes.appserver.repository;
import com.wmes.appserver.domain.CustomerKind;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustomerKind entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerKindRepository extends JpaRepository<CustomerKind, Long> {

}
