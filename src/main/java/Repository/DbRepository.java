package Repository;

import blockchain.Block;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbRepository implements Repository<Block> {
    private final Connection conn;

    public DbRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Block block) throws SQLException {
        String insert = "INSERT INTO Blockchain(data,hash,prevHash,blockTimestamp,nonce)" +
                "VALUES(?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(insert);
        stmt.setString(1, block.getData());
        stmt.setString(2, block.getHash());
        stmt.setString(3, block.getPreviousHash());
        stmt.setString(4, String.valueOf(block.getTimeStamp()));
        stmt.setString(5, String.valueOf(block.getNumberOnce()));
        stmt.executeUpdate();
    }

    @Override
    public List<Block> selectAll() throws SQLException {
        List<Block> list = new ArrayList<>();
        String select = "SELECT * FROM Blockchain ORDER BY Id ASC";
        PreparedStatement stmt = conn.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Block block = new Block(rs.getString("hash"), rs.getString("data"),
                    rs.getString("prevHash"), rs.getString("blockTimestamp"),
                    Integer.parseInt(rs.getString("nonce")));
            list.add(block);
        }
        stmt.close();
        return list;
    }
}
