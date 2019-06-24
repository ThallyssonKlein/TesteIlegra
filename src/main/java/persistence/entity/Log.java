package persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
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
