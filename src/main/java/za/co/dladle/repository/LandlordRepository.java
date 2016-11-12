package za.co.dladle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.dladle.model.Landlord;

/**
 * Created by prady on 9/3/2016.
 */
@Repository
public interface LandlordRepository extends JpaRepository<Landlord, Long>{

}
