package Menu;

import blockchain.Block;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuExtensions {
    //o arithmos ton anazitiseon vasei tis xoras pou eisigage o xristis
    //os parametro
    public static int numOfSearchesByCountry(List<Block> list, String searchParams) {
        return list.stream().filter(x -> {
            JSONObject obj = null;
            try {
                obj = (JSONObject) new JSONParser().parse(x.getData());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Objects.requireNonNull(obj).get("Location").equals(searchParams);
        }).toList().size();
    }

    //o arithmos ton anazitiseon gia thanatous
    //gia tin xora pou eisigage o xristis os parametro
    public static int numOfSearchesForDeathsByCountry(List<Block> list, String searchParams) {
        return list.stream().filter(x -> {
            JSONObject obj = null;
            try {
                obj = (JSONObject) new JSONParser().parse(x.getData());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Objects.requireNonNull(obj).get("Location").equals(searchParams) &&
                    Objects.requireNonNull(obj).get("SearchCase").equals("deaths");
        }).toList().size();
    }

    //o arithmos ton anazitiseon gia krousmata
    //gia tin xora pou eisigage o xristis os parametro
    public static int numOfSearchesForConfirmedByCountry(List<Block> list, String searchParams) {
        return list.stream().filter(x -> {
            JSONObject obj = null;
            try {
                obj = (JSONObject) new JSONParser().parse(x.getData());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Objects.requireNonNull(obj).get("Location").equals(searchParams) &&
                    (Objects.requireNonNull(obj).get("SearchCase").equals("confirmed")
                            || Objects.requireNonNull(obj).get("SearchCase").equals("cases"));
        }).toList().size();
    }

    //o arithmos ton anazitiseon gia anarroseis
    //gia tin xora pou eisigage o xristis os parametro
    public static int numOfSearchesForRecoveredByCountry(List<Block> list, String searchParams) {
        return list.stream().filter(x -> {
            JSONObject obj = null;
            try {
                obj = (JSONObject) new JSONParser().parse(x.getData());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Objects.requireNonNull(obj).get("Location").equals(searchParams) &&
                    Objects.requireNonNull(obj).get("SearchCase").equals("recovered");
        }).toList().size();
    }

    //o arithmos ton anazitiseon me timeseries
    //gia tin xora pou eisigage o xristis os parametro
    public static int numOfSearchesForTimeseriesByCountry(List<Block> list, String searchParams) {
        return list.stream().filter(x -> {
            JSONObject obj = null;
            try {
                obj = (JSONObject) new JSONParser().parse(x.getData());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Objects.requireNonNull(obj).get("Location").equals(searchParams) &&
                    Objects.requireNonNull(obj).get("Month") != null;
        }).toList().size();
    }

    //o sinolikos arithmos ksexoriston anazitiseon
    //ginetai skip(1) giati i proti eggrafi den einai
    //pragmatiki anazitisi, alla to initialization block
    public static long distinctNumOfSearches(List<Block> list) {
        return list.stream().map(Block::getData).toList().stream().skip(1).distinct().count();
    }

    //o sinolikos arithmos anazitiseon
    //ginetai skip(1) giati i proti eggrafi den einai
    //pragmatiki anazitisi, alla to initialization block
    public static int totalNumOfSearches(List<Block> list) {
        return list.stream().skip(1).toList().size();
    }

    //ektiposi ton xoron gia tis opoies
    //exei pragmatopoiithei anazitisi
    public static List<Object> searchedCountries(List<Block> list) {
        return list.stream().map(x -> {
            JSONObject obj = null;
            try {
                obj = (JSONObject) new JSONParser().parse(x.getData());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Objects.requireNonNull(obj).get("Location");
        }).toList().stream().distinct().skip(1).toList();
    }

    //synolikos arithmos anazitiseon gia thanatous
    public static int searchedDeaths(List<Block> list) {
        return list.stream().filter(x -> {
            JSONObject obj = null;
            try {
                obj = (JSONObject) new JSONParser().parse(x.getData());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Objects.requireNonNull(obj).get("SearchCase").equals("deaths");
        }).toList().size();
    }

    //sinolikos arithmos anazitiseon gia krousmata
    public static int searchedConfirmed(List<Block> list) {
        return list.stream().filter(x -> {
            JSONObject obj = null;
            try {
                obj = (JSONObject) new JSONParser().parse(x.getData());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return (Objects.requireNonNull(obj).get("SearchCase").equals("confirmed")
                    || Objects.requireNonNull(obj).get("SearchCase").equals("cases"));
        }).toList().size();
    }

    //sinolikos arithmos anazitiseon gia anarroseis
    public static int searchedRecovered(List<Block> list) {
        return list.stream().filter(x -> {
            JSONObject obj = null;
            try {
                obj = (JSONObject) new JSONParser().parse(x.getData());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Objects.requireNonNull(obj).get("SearchCase").equals("recovered");
        }).toList().size();
    }

    //elegxos egkirotitas mina
    public static boolean isMonthValid(int month) {
        return month == 0 || month > 12;
    }

    //elegxos egkirotitas case
    public static boolean isSearchCaseValid(String searchCase) {
        return !searchCase.equals("deaths")
                && !searchCase.equals("cases");
    }

    //diaforetikos elegxos egkirotitas case
    public static boolean isSearchCaseValid2(String searchCase) {
        return !searchCase.equals("deaths")
                && !searchCase.equals("confirmed")
                && !searchCase.equals("recovered");
    }

    //elegxos user input me regex
    //gia mi apodektous xaraktires
    public static boolean checkInput(String searchParams){
        Pattern pattern = Pattern.compile("['~#@*+%{}<>\\[\\]|\"\\_^]");
        Matcher matcher = pattern.matcher(searchParams);;
        return matcher.find();
    }
}
