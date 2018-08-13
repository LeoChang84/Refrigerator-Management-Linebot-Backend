package service.repository;

import service.model.Food;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CabinetRepository extends MongoRepository<Food, String>, EditedRepository{

	public Food findOneById(String id);
	public List<Food> findByNameZh(String nameZh); 
	public List<Food> findByAcquisitionDateAndStatus(String acquisitionDate, Integer status);
	public List<Food> findByStatus(Integer status);
}