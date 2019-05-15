package br.com.thallyssonklein.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "LOGS")
@Table(name="LOGS")
@ToString
@EqualsAndHashCode
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    @Temporal(TemporalType.TIMESTAMP)
    private Date times;
    private String userUid;
    private Integer region;

    public Log(String url, Date times, String userUid, Integer region){
        this.url = url;
        this.times = times;
        this.userUid = userUid;
        this.region = region;
    }
}