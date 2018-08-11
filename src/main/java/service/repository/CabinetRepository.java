package service.repository;

import service.model.Food;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CabinetRepository extends MongoRepository<Food, String> {

	public Food findByNameZh(String nameZh); 
	public List<Food> findByStatus(Integer status);
}