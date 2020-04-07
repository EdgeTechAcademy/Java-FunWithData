import java.util.Scanner;

public class Utils {

    static Scanner sc = new Scanner(System.in);

    /**
     * 		getInput	-	Prompt the user to type something in the console window
     * @param prompt		String - The message telling the user what to enter
     * @return				String - The users response
     */
    public static String getInput(String prompt) {
        String response;					//	will hold the response from the user
        System.out.print(prompt);			//	hey user - give me some information
        response = sc.nextLine();			//	I will wait here until you type something
        return response;					//	much thanks I will return this to the calling method
    }

    /**
     * 	getDouble		-	Prompt the user to respond with a number
     * @param prompt		String - The message telling the user what to enter
     * @return				double - The users response converted to a number
     */
    public static double getDouble(String prompt) {
        double number = 0;							//	will hold the numeric response from the user
        String response;						//	the String user response that needs to be converted to a number
        do {									//	infinite do-while until the user enters a number
            response = getInput(prompt);		//	ask user for a response
            try {								//	protect the code from dieing if we don't get a number from the user
                number = Double.parseDouble(response);	//	convert to a number
                break;							//	Yay! The user gave us a valid number
            } catch (NumberFormatException e) {			//	bad news. We did not get a number
                System.out.println( response + " Is not a number");		//	warn the user and continue prompting
            }
        } while(true);							//	stay in the loop until we get a number from the user
        return number;							//	yes this could go after the parseDouble, I like all methods to end at the bottom
    }

    /**
     * 	getNumber		-	Prompt the user to respond with a number
     * @param prompt		String - The message telling the user what to enter
     * @return				int - The users response converted to a number
     */
    public static int getNumber(String prompt) {
        int number = 0;							//	will hold the numeric response from the user
        String response;						//	the String user response that needs to be converted to a number
        do {									//	infinite do-while until the user enters a number
            response = getInput(prompt);		//	ask user for a response
            try {								//	protect the code from dieing if we don't get a number from the user
                number = Integer.parseInt(response);	//	convert to a number
                break;							//	Yay! The user gave us a valid number
            } catch (NumberFormatException e) {			//	bad news. We did not get a number
                System.out.println( response + " Is not a number");		//	warn the user and continue prompting
            }
        } while(true);							//	stay in the loop until we get a number from the user
        return number;							//	yes this could go after the parseInt, I like all methods to end at the bottom
    }

    /**
     *
     * 	getNumber		-	Prompt the user to respond with a number less than or equal to max
     * @param prompt		String - The message telling the user what to enter
     * @param max			int - max number the user can enter
     * @return				int - The users response converted to a number
     */
    public static int getNumber(String prompt, int max) {
        int number = 0;							//	will hold the numeric response from the user
        do {									//	infinite do-while until the user enters a number
            number = getNumber(prompt);			//	ask user for a response
            if (number >= 0 && number <= max)	//	if number is between 0 and max we have a good number
                break;							//	now we can exit out loop
            System.out.println(number + " is not between 0 and " + max);
        } while (true);	//	stay at it until the user enters a proper number
        return number;							//	yay, we have our number
    }

    public static void main(String[] args) {
        int max, number;
        do {
            max = getNumber("Enter Maximum number: ");						//	ask user for a maximum number
            number = getNumber("Enter a number <= to " + max + ": ", max);	//	test code to see if we can only enter numbers < max
            //	yay, here is the users response
            System.out.println("User entered: " + number + " which is less than or equal to " + max);
        }	while (number != 0);
    }
}
