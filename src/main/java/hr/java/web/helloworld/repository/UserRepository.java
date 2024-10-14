package hr.java.web.helloworld.repository;

import hr.java.web.helloworld.domain.ApplicationUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<ApplicationUser,Long> {
    ApplicationUser findByUsername(final String username);
}
