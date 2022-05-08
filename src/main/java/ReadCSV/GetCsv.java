package ReadCSV;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class GetCsv {
    private final String url;

    public GetCsv(String url) {
        this.url = url;
    }

    public List<CSVModel> ReadAll(int month, String country) throws IOException, CsvException {
        CSVReader reader =
                new CSVReaderBuilder(new FileReader(url)).
                        withSkipLines(1). // Skip tin proti grammi giati einai to header
                        build();
        //meso tou stream API arxika kanoume map to object mas sto CSV arxeio, pairnoume mono ta dedomena pou
        //xreiazomaste.
        return reader.readAll().stream().map(data -> {
            CSVModel csvObject = new CSVModel();
            csvObject.setDay(Integer.parseInt(data[1]));
            csvObject.setMonth(Integer.parseInt(data[2]));
            csvObject.setYear(Integer.parseInt(data[3]));
            csvObject.setCases(Integer.parseInt(data[4]));
            csvObject.setDeaths(Integer.parseInt(data[5]));
            csvObject.setCountriesAndTerritories(data[6]);
            return csvObject;
        }).toList().stream().filter(data -> data.getMonth() == month  //metatrepoume to apotelesma tou map se List
                && data.getCountriesAndTerritories().equals(country)). // kai kanoume .filter() me vasi
                toList();                                              //ta dedomena pou edwse ws input o xristis
    }
}
