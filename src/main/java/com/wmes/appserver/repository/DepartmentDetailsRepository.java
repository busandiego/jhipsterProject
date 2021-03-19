package com.wmes.appserver.repository;
import com.wmes.appserver.domain.DepartmentDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DepartmentDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentDetailsRepository extends JpaRepository<DepartmentDetails, Long> {

}
