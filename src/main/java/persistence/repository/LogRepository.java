package persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import persistence.entity.Log;

import java.util.List;

@Repository
public interface LogRepository extends MongoRepository<Log, Long> {

    public List<Log> findAllOrderAsc();

    //TODO-Onde parei? Parei quando descobrir que posso salvar a quantidade de frequência nos próprios logs e que preciso migrar o projeto pro H2 Database
}
