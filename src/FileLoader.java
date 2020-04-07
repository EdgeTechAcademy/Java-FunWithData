import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
    public static void main(String[] args) {

        //  read the list of ship's passengers into a ListArray of text
        List<String> titanic = FileToList("C:/projects/csv/titanic.csv");

        //  take the List of passenger String data and convert into a List of Passengers
        List<Passenger> passengers = ListToPassengers(titanic);

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
     */
    private static List<Passenger> ListToPassengers(List<String> titanic) {
        //  the method returns a List of Passengers so we need a variable to hold that data
        List<Passenger> passengers = new ArrayList<Passenger>();

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
            Passenger pax = new Passenger(names[0], names[1], age, properties[2], properties[3], properties[4], properties[5].equals("1"));
            //  add the passenger to the output variable
            passengers.add(pax);
        }                   //  end of the enhanced (for each) loop

        //  return the ListArray of passengers back to the calling code
        return passengers;
    }

    /**
     *      FileToList
     *          We read the entire file into memory. Each line is saved to a String which is added to a ListArray
     *
     *  @param  from    file to be read
     * @return          return a list of lines of the file read
     *
     */
    public static List<String> FileToList(String from) {
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
        } catch (IOException e) {   }

        //  return the list of text lines
        return list;
    }           //  end of FileToList

}               //  end of the class
