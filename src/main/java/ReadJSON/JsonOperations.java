package ReadJSON;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JsonOperations {
    private final List<JSONObject> list = new ArrayList<>();
    private final String path;

    public JsonOperations(String path) {
        this.path = path;
    }

    public List<JSONObject> selectAllFromCountries(String country) throws IOException, ParseException {
        URL url = new URL(path + country);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        //Getting the response code
        int response = conn.getResponseCode();
        if (response != 200) {
            throw new RuntimeException("HttpResponseCode: " + response);
        } else {
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            //Write ton JSON data se string xrisimopoiontas scanner
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            //Close to scanner
            scanner.close();
            //Me tin vivliothiki JSON simple parse to string se json object
            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(inline.toString());
            //Pairnoume to object kai to vazoume se lista oste na to epistrepsoume
            JSONObject obj = (JSONObject) data_obj.get("data");
            list.add(obj);
            return list;
        }
    }


    public List<JSONObject> selectAllFromTimeseries(String searchCase) throws IOException, ParseException {
            URL url = new URL(path + searchCase);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int response = conn.getResponseCode();
            System.out.println(response);
            if (response != 200) {
                throw new RuntimeException("HttpResponseCode: " + response);
            } else {
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                scanner.close();
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(inline.toString());
                return (List<JSONObject>) data_obj.get(searchCase);

            }
    }

}
