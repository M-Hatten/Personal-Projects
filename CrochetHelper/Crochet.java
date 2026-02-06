/*
Hello! 
This code is to help me keep track of how much time I've put into crocheting comissions!

This app should be relatively simple.
Functions:
- Track multiple projects at once
- Allow user to "clock in" and "clock out"
- Allow user to enter material costs
- Allow user to set labor cost (Ex. $5 per hour of labor)
- clocking in starts a timer
- clocking out stops the timer and logs the time taken
- Saves all data to a txt or json file so it is able to keep track of data when program is not running
- when a project is complete, computes how much the project will sell for using material and labor cost

Extra notes:
- when adding a new project, will create a new text file to save new data
*/
package CrochetHelper;
import java.util.*;
import java.io.*;

interface CrochetEnterExitMeth{
    public void invoke();
}

interface CrochetStayMeth{
    public boolean invoke();
}

enum CrochetState {
    IDLE, //main menu
    NEW_PROJECT, //goes to page that creates a whole new project
    PICK_PROJECT, //selects the current project and reads the file
    FINISH_PROJECT, //notes a project as finished and presents final cost estimate
    LIST_PROJECTS; //Lists all current project files
}

class LetsCrochet{
    //initializing hashmaps
    private CrochetState state;
    private HashMap<CrochetState, CrochetEnterExitMeth> crochetEnterJumpTable;
    private HashMap<CrochetState, CrochetStayMeth> crochetStayJumpTable;
    private HashMap<CrochetState, CrochetEnterExitMeth> crochetExitJumpTable;

    //file reading object
    public static Scanner fileReader;

    public String currentfile; //current file chosen by the user
    Scanner userInputScanner = new Scanner(System.in);

    //prepares list to accept file input later
    private static ArrayList<Float> list = new ArrayList<>();

    //prepares to list all files in directory
    String directoryPath = "C:/Users/miloh/Desktop/Personal-Projects/CrochetHelper/Projects";
    File directory = new File(directoryPath);

    public LetsCrochet(){
        //Enter state initialization
        crochetEnterJumpTable = new HashMap<>();
        crochetEnterJumpTable.put(CrochetState.IDLE, () -> {StateEnterIdle();});
        crochetEnterJumpTable.put(CrochetState.NEW_PROJECT, () -> {StateEnterNewProject();});
        crochetEnterJumpTable.put(CrochetState.PICK_PROJECT, () -> {StateEnterPickProject();});
        crochetEnterJumpTable.put(CrochetState.FINISH_PROJECT, () -> {StateEnterFinishProject();});
        crochetEnterJumpTable.put(CrochetState.LIST_PROJECTS, () -> {StateEnterList();});


        //Stay state initialization
        crochetStayJumpTable = new HashMap<>();
        crochetStayJumpTable.put(CrochetState.IDLE, () -> {return StateStayIdle();});
        crochetStayJumpTable.put(CrochetState.NEW_PROJECT, () -> {return StateStayNewProject();});
        crochetStayJumpTable.put(CrochetState.PICK_PROJECT, () -> {return StateStayPickProject();});
        crochetStayJumpTable.put(CrochetState.FINISH_PROJECT, () -> {return StateStayFinishProject();});
        crochetStayJumpTable.put(CrochetState.LIST_PROJECTS, () -> {return StateStayList();});


        //Exit state initialization
        crochetExitJumpTable = new HashMap<>();
        crochetExitJumpTable.put(CrochetState.IDLE, () -> {StateExitIdle();});
        crochetExitJumpTable.put(CrochetState.NEW_PROJECT, () -> {StateExitNewProject();});
        crochetExitJumpTable.put(CrochetState.PICK_PROJECT, () -> {StateExitPickProject();});
        crochetExitJumpTable.put(CrochetState.FINISH_PROJECT, () -> {StateExitFinishProject();});
        crochetExitJumpTable.put(CrochetState.LIST_PROJECTS, () -> {StateExitList();});


        //Makes the initial state IDLE
        state = CrochetState.IDLE;
    }

    public void changeState(CrochetState newState){
        if (state != newState){
            if (state != CrochetState.IDLE){
                crochetExitJumpTable.get(state).invoke();
            }
            state = newState;
            crochetEnterJumpTable.get(state).invoke();
        }
    }

    public boolean doState(){
        boolean test = crochetStayJumpTable.get(state).invoke();
        System.out.println("Test is: " + test);
        return test;
    }

    //Enter States
    private void StateEnterIdle(){
        System.out.println("State Enter Idle");
    }
    private void StateEnterNewProject(){
        /*creates a new project file
        takes labor input and material cost
        labor cost (ex. $5 per hour) will be first line
        material cost will be second line
        time taken will be third line
        */
       System.out.println("State enter New Project ;)");
    }
    private void StateEnterPickProject(){
        //selects current project
        System.out.println("Please enter the filename of the project you'd like to open. Remember to add .txt to the end of it\n");
        currentfile = userInputScanner.nextLine();
        
    }
    private void StateEnterFinishProject(){

    }
    private void StateEnterList(){

    }

    //Stay States
    private boolean StateStayIdle(){

        System.out.println("Hello! It's a good day to crochet! What would you like to do?\n1. Start a new project\n2. Pick a current project\n3. Finish a project\n4. List all projects\n5. Exit");
        String newInput = userInputScanner.nextLine();

        if (newInput.equals("1")){
            changeState(CrochetState.NEW_PROJECT);
        }
        else if (newInput.equals("2")){
            changeState(CrochetState.PICK_PROJECT);
        }
        else if (newInput.equals("3")){
            changeState(CrochetState.FINISH_PROJECT);
        }
        else if (newInput.equals("4")){
            changeState(CrochetState.LIST_PROJECTS);
        }
        else if (newInput.equals("5")){
            System.out.println ("Goodbye!");
            return false;
        }
        else {
            System.out.println ("Please pick a proper option :(");
        }
        return true;
    }
    
    /*create a new text file in the projects folder and populate it with data
    text file formatting (no spaces):
    [hourly wage],[hours worked],[material cost]
    */
    private boolean StateStayNewProject(){
        System.out.println("Ready to start a new project? Great! What would you like to name the file? Please respond with [FileName].txt ");
        String newInput = userInputScanner.nextLine();
        String fullString = "";
        try {
            File newFile = new File(newInput);
            if(newFile.createNewFile()){
                System.out.println("File created!");
            }
            else {
                System.out.println("File already exists :(\nsending you back to the main menu!");
                changeState(CrochetState.IDLE);
            }
        }
        catch (IOException e){
            System.out.println("Error! Error! The ship is going down!\nError message:\n");
            e.printStackTrace(); //prints error details
        }
        System.out.println("What is your hourly wage? ");
        String newData = userInputScanner.nextLine();
        fullString += newData;

        System.out.println("How many hours have you worked so far? ");
        newData = userInputScanner.nextLine();
        fullString += "," + newData;   

        System.out.println("How much have you spent on materials? ");
        newData = userInputScanner.nextLine();
        fullString += "," + newData;

        System.out.println("Here's what you gave me!\n" + fullString + "\nAdding to file now! :D");

        try {
            FileWriter fWriter = new FileWriter(directoryPath);
            fWriter.write(fullString);
            fWriter.close();
        }
        catch (IOException e) {
            System.out.println("Error! Error! We've encountered a problem! Message below!\n");
            e.printStackTrace();
        }
        System.out.println("Sending you back to the main menu! :D");
        changeState(CrochetState.IDLE);
        return true;
    }
    
    /* picks a file and gives you the option to clock in and clock out. Will also display hourly wage and material cost
     */
    private boolean StateStayPickProject(){

        return true;
    }

    private boolean StateStayFinishProject(){
        return true;
    }

    //Lists all current project files
    private boolean StateStayList(){
        File[] files = directory.listFiles();
        if (files != null){
            for (File file : files){
                System.out.println(file.getName());
            }
        }
        else{
            System.out.println ("No projects here!");
        }
        System.out.println("********\nThat's all! Sending you back to the main menu! :D\n********");
        changeState(CrochetState.IDLE);
        return true;
    }

    //Exit States
    private void StateExitIdle(){

    }
    private void StateExitNewProject(){

    }
    private void StateExitPickProject(){

    }
    private void StateExitFinishProject(){

    }
    private void StateExitList(){

    }
}

public class Crochet {
    public static void Main(String[] args){
        LetsCrochet crochet = new LetsCrochet();
        boolean keepRunning = true;
        while (keepRunning){
            keepRunning = crochet.doState();
        }
    }
    
}
