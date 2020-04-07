public class Passenger {
    private String  lastName;
    private String  firstName;
    private String  passengerClass;
    private String  paxOrCrew;
    private String  Role;
    private float   age;
    private boolean survivor;

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassengerClass() {
        return passengerClass;
    }
    public void setPassengerClass(String passengerClass) {
        this.passengerClass = passengerClass;
    }

    public String getPaxOrCrew() {
        return paxOrCrew;
    }
    public void setPaxOrCrew(String paxOrCrew) {
        this.paxOrCrew = paxOrCrew;
    }

    public String getRole() {
        return Role;
    }
    public void setRole(String role) {
        Role = role;
    }

    public float getAge() {
        return age;
    }
    public void setAge(float age) {
        this.age = age;
    }

    public boolean isSurvivor() {
        return survivor;
    }
    public void setSurvivor(boolean survivor) {
        this.survivor = survivor;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", passengerClass='" + passengerClass + '\'' +
                ", paxOrCrew='" + paxOrCrew + '\'' +
                ", Role='" + Role + '\'' +
                ", age=" + age +
                ", survivor=" + survivor +
                '}';
    }

    public Passenger(String lastName, String firstName, float age, String passengerClass, String paxOrCrew, String role, boolean survivor) {
        this.lastName = lastName;
        this.firstName = firstName.trim();
        this.passengerClass = passengerClass;
        this.paxOrCrew = paxOrCrew;
        Role = role;
        this.age = age;
        this.survivor = survivor;
    }
}
