import java.util.DoubleSummaryStatistics;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;

public class DataExercises {
    public static void main(String[] args) {

        //  read the list of ship"s passengers into a ListArray of text
        List<String> titanic = Utils.FileToList("C:/projects/csv/titanic.csv");

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
//          The count() method is a 'terminal' method that closes the stream
//          Java does not permit the reuse of a stream once it has been clased
//          JavaScript does not have this proscription
        var survivingCrew = passengers.stream().filter(p -> p.isSurvivor() && ! p.isPassenger());
        System.out.format("Number of the crew that survived: %d%n", survivingCrew.count());

//  one more filter request - get the number of children (under 18), that survived from 1st class
        var youngSurvivors = passengers.stream().filter(p -> p.getAge() > 0  && p.getAge() < 18 &&
                                                             p.getPassageClass().equals("1st Class") && p.isSurvivor());
        youngSurvivors.forEach(System.out::print);
//        System.out.println(youngSurvivors.count());       //  not permitted because this stream is already closed

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
        var allTitles = firstNames.map(name -> name.split(" ")[0]);                      //  this still has 2477 entries in it
//      step 4 - create a new list of titles for just the unique values
        var titles = allTitles.distinct();                  //  this is a stream NOT a Set
        System.out.format("What are the titles of the people on the Titanic? %s%n", titles.collect(Collectors.joining(",")));

//      start over and do it all in one step
        var titles2 = passengers.stream().map(p -> p.getFirstName().split(" ")[0]).collect(Collectors.toSet());      //  this is an honest Set
        System.out.format("What are the titles of the people on the Titanic? %s%n", String.join(",",titles2));

//------------------------------------------------------------------------------------------------
//
//  REDUCE
//      Reduce is great for getting the total of an array or the longest string or largest value
//
//------------------------------------------------------------------------------------------------
//  get total ages of all passengers and crew members
//      method 1 - mapToDouble will produce a stream of age values and sum will do what it does
        double totalAges = passengers.stream().mapToDouble(Passenger::getAge).sum();        //  there is also .average, .min, and .max
        System.out.format("Total of all ages %f and the Average %f%n", totalAges, totalAges/passengers.size());
//      method 2 - get just the ages using the map function then use a stream Collector to get all of the stats
        DoubleSummaryStatistics stats = passengers.stream().map(Passenger::getAge).collect(Collectors.summarizingDouble(a -> a));
        System.out.format("Total of all ages %f and the Average %f%n", stats.getSum(), stats.getAverage());
//      method 3 - go directly to the summing. rather than extract the ages just look at the passenger.age value and use it
        stats = passengers.stream().collect(Collectors.summarizingDouble(Passenger::getAge));
        System.out.format("Total of all ages %f and the Average %f%n", stats.getSum(), stats.getAverage());

//
//  what is the average age of the survivors?
        stats = passengers.stream().filter(Passenger::isSurvivor).collect(Collectors.summarizingDouble(Passenger::getAge));
        System.out.format("Average age of survivors %f%n", stats.getAverage());
//  average returns an OptionalDouble which may or may not have a value (what if the list is empty?)
        var averageAge = passengers.stream().mapToDouble(Passenger::getAge).average();
        System.out.format("Average age of survivors %f%n", averageAge.getAsDouble());

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

//  order names by length and find the shortest name
        //  version 1 - create a sorted stream based on length of last name
        var orderedNames = passengers.stream().sorted((e1, e2) -> e1.getLastName().length() - e2.getLastName().length());
        //  version 2 - create a sorted stream based on length of name using the Comparator class
        orderedNames = passengers.stream().sorted(Comparator.comparingInt(e -> e.getLastName().length()));
        //      version 1/2 - get the first element of the stream. It will have the shortest name
        var shortestName = orderedNames.findFirst();

//  order names by length and find the longest name
        //  version 1 - order the list by length of name
        orderedNames = passengers.stream().sorted(Comparator.comparingInt(e -> e.getLastName().length()));
        //      skip all but the last record, get the next record
        var longestName = orderedNames.skip(passengers.size()-1).findFirst();
        //  version 2 - order names by length and reverse the order
        orderedNames = passengers.stream().sorted(Comparator.comparingInt(e -> ((Passenger)e).getLastName().length()).reversed());
        //      no need to skip, the first name is the longest name
        longestName = orderedNames.findFirst();

        //  longestName and shortestName are Optional variables. So we use get to get the value
        //      if we were good programmes we would first check to see if there was a result
        //      with something like this:                   if (shortestName.isPresent() ) .....
        System.out.format("%s has the shortest last name%n", shortestName.get().getLastName());
        System.out.format("%s has the  longest last name%n", longestName.get().getLastName());

        List<Passenger> pax;
        Set<String> roles;
        Map<Float, List<Passenger>> personByAge;
        Map<String, List<Passenger>> personByClass;

        //  get a List of Musicians
        pax = passengers.stream().filter(p -> p.getRole().equals("Musician")).collect(Collectors.toList());

        //  get a unique Set of roles
        roles = passengers.stream().map(Passenger::getRole).collect(Collectors.toSet());

        // Let"s see how we can group objects in Java 8
        personByClass = passengers.stream().collect(Collectors.groupingBy(Passenger::getPassageClass));
        personByClass.keySet().forEach(k -> System.out.println(k + " " + personByClass.get(k).size()));

        personByAge = passengers.stream().collect(Collectors.groupingBy(Passenger::getAge));
        personByAge.keySet().forEach(k -> System.out.println(k + " " + personByAge.get(k).size()));

        //  now just for some simple array functions with a simple array of numbers
        int[]     ages  =           {14, 17, 11, 32, 33, 16, 40, 15, 4, 18, 912, 543, 33};

        System.out.format ("reduce -    %d%n", Arrays.stream(ages).reduce(0, (tot, num) -> tot - num)); //  subtract the numbers from 0
        System.out.format ("reduce +    %d%n", Arrays.stream(ages).reduce(0, (tot, num) -> tot + num)); //  add numbers together starting at 0
        System.out.format ("every       %b%n", Arrays.stream(ages).allMatch(age -> age >= 18));         //  is every number >= to 18
        System.out.format ("includes    %b%n", Arrays.stream(ages).anyMatch(age -> age >= 18));         //  does the array include any numbers >= 18?
        System.out.format ("none        %b%n", Arrays.stream(ages).noneMatch(age -> age >= 18));        //  are none of the numbers >= 18
        System.out.format ("find        %d%n", Arrays.stream(ages).filter(age -> age >= 18).findFirst()); //  find the first 18 in the array
        System.out.format ("includes    %d%n", Arrays.asList(ages).contains(16));                       //  look for 16 starting at the front [0] of the array
        System.out.format ("some        %d%n", Arrays.stream(ages).anyMatch(age -> age >= 18));         //  are some of the numbers >= 18
        System.out.format ("indexOf     %d%n", Arrays.asList(ages).indexOf(33));                        //  where is the first 33
        System.out.format ("lastIndexOf %d%n", Arrays.asList(ages).lastIndexOf(33));                    //  where is the last 33
        System.out.format ("filter      %d%n", Arrays.stream(ages).filter(age -> age >= 100).count());  //  get array of numbers > 100
    }       //  end of main
}               //  end of the class
