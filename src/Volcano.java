public class Volcano {
    private int         year;
    private String      name;
    private String      location;
    private String      country;
    private float       latitude;
    private float       longitude;
    private int         elevation;
    private String      type;
    private int         VEI;
    private String      agent;
    private int         deaths;

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public float getLatitude() {
        return latitude;
    }
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    public float getLongitude() {
        return longitude;
    }
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    public int getElevation() {
        return elevation;
    }
    public void setElevation(int elevation) {
        this.elevation = elevation;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getVEI() {
        return VEI;
    }
    public void setVEI(int VEI) {
        this.VEI = VEI;
    }
    public String getAgent() {
        return agent;
    }
    public void setAgent(String agent) {
        this.agent = agent;
    }
    public int getDeaths() {
        return deaths;
    }
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    @Override
    public String toString() {
        return "Volcano{" +
                "year=" + year +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", elevation=" + elevation +
                ", type='" + type + '\'' +
                ", VEI=" + VEI +
                ", agent='" + agent + '\'' +
                ", deaths=" + deaths +
                '}';
    }

    public Volcano(int year, String name, String location, String country, float latitude, float longitude, int elevation, String type, int VEI, String agent, int deaths) {
        this.year = year;
        this.name = name;
        this.location = location;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.type = type;
        this.VEI = VEI;
        this.agent = agent;
        this.deaths = deaths;
    }
}
