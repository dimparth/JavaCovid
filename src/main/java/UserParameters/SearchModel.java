package UserParameters;

import com.google.gson.GsonBuilder;

public class SearchModel {
    private final String Location;
    private final String SearchCase;
    private final String Month;
    private final String Year;

    public String getLocation() {
        return Location;
    }

    public String getCase() {
        return SearchCase;
    }

    public String getMonth() {
        return Month;
    }

    public String getYear() {
        return Year;
    }

    private SearchModel(SearchModelBuilder builder) {
        this.Location = builder.location;
        this.SearchCase = builder.searchCase;
        this.Month = builder.month;
        this.Year = builder.year;
    }

    public static class SearchModelBuilder {
        private final String location;
        private final String searchCase;
        private String month;
        private String year;

        public SearchModelBuilder(String location, String searchCase) {
            this.location = location;
            this.searchCase = searchCase;
        }

        public SearchModelBuilder month(String month) {
            this.month = month;
            return this;
        }

        public SearchModelBuilder year(String year) {
            this.year = year;
            return this;
        }

        public SearchModel build() {
            return new SearchModel(this);
        }
    }

    //ToJSON oste na paroume JSON morfi tou antikeimenou gia na perastei sto block
    public String ToJSON() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
