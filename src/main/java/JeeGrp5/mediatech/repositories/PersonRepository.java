package JeeGrp5.mediatech.repositories;

import JeeGrp5.mediatech.entities.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {
}
