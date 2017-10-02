package za.co.dladle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.dladle.jpamodel.UserDladle;

/**
 * Created by prady on 10/2/2017.
 */
public interface UserRepository extends JpaRepository<UserDladle, Long> {

}
