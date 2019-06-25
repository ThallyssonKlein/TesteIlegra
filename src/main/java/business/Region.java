package business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Region{
    private Integer regionCode;
    private String[] top3MostAccessedUrls;
}