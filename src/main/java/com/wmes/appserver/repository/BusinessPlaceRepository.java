package com.wmes.appserver.repository;
import com.wmes.appserver.domain.BusinessPlace;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BusinessPlace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessPlaceRepository extends JpaRepository<BusinessPlace, Long> {

}
