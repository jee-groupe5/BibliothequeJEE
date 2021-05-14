package JeeGrp5.mediatech.repositories;

import JeeGrp5.mediatech.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByFirstname(String firstname);
    User findByLogin(String login);
}
