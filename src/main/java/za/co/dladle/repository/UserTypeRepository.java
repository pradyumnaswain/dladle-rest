package za.co.dladle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import za.co.dladle.model.UserType;

/**
 * Created by prady on 7/26/2016.
 */
@RepositoryRestResource(collectionResourceRel = "UserType", path = "usertype")
public interface UserTypeRepository extends JpaRepository<UserType, Long>{
}
