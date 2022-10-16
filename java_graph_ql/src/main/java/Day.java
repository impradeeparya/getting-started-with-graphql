import lombok.Builder;
import lombok.ToString;

/**
 * Created by p0a00hg on 16/10/22
 **/

@ToString
@Builder
public class Day {

    private String id;
    private String date;
    private Float temperature;
    private String season;
}
