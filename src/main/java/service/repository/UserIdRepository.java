package service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import service.model.UserId;

public interface UserIdRepository extends MongoRepository<UserId, String> {

}
