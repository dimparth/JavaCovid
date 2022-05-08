package blockchain.service;

import UserParameters.SearchModel;
import blockchain.Block;

import java.sql.SQLException;
import java.util.List;

public interface Service {
    boolean checkIfEmpty() throws SQLException;

    void initialize() throws SQLException;

    List<Block> getChain() throws SQLException;

    void insert(SearchModel searchModel) throws SQLException;
}
