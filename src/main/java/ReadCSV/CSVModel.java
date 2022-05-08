package ReadCSV;

public class CSVModel {
    private int day;
    private int month;
    private int year;
    private int cases;
    private int deaths;
    private String countriesAndTerritories;

    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getCases() {
        return cases;
    }
    public void setCases(int cases) {
        this.cases = cases;
    }
    public int getDeaths() {
        return deaths;
    }
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
    public String getCountriesAndTerritories() {
        return countriesAndTerritories;
    }
    public void setCountriesAndTerritories(String countriesAndTerritories){
        this.countriesAndTerritories = countriesAndTerritories;}

    @Override
    public String toString(){
        return "\nDeaths "+getDeaths()
                +" Day "+getDay()
                +" Month "+getMonth()
                +" Country "+getCountriesAndTerritories();
    }
}
