import java.util.ArrayList;
import java.util.List;

public class Passenger {
    private String  lastName;
    private String  firstName;
    private String  passageClass;
    private boolean passenger;
    private String  Role;
    private float   age;
    private boolean survivor;

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassageClass() {
        return passageClass;
    }

    public boolean isPassenger() {
        return passenger;
    }

    public String getRole() {
        return Role;
    }

    public float getAge() {
        return age;
    }

    public boolean isSurvivor() {
        return survivor;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", passageClass='" + passageClass + '\'' +
                ", passenger='" + passenger + '\'' +
                ", Role='" + Role + '\'' +
                ", age=" + age +
                ", survivor=" + survivor +
                "}\n";
    }

    public Passenger(String lastName, String firstName, float age, String passageClass, String paxOrCrew, String role, boolean survivor) {
        this.lastName = lastName;
        this.firstName = firstName.trim();
        this.passageClass = passageClass;
        this.passenger = paxOrCrew.equals("Passenger");
        Role = role;
        this.age = age;
        this.survivor = survivor;
    }

    /**
     * ListToPassengers
     * The parameter titanic is a ListArray of strings representing passengers on the titanic
     * This is the format of the text
     * ﻿Last Name/ First Name, Age, Class, Passenger or Crew, Role, Survivor
     * we will split the text in to the individual fields and create a Passenger object with the data
     *
     * @param titanic ListArray of strings. All the text from a file
     * @return return a list of Passengers
     */
    public static List<Passenger> listToPassengers(List<String> titanic) {
        //  the method returns a List of Passengers so we need a variable to hold that data
        List<Passenger> passengers = new ArrayList<>();

        //  the first line in the list is the header line. We want to remove it and not try convert it to a passenger
        titanic.remove(0);

        //  for each line of text in the ListArray
        for (var line : titanic) {
            //  take the text and split on comma to create an array of the individual elements for each passenger
            String[] properties = line.split(",");

            //  convert the text age into a floating point number
            if (properties[2].length() == 0) properties[2] = "0";
            float age = Utils.convertToFloat(properties[2]);

            //                           ﻿Last Name,      First Name,    Age,  Class,      Passenger or Crew,    Role,      Survivor
            Passenger pax = new Passenger(properties[0], properties[1], age, properties[3], properties[4], properties[5], properties[6].equals("true"));
            //  add the passenger to the output variable
            passengers.add(pax);
        }                   //  end of the enhanced (for each) loop

        //  return the ListArray of passengers back to the calling code
        return passengers;
    }
}
