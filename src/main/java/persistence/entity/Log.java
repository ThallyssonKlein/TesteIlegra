package persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Document(collection = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @Temporal(TemporalType.TIMESTAMP)
    private Date whenAccessed;

    private String userUid;

    private Integer region;

    private Integer frequence;

    public void addFrequence(){
        ++frequence;
    }
}
