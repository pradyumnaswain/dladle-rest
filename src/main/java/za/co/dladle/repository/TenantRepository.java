package za.co.dladle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.dladle.model.Tenant;

/**
 * Created by prady on 9/3/2016.
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long>{
}
