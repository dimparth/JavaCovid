package Properties;

public class PropertiesModel {
    private final String ConnectionString;
    private final String CSVPath;
    private final String JSONCountriesPath;
    private final String JSONTimelinePath;

    public String getConnectionString() {
        return ConnectionString;
    }

    public String getCSVPath() {
        return CSVPath;
    }

    public String getJSONCountriesPath() {
        return JSONCountriesPath;
    }

    public String getJSONTimelinePath() {
        return JSONTimelinePath;
    }

    private PropertiesModel(PropertiesBuilder builder) {
        this.ConnectionString = builder.connString;
        this.CSVPath = builder.csvPath;
        this.JSONCountriesPath = builder.jsonCountriesPath;
        this.JSONTimelinePath = builder.jsonTimelinePath;
    }

    public static class PropertiesBuilder {
        private final String connString;
        private String csvPath;
        private String jsonCountriesPath;
        private String jsonTimelinePath;

        public PropertiesBuilder(String connString) {
            this.connString = connString;
        }

        public PropertiesBuilder csvPath(String csvPath) {
            this.csvPath = csvPath;
            return this;
        }

        public PropertiesBuilder jsonCountriesPath(String jsonCountriesPath) {
            this.jsonCountriesPath = jsonCountriesPath;
            return this;
        }

        public PropertiesBuilder jsonTimelinePath(String jsonTimelinePath) {
            this.jsonTimelinePath = jsonTimelinePath;
            return this;
        }

        public PropertiesModel build() {
            return new PropertiesModel(this);
        }
    }

}
