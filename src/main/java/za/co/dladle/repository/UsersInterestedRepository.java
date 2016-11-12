package za.co.dladle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.dladle.model.UsersInterested;

@Repository
public interface UsersInterestedRepository extends JpaRepository<UsersInterested,Long>{

}
