import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import static java.nio.file.StandardOpenOption.CREATE;
import javax.swing.*;

/**
 * @author Peyton Terry terrypn@mail.uc.edu
 */

public class ListMaker {
    private static final ArrayList<String> list = new ArrayList<>();
    private static final Scanner in = new Scanner(System.in); //No need to repeat Scanner in
    private static boolean needsToBeSaved = false; //Track save function
    private static String currentFile = null;

    /**
     * Switch is the easiest way to filter through all the choices. A lot like Rock Paper Scissors Lab.
     * menuChoice.toUpperCase couldn't use .equalsIgnoreCase so this made it easier for the switch
     */
    public static void main(String[] args) {
        //Okay so here's where things get funky
        //I really struggled with this one
        //I couldn't get the GUI to load and the only reason I found was
        //the code was being blocked by something in the main,
        //so I added this to the main and made a basic wait function to act like it is loading
        //By doing this, later on when the user selects O to open, the GUI actually opens
        //Until I did this, the GUI wouldn't open and the code would break
        //If I could get feedback on how to do this properly it would be helpful

        System.out.println("Initializing JFileChooser GUI please wait...");
        try {
            Thread.sleep(2000);
            System.out.println("Close JFileChooser to see full menu...");
            Thread.sleep(5000);
            System.out.println("Initialization complete!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
        boolean done = false;

        do {
            menuDisplay();
            String menuChoice = SafeInput.getRegExString(in,"What would you like to do?", "[AaDdIiQqMmOoSsCcVv]");
            switch (menuChoice.toUpperCase()) {
                case "O":
                    openItem();
                    break;
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "I":
                    insertItem();
                    break;
                case "Q":
                    done = quit();
                    break;
                case "M":
                    moveItem();
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

    public static void menuDisplay() {
        System.out.println("\nThis is your current list of items:");
        if(list.isEmpty()){
            System.out.println("\n(You have no items in your current list)");
        }
        for(String l : list) {
            System.out.println(l);
        }
        System.out.println("\nA - Add an item to the list\tD - Delete an item from the list\tI - Insert an item into the list\tM - Move an item\tO - Open a list\tS - Save list to disk\tC - Clear remove elements from list\tV - View a list\tQ - Quit the program");
    }

    /**
     * Basic addItem function from class.
     */
    private static void addItem(){
        //from Lab 12, just added needsToBeSaved
        String item = SafeInput.getNonZeroLenString(in,"What would you like to add");
        list.add(item);
        needsToBeSaved = true;
    }

    /**
     * Deletes an item after showing the list. List display uses the size - 1 because of how it counts. It is a little funky if only one item is added.
     */
    private static void deleteItem(){
        //from Lab 12, just added needsToBeSaved
        numberList();
        if(list.isEmpty()){
            System.out.println("\nYou have no items in your current list");
        }else {
            int delete = SafeInput.getRangedInt(in, "What number would you like to delete? ", 1, list.size()) - 1;
            list.remove(delete);
            needsToBeSaved = true;
        }
    }

    /**
     * Inserts items into the list after showing a number list. Same as deleteItem
     */
    private static void insertItem(){
        //from Lab 12, just added needsToBeSaved
        numberList();
        int num = SafeInput.getRangedInt(in, "Where would you like to insert your item? ", 1, list.size()) -1;
        String item = SafeInput.getNonZeroLenString(in,"What would you like to add? ");
        list.add(num,item);
        needsToBeSaved = true;
    }

    /**
     * View the items currently in the list
     */
    private static void viewList() {
        //replaced print list
        if (list.isEmpty()) {
            System.out.println("\nYour list is empty.");
            System.out.println("--------------------------------------------");
        } else {
            System.out.println("\nYour list is printed below:");
            for (String l : list) {
                System.out.println(l);
            }
        }
    }

    private static void moveItem(){
        //Pretty self-explanatory, shows the list, move an item to new spot, needs to be saved after.
        numberList();
        if(list.isEmpty()){
            System.out.println("\nYour List is empty.");
            System.out.println("--------------------------------------------");
        }
        else{
            int moveFrom = SafeInput.getRangedInt(in, "What number would you like to move? ", 1, list.size()) - 1;
            int moveTo = SafeInput.getRangedInt(in, "Where would you like to move it to? ", 1, list.size()) - 1;

            String itemMove = list.remove(moveFrom);
            list.add(moveTo,itemMove);
            needsToBeSaved = true;
        }
    }
    public static void openItem() {
        //checks to see if needsToBeSaved is true and to make sure the list isn't empty so you don't save empty file.
        if(needsToBeSaved && !list.isEmpty()) {
            boolean isNeedsToBeSaved = SafeInput.getYNConfirm(in, "You have unsaved changes. Would you like to save them first?");
            if (isNeedsToBeSaved) {
                saveItem();
            }
        }
        //System.out.println("Print"); Use this to see if openItem will even start JFileChooser
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec;

        try {
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                //Only use try BufferedReader to read the files
                try (BufferedReader reader = Files.newBufferedReader(file)) {
                    int line = 0;
                    while ((rec = reader.readLine()) != null) { //Make sure there is something there
                        line++;
                        System.out.printf("\nLine %4d %-60s", line, rec);
                    }
                }
                System.out.println("\n\nData file read!");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Print2"); Use this to test if openItem runs all the way through
    }


    private static void saveItem(){
        //Used pretty much the exact code from class but made sure txt was added
        File workingDirectory = new File(System.getProperty("user.dir"));
        String fileName = SafeInput.getNonZeroLenString(in, "Enter the file name");
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\" + fileName + ".txt");
        try {
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer
                    = new BufferedWriter(new OutputStreamWriter(out));

            for (String rec : list) {
                writer.write(rec, 0, rec.length());
                writer.newLine();
            }
            currentFile = fileName;
            needsToBeSaved = false;
            writer.close();
            System.out.println("Data file written.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void clearList(){
        if(SafeInput.getYNConfirm(in, "Are you sure you want to clear the list?")){
            list.clear();
            needsToBeSaved = true;
        }
    }

    /**
     * Confirm quitting rather than just quitting
     */
    private static boolean quit(){
        //Again just make sure that there isn't anything that needs to be saved prior to quitting
        if(needsToBeSaved && !list.isEmpty()) {
            boolean isNeedsToBeSaved = SafeInput.getYNConfirm(in, "You have unsaved changes. Would you like to save them first?");
            if (isNeedsToBeSaved) {
                saveItem();
            }
        }
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
