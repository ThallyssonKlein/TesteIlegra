package br.com.thallyssonklein.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TopByRegion{
    private Integer Region;
    private String[] top3MostAccess;
}