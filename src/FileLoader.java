import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
    public static void main(String[] args) {

        //  read the list of ship's passengers into a ListArray of text
        List<String> titanic    = FileToList("C:/projects/csv/titanic.csv");

        //  read the list of jobs
        List<String> arOfJobs   = FileToList("C:/projects/csv/JobSearch.csv");

        //  take the List of passenger String data and convert into a List of Passengers
        List<Job> jobs = listToJobs(arOfJobs);

        //  take the List of passenger String data and convert into a List of Passengers
        List<Passenger> passengers = listToPassengers(titanic);

        //  TODO #1 how many jobs did I apply for in May
        //  TODO #2 make this into a method: 1 parameter: month number and returns a number of jobs applied for
        //  TODO #3 how many jobs did I apply for on the 3rd of the month
        //  TODO #4 change the method:
        //      now it has two parameters:
        //          a number (could be the day of the month or the number of the month)
        //          a string ("day" or "month")
        //      add a switch statement to your method to switch on your string.
        //          if it is "day" you will count the jobs applied for on that day of the month, like you did in #3
        //          if it is "month" you will count the jobs applied for in that month, like you did in #1
        //  TODO #5 how many jobs did I apply for on a Thursday. change your method
        //      Change the switch satement to switch on "weekday"

        //  TODO #6 add a request in main to ask the user for
        //      the kind of search to do ("day", "month", "weekday"
        //      the number of a month or day of the week or day of the month
        //      use those two pieces of data to call your method to get the count of jobs applied for

        //  TODO #7 What day of the week did I apply for the most jobs
        //      create an array of 7 ints
        //      get the count of jobs applied for each day
        //      find the largest number in the array
        //      this is your biggest day of the week

        for (var j : jobs) {
            System.out.println(j.getDateApplied());
        }

//        passengers.forEach(p -> System.out.println(p.getFirstName()));
        for (var p : passengers) {
            //  find baby passengers (age 0 just means we do not have their age from the ship's records
            if (p.getAge() < 1 && p.getAge() != 0) {
                System.out.println(p.getFirstName() + " " + p.getLastName() + " was " + p.getAge()*12 + " months old.");
            }
        }   //  end of the for loop

    }       //  end of main

    /**
     *      ListToPassengers
     *          The parameter titanic is a ListArray of strings representing passengers on the titanic
     *          This is the format of the text
     *          ﻿Last Name/ First Name, Age, Class, Passenger or Crew, Role, Survivor
     *          we will split the text in to the individual fields and create a Passenger object with the data
     *
     * @param   titanic     ListArray of strings. All the text from a file
     * @return              return a list of Passengers
     *
     */
    private static List<Passenger> listToPassengers(List<String> titanic) {
        //  the method returns a List of Passengers so we need a variable to hold that data
        List<Passenger> passengers = new ArrayList<>();

        //  the first line in the list is the header line. We want to remove it and not try convert it to a passenger
        titanic.remove(0);

        //  for each line of text in the ListArray
        for (var line : titanic) {
            //  take the text and split on comma to create an array of the individual elements for each passenger
            String[] properties = line.split(",");

            //  convert the text age into a floating point number
            if (properties[1].length()== 0) properties[1] = "0";
            float age = Float.parseFloat(properties[1]);

            //  the name field is the first property in the array the names are like this
            //  Last Name/ First name
            //  if we split on "/" it will give us the first and last names of the passenger without any space padding
            String[] names = properties[0].split("/");

            //                           ﻿Last Name/ First Name,Age,  Class,      Passenger or Crew,    Role,      Survivor
            Passenger pax = new Passenger(names[0], names[1], age, properties[2], properties[3], properties[4], properties[5].equals("T"));
            //  add the passenger to the output variable
            passengers.add(pax);
        }                   //  end of the enhanced (for each) loop

        //  return the ListArray of passengers back to the calling code
        return passengers;
    }

    /**
     *      ListToPassengers
     *          The parameter titanic is a ListArray of strings representing passengers on the titanic
     *          This is the format of the text
     *          ﻿Last Name/ First Name, Age, Class, Passenger or Crew, Role, Survivor
     *          we will split the text in to the individual fields and create a Passenger object with the data
     *
     * @param   jobSearch     ListArray of strings. All the text from a file
     * @return              return a list of Passengers
     *
     */
    private static List<Job> listToJobs(List<String> jobSearch) {
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
            int week    = Integer.parseInt(properties[3]);
            int salary  = Integer.parseInt(properties[5]);

            //  conver the date 9-Oct to a LocalDate object. Append 2020 on to the end of the string
            LocalDate date = LocalDate.parse(properties[2]+"-2020", formatter);

            //              Entry,      Company,      Date, Week,  Location,     Salary,  Position,     Title
            Job job = new Job(entryId, properties[1], date, week, properties[4], salary, properties[6], properties[7] );
            //  add the passenger to the output variable
            jobs.add(job);
        }                   //  end of the enhanced (for each) loop

        //  return the ListArray of passengers back to the calling code
        return jobs;
    }

    /**
     *      FileToList
     *          We read the entire file into memory. Each line is saved to a String which is added to a ListArray
     *
     *  @param  from    file to be read
     * @return          return a list of lines of the file read
     *
     */
    private static List<String> FileToList(String from) {
        //  list is the variable that will hold the file we are reading
        List<String> list = new ArrayList<>();
        String line;

        //  convert the string of the file name in to a File object
        File fin = new File(from);
        try {
            //  Construct BufferedReader from FileReader
            //  The BufferedReader is designed to read line based text from a file
            BufferedReader br = new BufferedReader(new FileReader(fin));

            //  while there is data to be read
            while ((line = br.readLine()) != null) {
                //  add each line to the list
                list.add(line);
            }
            //  close the file
            br.close();
        } catch (IOException e) {
            System.out.println("File IO error on read: " + e);
        }

        //  return the list of text lines
        return list;
    }           //  end of FileToList

}               //  end of the class
