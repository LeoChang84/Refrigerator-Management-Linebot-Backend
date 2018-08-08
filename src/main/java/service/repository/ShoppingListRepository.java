package service.repository;

import service.model.Food;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShoppingListRepository extends MongoRepository<Food, String> {

	public Food findByNameZh(String nameZh); 
}