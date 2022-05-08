import Menu.Menu;
import Properties.PropertiesModel;
import Properties.ReadProperties;
import Repository.DbRepository;
import Repository.Repository;
import blockchain.Block;
import blockchain.service.BlockchainService;
import blockchain.service.Service;
import com.opencsv.exceptions.CsvException;
import dbconn.factory.SQLiteConnectionFactory;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class MainUseCaseCSV {

    public static void ConfigureInjection() throws SQLException, ClassNotFoundException, IOException, ParseException, CsvException {
        PropertiesModel propertiesModel = new ReadProperties("app.properties").MapPropertiesToModel();
        Connection con = new SQLiteConnectionFactory().getConnection(Objects.requireNonNull(propertiesModel).getConnectionString());
        Repository<Block> repository = new DbRepository(con);
        Service blockchainService = new BlockchainService(repository);
        Menu menu = new Menu(propertiesModel, blockchainService);
        menu.MainMenu();
    }

    public static void main(String[] args) {
        try {
            ConfigureInjection();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Oops! Something went wrong! Error message: " + ex.getMessage());
        }
    }
}

