package service.repository;

import service.model.CategoryTable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryTableRepository extends MongoRepository<CategoryTable, String> {

	public CategoryTable findOneByNameZh(String nameZh);
}