import java.util.DoubleSummaryStatistics;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataExercises {
    public static void main(String[] args) {

        //  read the list of ship"s passengers into a ListArray of text
        List<String> titanic = FileToList("C:/projects/csv/titanic.csv");

        //  take the List of passenger String data and convert into a List of Passengers
        List<Passenger> passengers = Passenger.listToPassengers(titanic);

        //  How many people were on the Titanic?
        System.out.format("Titanic passenger/crew count: %d%n", passengers.size());

    //  we are going to use several High Order Functions
    //      FILTER - return EVERY attribute from a JSON array but only SOME of the rows
    //      MAP    - return a row for EVERY item in the JSON array but only SOME of the attributes
    //      REDUCE - return a ONE piece of information for the JSON array (total of a col, longest/shortest string)

    //------------------------------------------------------------------------------------------------
    //
    //  let"s start off with filter
    //
    //------------------------------------------------------------------------------------------------
        //      a list for survivors
        var survivors = passengers.stream().filter(Passenger::isSurvivor);
        //  count is a TERMINAL operation which means we can no longer use this stream
        System.out.format("Titanic Survivors count: %s%n", survivors.count());

        var crewMembers = passengers.stream().filter(p -> ! p.isPassenger());
        //  count is a TERMINAL operation which means we can no longer use this stream
        System.out.format("Titanic Crew count: %d%n", crewMembers.count());

//  let's get a list of surviving crew members in three different ways (NOT in Java we wont)
//      ATTENTION we can not reuse the survivor or crewMembers list again.
//          The count() method is a terminal method that closes the stream
//          Java does not permit the reuse of a stream once it has been clased
//          JavaScript does not have this proscription
        var survivingCrew = passengers.stream().filter(p -> p.isSurvivor() && ! p.isPassenger());
        System.out.format("Number of the crew that survived: %d%n", survivingCrew.count());

//  one more filter request - get the number of children (under 18), that survived from 1st class
        var youngSurvivors = passengers.stream().filter(p -> p.getAge() > 0 && p.getAge() < 18).filter(p -> p.getPassageClass().equals("1st Class") && p.isSurvivor());
        youngSurvivors.forEach(System.out::print);

//------------------------------------------------------------------------------------------------
//
//  Moving on the MAP
//      map will extract a one attribute of your object or multiple if you like
//
//------------------------------------------------------------------------------------------------
//  get all first names. Notice the length of the array matches the length of the passengers array
        var allFirstNames  = passengers.stream().map(Passenger::getFirstName);
        System.out.format("We have a list of %d first names%n", allFirstNames.count());

//  get all titles
//          using Set and map. map returns ALL of the titles, Set condenses down to the unique titles
//          Set is a Java object that only holds UNIQUE values. All dups are tossed away
//      step 1 - get the firstName. The first name attribute looks like this              Mrs Rhoda Mary 'Rosa'
        var firstNames = passengers.stream().map(Passenger::getFirstName);
//      step 2 - SPLIT the first name on space. This creates an array of the names       ['Mrs', 'Rhoda', 'Mary', '\'Rosa\'']
//      step 3 - get the first token of the array [0]                                     'Mrs'
//      step 4 - create a new list of titles for just the unique values
        var allTitles = firstNames.map(name -> name.split(" ")[0]).distinct().collect(Collectors.toSet());     //  this still has 2477 entries in it

//      start over and do it all in one step
        allTitles = passengers.stream().map(p -> p.getFirstName().split(" ")[0]).collect(Collectors.toSet());
        System.out.format("What are the titles of the people on the Titanic? %s%n", String.join(",",allTitles));


//------------------------------------------------------------------------------------------------
//
//  REDUCE
//      Reduce is great for getting the total of an array or the longest string or largest value
//
//------------------------------------------------------------------------------------------------
//  get total ages of all passengers and crew members
//      method 1 - get just the ages using the map function then sum them to get the total
        DoubleSummaryStatistics stats = passengers.stream().map(Passenger::getAge).collect(Collectors.summarizingDouble(a -> a));
        System.out.format("Total of all ages %f and the Average %f%n", stats.getSum(), stats.getAverage());
//      method 2 - go directly to the summing. rather than extract the ages just look at the passenger.age value and use it
        stats = passengers.stream().collect(Collectors.summarizingDouble(Passenger::getAge));
        System.out.format("Total of all ages %f and the Average %f%n", stats.getSum(), stats.getAverage());

//
//  what is the average age of the survivors?
        stats = passengers.stream().filter(Passenger::isSurvivor).collect(Collectors.summarizingDouble(Passenger::getAge));
        System.out.format("Average age of survivors %f%n", stats.getAverage());

// 3rd class passengers over 60 that survived
        var thirdClass = passengers.stream().filter(p -> p.isSurvivor() && (p.getAge() > 60) && p.getPassageClass().equals("3rd Class")).count();
        System.out.format("3rd class passengers over 60 that survived: %d%n", thirdClass);

//  find the captain
//      hint firstName starts with Capt (and they were an officer)
//      Surprise there were two Captains on board. One was a passenger
        var captains = passengers.stream().filter(p -> p.getFirstName().startsWith("Capt"));  //   && ! p.passenger
        captains.forEach(System.out::print);

// find the Musicians
        var musicians = passengers.stream().filter(p -> p.getRole().equals("Musician"));
        musicians.forEach(System.out::print);

//  get list of married women
        var ladies = passengers.stream().filter(p -> p.getFirstName().startsWith("Mrs"));
        System.out.format("Number of Mrs's on board %d%n", ladies.count());

//-------------------------------------------------------------------------------------
//
//      Let's look at some of other array functions available to us
//          anyMatch    returns true or false if at least one record matches the conditional expression
//          allMatch    returns TRUE if EVERY record matches the conditional expression
//          findFirst   returns the first record
//          sort        sort the records in the array using the test (compare two records return <0 if record A is smaller >0 if A is bigger, 0 is the same)
//-------------------------------------------------------------------------------------
        var oldSurvivors      = passengers.stream().anyMatch(p -> p.isSurvivor() && p.getAge() > 80);                     //  did some octogenerians survice
        var allMusiciansDead  = passengers.stream().allMatch(p -> p.getRole().equals("Musician") && ! p.isSurvivor());        //  did every musician die?
        var first30Plus       = passengers.stream().filter(p -> p.getAge() > 30 && p.isSurvivor()).findFirst();                     //  find the first 30 something survivor

//  Who was the youngest passenger? This is a little tricky.
//      We have some missing age data so if we do not have the age of the passenger it has been set to 0;
//      which we would not want to use as a valid lowest age. So we add a compare to 0 and skip that record
        Optional<Passenger> youngest = passengers.stream().reduce((prev, next) -> (next.getAge() == 0 || prev.getAge() < next.getAge()) ? prev : next);
        Optional<Passenger> oldest   = passengers.stream().reduce((prev, next) ->                        prev.getAge() > next.getAge()  ? prev : next);
        System.out.format("The youngest passenger on the Titanic was %s %s age: %f%n", youngest.get().getFirstName(), youngest.get().getLastName(), youngest.get().getAge());
        System.out.format("The oldest   passenger on the Titanic was %s %s age: %f%n", oldest.get().getFirstName(), oldest.get().getLastName(), oldest.get().getAge());

//  alphabetize names by last name
        musicians = passengers.stream().filter(p -> p.getRole().equals("Musician"));
        Stream<Passenger> sorted = musicians.sorted(Comparator.comparing(Passenger::getLastName).reversed());
        sorted.forEach(System.out::print);

//  order names by length
        passengers.stream().sorted((e1, e2) -> e1.getLastName().length() - e2.getLastName().length());
        passengers.stream().sorted(Comparator.comparingInt(e -> e.getLastName().length()));
        System.out.format("%s has the shortest last name%n", passengers.get(0).getLastName());
        System.out.format("%s has the  longest last name%n", passengers.get(passengers.size()-1).getLastName());


        double  total, average, min, max;
        long count;
        List<String> list;
        List<Passenger> pax;
        Set<String> roles;
        Map<Float, List<Passenger>> personByAge;
        Map<String, List<Passenger>> personByClass;

        average = passengers.stream().filter(Passenger::isSurvivor).mapToDouble(Passenger::getAge).average().getAsDouble();
        average = passengers.stream().filter(Passenger::isSurvivor).mapToDouble(Passenger::getAge).average().getAsDouble();

        total = passengers.stream().mapToDouble(Passenger::getAge).sum();
        total = (double) passengers.stream().map(Passenger::getAge).reduce(0.0f, (wipTotal, paxAge) -> wipTotal + paxAge);

        min = passengers.stream().filter(Passenger::isSurvivor).mapToDouble(Passenger::getAge).min().getAsDouble();
        max = passengers.stream().filter(Passenger::isSurvivor).mapToDouble(Passenger::getAge).max().getAsDouble();
        count = passengers.stream().filter(Passenger::isSurvivor).count();

        list = passengers.stream().filter(Passenger::isSurvivor).map(p -> p.getLastName() + " " + p.getFirstName()).collect(Collectors.toList());
        pax = passengers.stream().filter(p -> p.getRole().equals("Musician")).collect(Collectors.toList());
        roles = passengers.stream().map(Passenger::getRole).collect(Collectors.toSet());
        roles = passengers.stream().map(Passenger::getRole).collect(Collectors.toCollection(TreeSet::new));

        // Let"s see how we can group objects in Java 8
        personByClass = passengers.stream().collect(Collectors.groupingBy(Passenger::getPassageClass));
        personByClass.keySet().forEach(k -> System.out.println(k + " " + personByClass.get(k).size()));

        // Now let"s group person by age
        personByAge = passengers.stream().collect(Collectors.groupingBy(Passenger::getAge));
        personByAge.keySet().forEach(k -> System.out.println(k + " " + personByAge.get(k).size()));


        //  now just for some simple array functions with a simple array of numbers
        int[] ages = {14, 17, 11, 32, 33, 16, 40, 15, 4, 18, 912, 543, 33};

        IntStream stream = Arrays.stream(ages);
        Arrays.stream(ages).forEach(n -> System.out.println(n));

        System.out.format ("reduce -    " + stream.reduce(0, (tot, num) -> tot - num)); //  subtract the numbers from 0
        System.out.format ("reduce +    " + stream.reduce(0, (tot, num) -> tot + num)); //  add numbers together starting at 0
        System.out.format ("every       " + stream.allMatch(age -> age >= 18));                //  is every number >= to 18
        System.out.format ("includes    " + stream.includes(age -> age >= 18));             //  does the array include any numbers >= 18?
        System.out.format ("find        " + stream.find(age -> age >= 18));                 //  find the first 18 in the array
        System.out.format ("includes    " + stream.includes(16));                           //  look for 16 starting at the front [0] of the array
        System.out.format ("includes    " + stream.includes(16, 6));                        //  look for 16 starting at position 6
        System.out.format ("findIndex   " + stream.findIndex(age -> age >= 18));            //  find the first number >= 18
        System.out.format ("some        " + stream.anyMatch(age -> age >= 18));                 //  are some of the numbers >= 18
        System.out.format ("indexOf     " + stream.indexOf(33));                            //  where is the first 33
        System.out.format ("lastIndexOf " + stream.lastIndexOf(33));                        //  where is the last 33
        System.out.format ("filter      " + stream.filter(age -> age >= 100));              //  get array of numbers > 100

    }       //  end of main

    /**
     * ListToVolcanos
     * The parameter titanic is a ListArray of strings representing passengers on the titanic
     * This is the format of the text
     * ﻿Last Name/ First Name, Age, Class, Passenger or Crew, Role, Survivor
     * we will split the text in to the individual fields and create a Passenger object with the data
     *
     * @param volcanoList ListArray of strings. All the text from a file
     * @return return a list of Volcanos
     */
    private static List<Volcano> listToVolcanos(List<String> volcanoList) {
        //  the method returns a List of Passengers so we need a variable to hold that data
        List<Volcano> volcanos = new ArrayList<>();

        //  the first line in the list is the header line. We want to remove it and not try convert it to a passenger
        volcanoList.remove(0);

        //  for each line of text in the ListArray
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        for (var line : volcanoList) {
            //  take the text and split on comma to create an array of the individual elements for each passenger
            String[] properties = line.split(",", 11);

            //  convert the text to ints and floats as needed
            int year        = convertToInt(properties[0]);
            float latitude  = convertToFloat(properties[4]);
            float longitude = convertToFloat(properties[5]);
            int elevation   = convertToInt(properties[6]);
            int VEI         = convertToInt(properties[8]);
            int deaths      = convertToInt(properties[10]);

            //              Entry,      Company,      Date, Week,  Location,     Salary,  Position,     Title
            Volcano volcano = new Volcano(year, properties[1], properties[2], properties[3], latitude, longitude, elevation, properties[7], VEI, properties[9], deaths);
            //  add the passenger to the output variable
            volcanos.add(volcano);
        }                   //  end of the enhanced (for each) loop

        //  return the ListArray of passengers back to the calling code
        return volcanos;
    }

    public static int convertToInt(String num) {
        int number;
        try {								//	protect the code from dieing if we don"t get a number from the user
            number = Integer.parseInt(num);	//	convert to a number
        } catch (NumberFormatException e) {			//	bad news. We did not get a number
            number = 0;		//	warn the user and continue prompting
        }
        return number;
    }

    public static float convertToFloat(String num) {
        float number;
        try {								//	protect the code from dieing if we don"t get a number from the user
            number = Float.parseFloat(num);	//	convert to a number
        } catch (NumberFormatException e) {			//	bad news. We did not get a number
            number = 0;		//	warn the user and continue prompting
        }
        return number;
    }

    /**
     * FileToList
     * We read the entire file into memory. Each line is saved to a String which is added to a ListArray
     *
     * @param from file to be read
     * @return return a list of lines of the file read
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