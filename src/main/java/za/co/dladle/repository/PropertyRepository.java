package za.co.dladle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.dladle.model.Property;

/**
 * Created by prady on 9/4/2016.
 */
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>{
}
