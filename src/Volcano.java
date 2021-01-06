import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    public String getName() {
        return name;
    }
    public String getLocation() {
        return location;
    }
    public String getCountry() {
        return country;
    }
    public float getLatitude() {
        return latitude;
    }
    public float getLongitude() {
        return longitude;
    }
    public int getElevation() {
        return elevation;
    }
    public String getType() {
        return type;
    }
    public int getVEI() {
        return VEI;
    }
    public String getAgent() {
        return agent;
    }
    public int getDeaths() {
        return deaths;
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

    /**
     * ListToVolcanoes
     * The parameter titanic is a ListArray of strings representing passengers on the titanic
     * This is the format of the text
     * ﻿Last Name/ First Name, Age, Class, Passenger or Crew, Role, Survivor
     * we will split the text in to the individual fields and create a Passenger object with the data
     *
     * @param volcanoList ListArray of strings. All the text from a file
     * @return return a list of Volcanos
     */
    private static List<Volcano> listToVolcanoes(List<String> volcanoList) {
        //  the method returns a List of Passengers so we need a variable to hold that data
        List<Volcano> volcanoes = new ArrayList<>();

        //  the first line in the list is the header line. We want to remove it and not try convert it to a passenger
        volcanoList.remove(0);

        //  for each line of text in the ListArray
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        for (var line : volcanoList) {
            //  take the text and split on comma to create an array of the individual elements for each passenger
            String[] properties = line.split(",", 11);

            //  convert the text to ints and floats as needed
            int year        = Utils.convertToInt(properties[0]);
            float latitude  = Utils.convertToFloat(properties[4]);
            float longitude = Utils.convertToFloat(properties[5]);
            int elevation   = Utils.convertToInt(properties[6]);
            int VEI         = Utils.convertToInt(properties[8]);
            int deaths      = Utils.convertToInt(properties[10]);

            //              Entry,      Company,      Date, Week,  Location,     Salary,  Position,     Title
            Volcano volcano = new Volcano(year, properties[1], properties[2], properties[3], latitude, longitude, elevation, properties[7], VEI, properties[9], deaths);
            //  add the passenger to the output variable
            volcanoes.add(volcano);
        }                   //  end of the enhanced (for each) loop

        //  return the ListArray of passengers back to the calling code
        return volcanoes;
    }
}
