import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;

@Dao
public interface TravelDao {
    @Select
    Travel findById(Integer id);

    @Insert
    void save(Travel travel);

    @Update
    void update(Travel travel);

    @Delete
    void delete(Travel travel);
}
