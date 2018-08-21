package service.repository;

import service.model.ExpirationDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpirationRepository extends MongoRepository<ExpirationDoc, String> {

	public ExpirationDoc findByNameZh(String nameZh);
}