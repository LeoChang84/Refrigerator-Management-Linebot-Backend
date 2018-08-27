package service.repository;

import service.model.EasyExpired;
import service.model.Food;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EasyExpiredrepository extends MongoRepository<EasyExpired, String>, EditedRepository{

    public EasyExpired findOneByType(String type);
    public EasyExpired findOneByNameZh(String NameZh);
}