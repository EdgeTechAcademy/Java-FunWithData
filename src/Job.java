import java.time.LocalDate;

public class Job {
    private int         entry;
    private String      company;
    private LocalDate   dateApplied;
    private int         week;
    private String      location;
    private int         salary;
    private String      position;
    private String      title;

    public int getEntry() {
        return entry;
    }
    public void setEntry(int entry) {
        this.entry = entry;
    }

    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDate getDateApplied() {
        return dateApplied;
    }
    public void setDateApplied(LocalDate dateApplied) {
        this.dateApplied = dateApplied;
    }

    public int getWeek() {
        return week;
    }
    public void setWeek(int week) {
        this.week = week;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public int getSalary() {
        return salary;
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Job{" +
                "entry=" + entry +
                ", company='" + company + '\'' +
                ", dateApplied=" + dateApplied +
                ", week=" + week +
                ", location='" + location + '\'' +
                ", salary=" + salary +
                ", position='" + position + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public Job(int entry, String company, LocalDate dateApplied, int week, String location, int salary, String position, String title) {
        this.entry = entry;
        this.company = company;
        this.dateApplied = dateApplied;
        this.week = week;
        this.location = location;
        this.salary = salary;
        this.position = position;
        this.title = title;
    }
}
