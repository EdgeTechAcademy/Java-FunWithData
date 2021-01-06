import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    /**
     *      ListToJobs
     *          The parameter titanic is a ListArray of strings representing passengers on the titanic
     *          This is the format of the text
     *          ﻿Last Name/ First Name, Age, Class, Passenger or Crew, Role, Survivor
     *          we will split the text in to the individual fields and create a Passenger object with the data
     *
     * @param   jobSearch     ListArray of strings. All the text from a file
     * @return              return a list of Passengers
     *
     */
    public static List<Job> listToJobs(List<String> jobSearch) {
        //  the method returns a List of Passengers so we need a variable to hold that data
        List<Job> jobs = new ArrayList<>();

        //  the first line in the list is the header line. We want to remove it and not try convert it to a passenger
        jobSearch.remove(0);

        //  for each line of text in the ListArray
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        for (var line : jobSearch) {
            //  take the text and split on comma to create an array of the individual elements for each passenger
            String[] properties = line.split(",");

            //  convert the text entryId, week and salary into an int
            int entryId = Integer.parseInt(properties[0]);
            int week = Integer.parseInt(properties[3]);
            int salary = Integer.parseInt(properties[5]);

            //  conver the date 9-Oct to a LocalDate object. Append 2020 on to the end of the string
            LocalDate date = LocalDate.parse(properties[2] + "-2020", formatter);

            //              Entry,      Company,      Date, Week,  Location,     Salary,  Position,     Title
            Job job = new Job(entryId, properties[1], date, week, properties[4], salary, properties[6], properties[7]);
            //  add the passenger to the output variable
            jobs.add(job);
        }                   //  end of the enhanced (for each) loop

        //  return the ListArray of passengers back to the calling code
        return jobs;
    }

}
