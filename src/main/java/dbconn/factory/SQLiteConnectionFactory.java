package dbconn.factory;

import java.sql.*;

public class SQLiteConnectionFactory implements ConnectionFactory {
    private Connection con;

    @Override
    public Connection getConnection(String conString) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection(conString);
        createTable();
        return con;
    }

    private void createTable() throws SQLException {
        String create = "CREATE TABLE IF NOT EXISTS Blockchain (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "data TEXT NOT NULL," +
                "hash TEXT," +
                "prevHash TEXT," +
                "blockTimestamp TEXT," +
                "nonce TEXT" +
                ");";
        PreparedStatement stmt = con.prepareStatement(create);
        stmt.execute();
        stmt.close();
    }
}
