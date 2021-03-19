package com.wmes.appserver.repository;
import com.wmes.appserver.domain.CustomerManager;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustomerManager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerManagerRepository extends JpaRepository<CustomerManager, Long> {

}
