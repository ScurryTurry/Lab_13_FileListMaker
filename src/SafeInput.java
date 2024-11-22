import java.util.Scanner;

/**
 * @author Peyton Terry terrypn@mail.uc.edu
 */
public class SafeInput {
    /**
     * Get a String with at least one character in it
     * @param in Scanner used to read System.in
     * @param prompt prompt the user
     * @return String that is not zero length
     */
    public static String getNonZeroLenString(Scanner in, String prompt)
    {
        String retString = "";

        do {
            System.out.print("\n" + prompt + ": ");
            retString = in.nextLine();
        }while (retString.isEmpty());
        return retString;
    }

    /**
     * Get an int without conditions
     * @param in Scanner used to read System.in
     * @param prompt prompt the user
     * @return int value
     */
    public static int getInt (Scanner in, String prompt)
    {
        int retInt = 0;
        String trash = "";
        boolean done = false;

        do {
            System.out.print("\n" + prompt + ": ");
            if(in.hasNextInt())
            {
                retInt = in.nextInt();
                in.nextLine();
                done = true;
            }
            else{
                trash = in.nextLine();
                System.out.println("You must enter a valid number and not " + trash);
            }
        }while(!done);
        return retInt;
    }

    /** Get a double without conditions
     * @param in Scanner used to read System.in
     * @param prompt prompt the user
     * @return double value
     */
    public static double getDouble(Scanner in, String prompt)
    {
        double retDouble = 0;
        String trash = "";
        boolean done = false;

        do{
            System.out.print("\n" + prompt + ": ");
            if(in.hasNextDouble()){
                retDouble = in.nextDouble();
                in.nextLine();
                done = true;
            }
            else{
                trash = in.nextLine();
                System.out.println("You must enter a valid number and not " + trash);
            }
        }while(!done);
        return retDouble;
    }

    /**Get a ranged int
     * @param in - Scanner instance to read the data System.in
     * @param prompt - input prompt
     * @param low low end of range
     * @param high end of range
     * @return int value inside range
     */
    public static int getRangedInt(Scanner in, String prompt, int low, int high)
    {
        int retInt = 0;
        String trash = "";
        boolean done = false;

        do {
            System.out.print("\n" + prompt + "[" + low + "-" + high + "]: ");
            if(in.hasNextInt())
            {
                retInt = in.nextInt();
                in.nextLine();
                if(retInt >= low && retInt <= high)
                {
                    done = true;
                }
                else{
                    System.out.println("\nNumber is out of range [" + low + "-" + high + "]: " + retInt);
                }
            }
            else{
                trash = in.nextLine();
                System.out.println("You must enter a valid number and not " + trash);
            }
        }while(!done);
        return retInt;
    }

    /**Get a ranged double
     * @param in - Scanner instance to read the data System.in
     * @param prompt - input prompt
     * @param low low end of range
     * @param high end of range
     * @return double value inside range
     */

    public static double getRangedDouble(Scanner in, String prompt, double low, double high)
    {
        double retDouble = 0;
        String trash = "";
        boolean done = false;

        do{
            System.out.print("\n" + prompt + "[" + low + "-" + high + "]: ");
            if(in.hasNextDouble())
            {
                retDouble = in.nextDouble();
                in.nextLine();
                if(retDouble >= low && retDouble <= high)
                {
                    done = true;
                }
                else{
                    System.out.println("\nNumber is out of range [" + low + "-" + high + "]: " + retDouble);
                }
            }
            else{
                trash = in.nextLine();
                System.out.println("You must enter a valid number and not " + trash);
            }
        }while(!done);
        return retDouble;
    }

    /**
     * Get Y/N confirmation
     * @param in Scanner used
     * @param prompt prompt for Y/N
     * @return Yes or no // True or False
     */
    public static boolean getYNConfirm(Scanner in, String prompt)
    {
        boolean retConfirm = true;
        String response = "";
        boolean gotAVal = false;

        do{
            System.out.print("\n" + prompt + " [Y/N]: ");
            response = in.nextLine();
            if(response.equalsIgnoreCase("y"))
            {
                gotAVal = true;
                retConfirm = true;
            }
            else if (response.equalsIgnoreCase("n")){
                gotAVal = true;
                retConfirm = false;
            }
            else{
                System.out.println("You must answer [Y/N] and not: " + response);
            }
        }while(!gotAVal);
        return retConfirm;
    }
    /**
     * Get a string that matches a RegEx
     * @param in  Scanner instance to read the data System.in
     * @param prompt  prompt  user
     * @param regExPattern java  RegEx pattern
     * @return String that matches the RegEx
     */
    public static String getRegExString(Scanner in, String prompt, String regExPattern)
    {
        String retString = "";
        boolean gotAVal = false;

        do {
            System.out.print("\n" + prompt + ": ");
            retString = in.nextLine();
            if(retString.matches(regExPattern))
            {
                gotAVal = true;
            }
            else{
                System.out.println("\nInvalid input: " + retString + ".");
                System.out.println("Try again");
            }
        }while(!gotAVal);
        return retString;
    }
    public static void prettyHeader(String msg){
        int totalWidth = 60;
        int msgWidth = msg.length();
        int spacing = ((totalWidth - 6) - msgWidth) / 2;
        //-6 so it is 54 - msg.length = number of spaces / 2 for both sides

        for (int i = 0; i < totalWidth; i++) { //Top
            System.out.print("*");
        }
        System.out.println(); //Buffer
        System.out.print("***"); //First Three
        for (int i = 0; i < spacing; i++) { // Spacing until msg
            System.out.print(" ");
        }
        System.out.print(msg);
        for (int i = 0; i < spacing; i++) { // Spacing after msg
            System.out.print(" ");
        }
        if(msgWidth % 2 != 0)//Test for even
        {
            System.out.print(" "); //Print extra space if odd
        }
        System.out.print("***"); //Last Three
        System.out.println(); //Buffer
        for (int i = 0; i < totalWidth; i++) { //Bottom
            System.out.print("*");
        }
    }
}