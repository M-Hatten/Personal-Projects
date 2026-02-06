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

    public String currentFile; //current file chosen by the user
    Scanner userInputScanner = new Scanner(System.in);

    //prepares list to accept file input later
    private static ArrayList<Float> list = new ArrayList<>();

    //prepares to list all files in directory
    String directoryPath = "C:/Users/miloh/Desktop/Personal-Projects/CrochetHelper/Projects";
    File directory = new File(directoryPath);
    public File fileChecker;

    public LetsCrochet(){
        //Enter state initialization
        crochetEnterJumpTable = new HashMap<>();
        crochetEnterJumpTable.put(CrochetState.IDLE, () -> {StateEnterIdle();});
        crochetEnterJumpTable.put(CrochetState.NEW_PROJECT, () -> {StateEnterNewProject();});
        crochetEnterJumpTable.put(CrochetState.PICK_PROJECT, () -> {StateEnterPickProject();});
        crochetEnterJumpTable.put(CrochetState.LIST_PROJECTS, () -> {StateEnterList();});


        //Stay state initialization
        crochetStayJumpTable = new HashMap<>();
        crochetStayJumpTable.put(CrochetState.IDLE, () -> {return StateStayIdle();});
        crochetStayJumpTable.put(CrochetState.NEW_PROJECT, () -> {return StateStayNewProject();});
        crochetStayJumpTable.put(CrochetState.PICK_PROJECT, () -> {return StateStayPickProject();});
        crochetStayJumpTable.put(CrochetState.LIST_PROJECTS, () -> {return StateStayList();});


        //Exit state initialization
        crochetExitJumpTable = new HashMap<>();
        crochetExitJumpTable.put(CrochetState.IDLE, () -> {StateExitIdle();});
        crochetExitJumpTable.put(CrochetState.NEW_PROJECT, () -> {StateExitNewProject();});
        crochetExitJumpTable.put(CrochetState.PICK_PROJECT, () -> {StateExitPickProject();});
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
        //System.out.println("Test is: " + test);
        return test;
    }

    //Enter States
    private void StateEnterIdle(){
        //System.out.println("State Enter Idle");
    }
    private void StateEnterNewProject(){
        /*creates a new project file
        takes labor input and material cost
        labor cost (ex. $5 per hour) will be first line
        material cost will be second line
        time taken will be third line
        */
       //System.out.println("State enter New Project ;)");
    }
    private void StateEnterPickProject(){
        //selects current project
        System.out.println("Please enter the filename of the project you'd like to open. Remember to add .txt to the end of it\n");
        currentFile = userInputScanner.nextLine();
        fileChecker = new File(directoryPath + "/" + currentFile);
        
        if (fileChecker.exists() && fileChecker.isFile()){
            System.out.println("Good news! That project exists!");
        }
        else{
            System.out.println("That file doesn't exist :(\nsending you back to the main menu! Make sure to properly check the list of current project files");
            changeState(CrochetState.IDLE);
        }
        try{
            //this is a convoluted way to read the string and take the float values out of it and then add those values to the list
            fileReader = new Scanner(fileChecker);
            String data = fileReader.nextLine();
            String[] arr = data.split(",");
            for (String str : arr){
                float n = Float.parseFloat(str);
                list.add(n);
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Error! Error! Problem detected! Printing out the problem now!\n");
            e.printStackTrace(); //prints error details
        }
        

    }
    private void StateEnterList(){

    }

    //Stay States
    private boolean StateStayIdle(){

        System.out.println("Hello! It's a good day to crochet! What would you like to do?\n1. Start a new project\n2. Pick a current project\n3. List all projects\n4. Exit");
        String newInput = userInputScanner.nextLine();

        if (newInput.equals("1")){
            changeState(CrochetState.NEW_PROJECT);
        }
        else if (newInput.equals("2")){
            changeState(CrochetState.PICK_PROJECT);
        }
        else if (newInput.equals("3")){
            changeState(CrochetState.LIST_PROJECTS);
        }
        else if (newInput.equals("4")){
            System.out.println ("Goodbye!");
            return false;
        }
        else {
            System.out.println ("Please pick a proper option :(");
        }
        return true;
    }
    
    
    private boolean StateStayNewProject(){
        /*create a new text file in the projects folder and populate it with data
        text file formatting (no spaces):
        [hourly wage],[hours worked],[material cost]
        */
        System.out.println("Ready to start a new project? Great!\nWhat would you like to name the file? Please respond with [FileName].txt ");
        String newInput = userInputScanner.nextLine();
        String fullString = "";
        try {
            File newFile = new File(directoryPath + "/" + newInput);
            if(newFile.createNewFile()){
                System.out.println("File created!");

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
                    FileWriter fWriter = new FileWriter(directoryPath + "/" + newInput);
                    for (int i = 0; i < fullString.length(); i++){
                        fWriter.write(fullString.charAt(i));
                    }
                    fWriter.close();
                }
                catch (IOException e) {
                    System.out.println("Error! Error! We've encountered a problem! Message below!\n");
                    e.printStackTrace();
                }

            }
            else {
                System.out.println("File already exists :(");
            }
        }
        catch (IOException e){
            System.out.println("Error! Error! The ship is going down!\nError message:\n");
            e.printStackTrace(); //prints error details
        }
        
        System.out.println("Sending you back to the main menu! :D");
        changeState(CrochetState.IDLE);
        return true;
    }
    
    
    private boolean StateStayPickProject(){
        /* picks a file and gives you the option to clock in and clock out. Will also display hourly wage and material cost
        */
       //System.out.println("Current project: " + currentFile);
       Float wage = list.get(0);
       Float hours = list.get(1);
       Float materials = list.get(2);
       long startTimer = 0;
       long stopTimer = 0;
       long timeElapsedLong;
       Float timeElapsedFloat;

       System.out.println("********\nHere's the hourly wage you set: " + wage + "\nHere's how many hours you've worked so far: " + hours + "\nHere's the material cost for this project: " + materials + "\n********");

       while (true){
        System.out.println("What would you like to do?\n1. Clock in\n2. Clock out\n3. Update material cost\n4. Update hourly wage\n5. Finish Project\n6. Exit to Main Menu");
        String choice = userInputScanner.nextLine();
        if (choice.equals("1")){
            //Clock in
            startTimer = System.currentTimeMillis();
        }
        else if (choice.equals("2")){
            //clock out
            stopTimer = System.currentTimeMillis();
            timeElapsedLong = stopTimer - startTimer;
            //I didn't realize the timer functions applied to longs and I've gone too far to bother changign every float to a long so I'm just converting it here
            timeElapsedFloat = Float.valueOf(timeElapsedLong);
            hours += timeElapsedFloat;
            System.out.println("Your current time worked on this project is: " + hours);
        }
        else if (choice.equals("3")){
            //update material cost
            System.out.println("What's your new material cost? ");
            String updateChoice = userInputScanner.nextLine();
            materials = Float.valueOf(updateChoice);
            System.out.println("Got it! Your new material cost is $" + materials);
        }
        else if (choice.equals("4")){
            //update hourly wage
            System.out.println("What would you like your new hourly wage to be? ");
            String updateChoice = userInputScanner.nextLine();
            wage = Float.valueOf(updateChoice);
            System.out.println("Got it! Your new hourly wage for this project is $" + wage + " per hour");
        }
        else if (choice.equals("5")){
            //finish project
            System.out.println ("Hurray! Another project finished!");
            Float totalPayout = (wage * hours) + materials;
            System.out.println ("Your total payout for this comission is $" + totalPayout);
        }
        else if (choice.equals("6")){
            //exit to main menu
            System.out.println("Sending you back to the main menu! See ya! <3");
            break;
        }
       }
       list.set(0, wage);
       list.set(1, hours);
       list.set(2, materials);
       changeState(CrochetState.IDLE);
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
        System.out.println("Exiting idle!");
    }
    
    private void StateExitNewProject(){
        //System.out.println("Exiting new project!");
    }
    
    private void StateExitPickProject(){
        try{
            FileWriter fw = new FileWriter(fileChecker);
            String text = String.valueOf(list.get(0)) + "," + String.valueOf(list.get(1)) + "," + String.valueOf(list.get(2));
            for (int i = 0; i < 3; i++){
                list.remove(0);
            }
            //System.out.println("Is the list empty? " + list);
            for (int i = 0; i < text.length(); i++) 
                fw.write(text.charAt(i)); 
            fw.close();
        }
        catch (IOException e){
            System.out.println("Error! Error! The ship is going down!\nError message:\n");
            e.printStackTrace(); //prints error details
        }  
    }
    
    private void StateExitList(){
        //System.out.println("Exiting list!");
    }
}

public class Crochet {
    public static void main(String[] args){
        LetsCrochet crochet = new LetsCrochet();
        boolean keepRunning = true;
        while (keepRunning){
            keepRunning = crochet.doState();
        }
    }
    
}
