package com.wmes.appserver.repository;
import com.wmes.appserver.domain.Business;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Business entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

    Long findTopById(Long id);

    Optional<Business> findTop1ByBusinessName(String businessName);
    @Override
    Optional<Business> findById(Long id);

}
