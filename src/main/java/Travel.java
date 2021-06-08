import java.util.List;
import java.util.Map;
import java.util.Set;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

@Entity
public class Travel {

    @PartitionKey
    private Integer id;
    private List<String> attractions;
    private Integer numberOfSeats;
    private Integer cost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<String> attractions) {
        this.attractions = attractions;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Travel(){}

    public Travel(Integer id, List<String> attractions, Integer numberOfSeats, Integer cost){
        this.id =id;
        this.attractions = attractions;
        this.numberOfSeats = numberOfSeats;
        this.cost = cost;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
                .append("student {")
                .append(id + ",")
                .append(attractions + ",")
                .append(numberOfSeats + ",")
                .append(cost + ",")
                .append("}");
        return builder.toString();
    }
}
