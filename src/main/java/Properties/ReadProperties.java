package Properties;

import Properties.PropertiesModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public record ReadProperties(String filename) {
    public PropertiesModel MapPropertiesToModel() throws IOException {
        Properties prop = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + filename + "' not found in the classpath");
        }
        return new PropertiesModel.PropertiesBuilder(prop.getProperty("database"))
                .csvPath(prop.getProperty("csv-path"))
                .jsonCountriesPath(prop.getProperty("json-countries-path"))
                .jsonTimelinePath(prop.getProperty("json-timeline-path"))
                .build();
    }
}