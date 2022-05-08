package dbconn.factory;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    Connection getConnection(String conString) throws ClassNotFoundException, SQLException;
}
