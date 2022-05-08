package JSONUseCase;

import Properties.PropertiesModel;
import UserParameters.SearchModel;
import ReadJSON.JsonOperations;
import org.apache.commons.lang3.time.StopWatch;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Properties.ReadProperties;

public class MainUseCaseJSON {
    public static void main(String[] args) {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            PropertiesModel propertiesModel = new ReadProperties("app.properties").MapPropertiesToModel();
            SearchModel searchModel = new SearchModel.SearchModelBuilder("Greece", "deaths")
                    .month("1").year("22").build();
            String date = "1/" + searchModel.getMonth() + "/" + searchModel.getYear();
            LocalDate lastDayOfMonth = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/M/yy"))
                    .with(TemporalAdjusters.lastDayOfMonth());
            int l = Integer.parseInt(String.valueOf(lastDayOfMonth.getDayOfMonth()));
            String asd = searchModel.getMonth() + "/" + l + "/" + searchModel.getYear();
            List<JSONObject> getByTimeseries = new JsonOperations(propertiesModel.getJSONTimelinePath()).selectAllFromTimeseries(searchModel.getCase());
            getByTimeseries.forEach(data -> {
                if (data.get("Country/Region").equals(searchModel.getLocation())) {
                    System.out.println(data.get(asd));
                }
            });
            stopWatch.stop();
            System.out.println("Time elapsed: "+stopWatch.getTime(TimeUnit.SECONDS));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

