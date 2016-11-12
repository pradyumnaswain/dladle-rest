package za.co.dladle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.dladle.model.Vendor;

/**
 * Created by prady on 9/4/2016.
 */
public interface VendorRepository extends JpaRepository<Vendor, Long>{
}
