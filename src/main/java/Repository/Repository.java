package Repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    void insert(T t) throws SQLException;
    List<T> selectAll() throws SQLException;
}
