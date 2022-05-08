package blockchain.service;

import UserParameters.SearchModel;
import Repository.Repository;
import blockchain.Block;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class BlockchainService implements Service {
    private List<Block> chain;
    private final Repository<Block> blockRepository;

    //injection
    public BlockchainService(Repository<Block> repo) {
        this.blockRepository = repo;
    }

    //elegxos an to chain einai adeio
    public boolean checkIfEmpty() throws SQLException {
        chain = blockRepository.selectAll();
        return chain.size() == 0;
    }

    //initialization tou chain me "Initial" dedomena kai tyxaio UUID ws prevHash, prosthiki kai stin vasi
    public void initialize() throws SQLException {
        if (checkIfEmpty()) {
            SearchModel mockSrc = new SearchModel.SearchModelBuilder("Initial Block", "Initial Block").build();
            Block genesisBlock = new Block(mockSrc.ToJSON(), UUID.randomUUID().toString());
            genesisBlock.mineBlock(6);
            blockRepository.insert(genesisBlock);
            chain.add(genesisBlock);
        }
    }

    //pairno to blockchain os lista apo blocks
    public List<Block> getChain() throws SQLException {
        initialize();
        return chain;
    }

    //prosthiki neou block sto chain
    public void insert(SearchModel searchModel) throws SQLException {
        chain = getChain();
        Block block = new Block(searchModel.ToJSON(), chain.get(chain.size() - 1).getHash());
        block.mineBlock(6);
        blockRepository.insert(block);
        chain.add(block);
    }
}
