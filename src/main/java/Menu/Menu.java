package Menu;

import ReadCSV.CSVModel;
import Properties.PropertiesModel;
import UserParameters.SearchModel;
import ReadCSV.GetCsv;
import ReadJSON.JsonOperations;
import blockchain.Block;
import blockchain.service.Service;
import com.opencsv.exceptions.CsvException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Menu {
    private final String[] options = {"1 - Search by Country and type of case(available options - deaths, recovered, confirmed)",
            "2 - Search by Country and Timeseries",
            "3 - View Statistics",
            "4 - Exit"};
    private final PropertiesModel propertiesModel;
    private final Service blockchainService;

    //constructior injection
    public Menu(PropertiesModel propertiesModel, Service blockchainService) {
        this.propertiesModel = propertiesModel;
        this.blockchainService = blockchainService;
    }

    public void MainMenu() throws SQLException, IOException, ParseException, CsvException {
        //loop oste o termatismos na oristei apo ton xristi
        while (true) {
            Arrays.stream(options).toList().forEach(System.out::println);
            System.out.println("Please choose an option");
            //scanner gia tis epiloges tou xristi
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            //enhanced switch gia tis diafores epiloges
            switch (option) {
                case 1 -> {
                    scanner = new Scanner(System.in);
                    SearchByCountry(scanner);
                }
                case 2 -> {
                    scanner = new Scanner(System.in);
                    SearchTimeSeries(scanner);
                }
                case 3 -> {
                    List<Block> list = blockchainService.getChain();
                    scanner = new Scanner(System.in);
                    ViewStatistics(scanner, list);
                }
                case 4 -> {
                    return;
                }
            }
        }
    }

    //provoli statistikon
    private void ViewStatistics(Scanner scanner, List<Block> list) {
        System.out.println("Viewing statistics");
        System.out.println("Total number of searches made: "
                + MenuExtensions.totalNumOfSearches(list));
        System.out.println("Total number of distinct searches made: "
                + MenuExtensions.distinctNumOfSearches(list));
        System.out.println("Countries that have been searched for : "
                + MenuExtensions.searchedCountries(list));
        System.out.println("Total searches for deaths: "
                + MenuExtensions.searchedDeaths(list));
        System.out.println("Total searches for confirmed: "
                + MenuExtensions.searchedConfirmed(list));
        System.out.println("Total searches for recovered: "
                + MenuExtensions.searchedRecovered(list));
        System.out.println("Enter country as search parameter for statistics");
        String searchParams = scanner.nextLine();
        while (MenuExtensions.checkInput(searchParams)){
            System.out.println("Input contains illegal characters. Please try again!");
            searchParams = scanner.nextLine();
        }
        System.out.println("Searches made for "
                + searchParams + " : "
                + MenuExtensions.numOfSearchesByCountry(list, searchParams)
                + "\n Searches for Deaths: " + MenuExtensions.numOfSearchesForDeathsByCountry(list, searchParams)
                + "\n Searches for Confirmed: " + MenuExtensions.numOfSearchesForConfirmedByCountry(list, searchParams)
                + "\n Searches for Recovered: " + MenuExtensions.numOfSearchesForRecoveredByCountry(list, searchParams)
                + "\n Searches including Timeseries: " + MenuExtensions.numOfSearchesForTimeseriesByCountry(list, searchParams));
    }

    //anazitisi me timeseries
    private void SearchTimeSeries(Scanner scanner) throws SQLException, IOException, CsvException {
        System.out.println("Enter name of Country: ");
        String country = scanner.nextLine();
        while (MenuExtensions.checkInput(country)){
            System.out.println("Input contains illegal characters. Please try again!");
            country = scanner.nextLine();
        }
        System.out.println("Enter search parameters (available options: deaths, cases): ");
        String searchCase = scanner.nextLine();
        while (MenuExtensions.isSearchCaseValid(searchCase)) {
            System.out.println("Please enter valid search case!(Hint: deaths or cases)");
            searchCase = scanner.nextLine();
        }
        System.out.println("Enter Month: ");
        int month = scanner.nextInt();
        while (MenuExtensions.isMonthValid(month)) {
            System.out.println("Please enter a valid month!(Hint: number from 1 to 12)");
            month = scanner.nextInt();
        }
        SearchModel searchModel = new SearchModel.
                SearchModelBuilder(country, searchCase).
                month(String.valueOf(month)).
                build();
        System.out.println("Total " + searchModel.getCase() + " for " + searchModel.getLocation() +
                " for month of " + searchModel.getMonth() +
                ": " + new GetCsv(propertiesModel.getCSVPath()).ReadAll(Integer.parseInt(searchModel.getMonth()),
                        searchModel.getLocation()).
                stream().
                mapToInt(CSVModel::getCases).
                sum());
        blockchainService.insert(searchModel);
        System.out.println("Thanks for choosing option 2");
    }

    //anazitisi ana xora
    private void SearchByCountry(Scanner scanner) throws SQLException, IOException, ParseException {
        System.out.println("Enter name of Country: ");
        String country = scanner.nextLine();
        while (MenuExtensions.checkInput(country)){
            System.out.println("Input contains illegal characters. Please try again!");
            country = scanner.nextLine();
        }
        System.out.println("Enter search parameters (available options: deaths, confirmed, recovered): ");
        String searchCase = scanner.nextLine();
        while (MenuExtensions.isSearchCaseValid2(searchCase)) {
            System.out.println("Please enter valid search case!(Hint: deaths, confirmed, recovered)");
            searchCase = scanner.nextLine();
        }
        SearchModel searchModel = new SearchModel.SearchModelBuilder(country, searchCase).build();
        List<JSONObject> getCaseByCountry = new JsonOperations(propertiesModel.getJSONCountriesPath())
                .selectAllFromCountries(searchModel.getLocation());
        getCaseByCountry.forEach(x -> System.out.println("Total " + searchModel.getCase() +
                " For " + searchModel.getLocation() + " : "
                + x.get(searchModel.getCase())));
        blockchainService.insert(searchModel);
        System.out.println("Thanks for choosing option 1");
    }

}
