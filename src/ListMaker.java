import java.io.*;
import java.nio.*;
import static java.nio.file.StandardOpenOption.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * @author Peyton Terry terrypn@mail.uc.edu
 */

public class ListMaker {
    private static final ArrayList<String> list = new ArrayList<>();
    private static final Scanner in = new Scanner(System.in); //No need to repeat Scanner in

    /**
     * Switch is the easiest way to filter through all the choices. A lot like Rock Paper Scissors Lab.
     * menuChoice.toUpperCase couldn't use .equalsIgnoreCase so this made it easier for the switch
     */
    public static void main(String[] args) {
        boolean done = false;

        do {
            menuDisplay();
            String menuChoice = SafeInput.getRegExString(in,"What would you like to do?", "[AaDdIiPpQq]");
            switch (menuChoice.toUpperCase()) {
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "I":
                    insertItem();
                    break;
                case "P":
                    printItem();
                    break;
                case "Q":
                    done = quit();
                    break;
                case "M":
                    moveItem();
                    break;
                case "O":
                    openItem();
                    break;
                    case "S":
                        saveItem();
                        break;
                case "C":
                    clearList();
                    break;
                case "V":
                    viewList();
                    break;
            }
        }while (!done);
    }

    /**
     * Display the current list along with the menu of options
     */

    public static void menuDisplay() {
        System.out.println("\nThis is your current list of items:");
        if(list.isEmpty()){
            System.out.println("\n(You have no items in your current list)");
        }
        for(String l : list) {
            System.out.println(l);
        }
        System.out.println("\nA - Add an item to the list\tD - Delete an item from the list\tI - Insert an item into the list\tP - Print the list\tM - Move an item\tO - Open a list\tS - Save list to disk\tC - Clear remove elements from list\tV - View a list\tQ - Quit the program");
    }

    /**
     * Basic addItem function from class.
     */
    private static void addItem(){
        String item = SafeInput.getNonZeroLenString(in,"What would you like to add");
        list.add(item);
    }

    /**
     * Deletes an item after showing the list. List display uses the size - 1 because of how it counts. It is a little funky if only one item is added.
     */
    private static void deleteItem(){
        numberList();
        if(list.isEmpty()){
            System.out.println("\nYou have no items in your current list");
        }else {
            int delete = SafeInput.getRangedInt(in, "What number would you like to delete? ", 1, list.size()) - 1;
            list.remove(delete);
        }
    }

    /**
     * Inserts items into the list after showing a number list. Same as deleteItem
     */
    private static void insertItem(){
        numberList();
        int num = SafeInput.getRangedInt(in, "Where would you like to insert your item? ", 1, list.size()) -1;
        String item = SafeInput.getNonZeroLenString(in,"What would you like to add? ");
        list.add(num,item);
    }

    /**
     * Print the items currently in the list
     */
    private static void printItem(){
        if(list.isEmpty()){
            System.out.println("\nYour list is empty.");
            System.out.println("--------------------------------------------");
        }else {
            System.out.println("\nYour list is printed below:");
            for(String l : list) {
                System.out.println(l);
            }
        }
    }
//Do all this later
    private static void moveItem(){

    }
    private static void openItem(){

    }
    private static void saveItem(){

    }
    private static void clearList(){

    }
    private static void viewList(){

    }

    /**
     * Confirm quitting rather than just quitting
     */
    private static boolean quit(){
        return SafeInput.getYNConfirm(in, "Are you sure you want to quit?");
    }

    /**
     * Display the numbered version of the list for delete and insert methods
     */
    private static void numberList(){
        for(int x = 0; x < list.size(); x++){
            System.out.println((x + 1) + " " + list.get(x));
        }
    }
}