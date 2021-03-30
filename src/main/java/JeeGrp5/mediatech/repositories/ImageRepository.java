package JeeGrp5.mediatech.repositories;

import JeeGrp5.mediatech.entities.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, String> {

}
