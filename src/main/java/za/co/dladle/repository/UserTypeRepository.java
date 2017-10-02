package za.co.dladle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.dladle.jpamodel.UserTypeDladle;

/**
 * Created by prady on 10/2/2017.
 */
public interface UserTypeRepository extends JpaRepository<UserTypeDladle, Long> {
}
