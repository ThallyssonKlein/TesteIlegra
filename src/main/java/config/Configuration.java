package config;

import com.mongodb.*;
import controller.LogController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import persistence.repository.LogRepository;

import java.util.ArrayList;
import java.util.List;

@org.springframework.context.annotation.Configuration
@EnableWebMvc
@EnableMongoRepositories(basePackageClasses = LogRepository.class)
@ComponentScan(basePackageClasses = LogController.class)
public class Configuration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "teste_ilegra";
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient("127.0.0.1", 27017);
    }
}
