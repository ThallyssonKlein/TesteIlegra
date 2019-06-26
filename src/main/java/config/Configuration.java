package config;

import business.service.LogService;
import com.mongodb.MongoClient;
import controller.LogController;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import persistence.entity.Log;
import persistence.repository.LogRepository;

@org.springframework.context.annotation.Configuration
@EnableWebMvc
@EnableMongoRepositories(basePackageClasses = LogRepository.class)
@EntityScan(basePackageClasses = Log.class)
@ComponentScan(basePackageClasses = {LogController.class, LogService.class})
public class Configuration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "teste_ilegra";
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return new MongoClient("127.0.0.1", 27017);
    }
}
