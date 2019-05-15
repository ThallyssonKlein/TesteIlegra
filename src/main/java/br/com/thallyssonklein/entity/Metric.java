package br.com.thallyssonklein.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Metric {

    private String[] top3MostAccess;
    private List<TopByRegion> topByRegions;
    private String lessAccessedOfTheWorld;
    private String[] top3ByTime;
    private int minuteWithMoreAcess;
}