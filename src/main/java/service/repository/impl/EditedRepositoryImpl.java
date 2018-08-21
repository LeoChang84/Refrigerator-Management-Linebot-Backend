package service.repository.impl;

import service.repository.EditedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class EditedRepositoryImpl implements EditedRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    // @Override
    public int updateNameZhAndTypeAndExpirationDateWithId(String id ,String nameZh, String type, String expirationDate) {

        // Query query = new Query(Criteria.where("_id").is(id));
        // Update update = new Update();
        // update.set("display", flag);

        // WriteResult result = mongoTemplate.updateFirst(query, update, Domain.class);

        // if(result!=null)
        //     return result.getN();
        // else
        //     return 0;
        return 0;
    }
}