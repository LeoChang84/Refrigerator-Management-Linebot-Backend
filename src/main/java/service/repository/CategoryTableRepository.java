package service.repository;

import service.model.CategoryTable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryTableRepository extends MongoRepository<CategoryTable, String> {

	public CategoryTable findOneByNameZh(String nameZh);
}