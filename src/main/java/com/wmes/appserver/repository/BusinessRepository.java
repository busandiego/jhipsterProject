package com.wmes.appserver.repository;
import com.wmes.appserver.domain.Business;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Business entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

}
