package service.repository;

import service.model.Food;
import service.model.Foods;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ShoppingListRepository extends MongoRepository<Foods, String> {

	public Food findByNameZh(String nameZh); 
	public List<Foods> findByStatus(Integer status);
}