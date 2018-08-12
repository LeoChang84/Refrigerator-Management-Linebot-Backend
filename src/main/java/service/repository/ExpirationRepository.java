package service.repository;

import service.model.ExpirationDoc;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExpirationRepository extends MongoRepository<ExpirationDoc, String> {

	public ExpirationDoc findByNameZh(String nameZh);
}