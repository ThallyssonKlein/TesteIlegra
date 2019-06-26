package persistence.repository.Impl;

import com.mongodb.client.MongoCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import persistence.entity.Log;
import persistence.repository.LogRepositoryCustom;

import java.util.ArrayList;
import java.util.List;

public class LogRepositoryImpl implements LogRepositoryCustom {

    private final MongoOperations mongoOperations;

    @Autowired
    protected LogRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Log findLessAccessedLog() throws ArrayIndexOutOfBoundsException{
        return mongoOperations.find(
                new Query().with(new Sort(Sort.Direction.ASC, "frequence")), Log.class).get(0);
    }

    @Override
    public List<Log> findOrderByFrequence(){
        return mongoOperations.find(new Query().with(new Sort(Sort.Direction.DESC, "frequence")), Log.class);
    }

    @Override
    public List<Integer> findAllRegions(){
        List<Integer> toReturn = new ArrayList<>();
        for (MongoCursor<Integer> it = mongoOperations.getCollection("logs").distinct("region", Integer.class).iterator(); it.hasNext(); ) {
            Integer i = it.next();
            toReturn.add(i);
        }
        return toReturn;
    }
}
