/*
Hello to anyone reading this!
This application is meant to help me track my characters in a mobile game I like to play

Capabilities
- Lists each umamusume
- Tracks race stats (Track type, Distance, and Style)
- Tracks highest graded veteran score
- Tracks what track they use in Team Trials
- Tracks umamusume tier

Data will be kept in a nested hashmap for ease of access and will be stored to a json file
This is mostly all just for practice to keep my skills sharp

What I've learned while throwing this code together:
- How to install Maven
- How to install packages with Maven
- how to write a nested hashmap to a json file and read it back
- How to create a finite state machine
- How to naavigate nested hashmaps
- Why it's easier to make a finite state machine in java than in python

How to run this program:
- open up two command prompt windows
- set one to Umamusume
- set the other to lib/build/libs
- build in the Umamusume folder with "./gradlew build"
- run the jar file in the libs folder with "java -jar lib.jar"
 */

import java.util.*;
import java.io.*;
import com.google.gson.*;

interface HorseFunctionEnterExit {
    public void invoke();
}

interface HorseFunctionStay {
    public boolean invoke();
}

enum HorseState{
    IDLE,
    ADD,
    UPDATE,
    TRIALS,
    CAREER;
}

class Umamusume {
    //initializing hashmaps
    private HorseState state;
    private HashMap<HorseState, HorseFunctionEnterExit> stateEnterMeths;
    private HashMap<HorseState, HorseFunctionStay> stateStayMeths;
    private HashMap<HorseState, HorseFunctionEnterExit> stateExitMeths;

    //initializing scanner object to read file
    public static Scanner horseDataScanner;

    //initializing scanner for user input
    Scanner userInputScanner = new Scanner(System.in);


    public Umamusume() {
        //initializing more hashmaps and putting methods in there
        stateEnterMeths = new HashMap<>();
        stateEnterMeths.put(HorseState.IDLE, () -> {StateEnterIdle();});
        stateEnterMeths.put(HorseState.ADD, () -> {StateEnterAdd();});
        stateEnterMeths.put(HorseState.UPDATE, () -> {StateEnterUpdate();});
        stateEnterMeths.put(HorseState.TRIALS, () -> {StateEnterTrials();});
        stateEnterMeths.put(HorseState.CAREER, () -> {StateEnterCareer();});

        stateStayMeths = new HashMap<>();
        stateStayMeths.put(HorseState.IDLE, () -> { return StateStayIdle();});
        stateStayMeths.put(HorseState.ADD, () -> { return StateStayAdd();});
        stateStayMeths.put(HorseState.UPDATE, () -> { return StateStayUpdate();});
        stateStayMeths.put(HorseState.TRIALS, () -> { return StateStayTrials();});
        stateStayMeths.put(HorseState.CAREER, () -> { return StateStayCareer();});

        stateExitMeths = new HashMap<>();
        stateExitMeths.put(HorseState.IDLE, () -> {StateExitIdle();});
        stateExitMeths.put(HorseState.ADD, () -> {StateExitAdd();});
        stateExitMeths.put(HorseState.UPDATE, () -> {StateExitUpdate();});
        stateExitMeths.put(HorseState.TRIALS, () -> {StateExitTrials();});
        stateExitMeths.put(HorseState.CAREER, () -> {StateExitCareer();});

        state = HorseState.IDLE;
    }

    public void changeState (HorseState newState){
        if ( state != newState){
            state = newState;
            if (stateEnterMeths.containsKey(newState)){
                stateEnterMeths.get(newState).invoke();
            }
        }
    }

    public boolean doState(){
        boolean test = stateStayMeths.get(state).invoke();
        System.out.println("Test is: " + test);
        return test;
    }


    /*
    ENTER METHODS
    */
    private void StateEnterIdle(){
        System.out.println("State Enter Idle");

    }
    private void StateEnterAdd(){
        System.out.println("State Enter Add");
        //read the file

        //figure out how to read a json file here

    }
    private void StateEnterUpdate(){
        System.out.println("State Enter Update");
    }
    private void StateEnterTrials(){
        System.out.println("State Enter Trials");
    }
    private void StateEnterCareer(){
        System.out.println("State Enter Career");
    }

    /*
    STAY METHODS
    */
    private boolean StateStayIdle(){
        System.out.println("State Stay Idle");
        System.out.println("Welcome to the main menu!\r\n" + //
                        "\r\n" + //
                        "1. Add a umamusume to the roster\r\n" + //
                        "2. Update an umamusume's veteran score\r\n" + //
                        "3. View the team trials menu\r\n" + //
                        "4. View the career menu\r\n" + //
                        "5. Quit");
        String newInput = userInputScanner.nextLine();
        if (newInput.equals("1")){
            changeState(HorseState.ADD);
        }
        else if (newInput.equals("2")){
            changeState(HorseState.UPDATE);
        }
        else if (newInput.equals("3")){
            changeState(HorseState.TRIALS);
        }
        else if (newInput.equals("4")){
            changeState(HorseState.CAREER);
        }
        else if (newInput.equals("5")){
            System.out.println("Goodbye! :D");
            return false;
        }
        return true;
    }
    private boolean StateStayAdd(){
        System.out.println("State Stay Add");
        return true;
    }
    private boolean StateStayUpdate(){
        System.out.println("State Stay Update");
        return true;
    }
    private boolean StateStayTrials(){
        System.out.println("State Stay Trials");
        return true;
    }
    private boolean StateStayCareer(){
        System.out.println("State Stay Career");
        return true;
    }

    /*
    EXIT METHODS
    */
    private void StateExitIdle(){
        System.out.println("State Exit Idle");

    }
    private void StateExitAdd(){
        System.out.println("State Exit Add");

    }
    private void StateExitUpdate(){
        System.out.println("State Exit Update");

    }
    private void StateExitTrials(){
        System.out.println("State Exit Trials");

    }
    private void StateExitCareer(){
        System.out.println("State Exit Career");

    }


}

public class horseGame{
    public static void main(String[] args) {
        System.out.println("It's a great day to play Umamusume!");
        Umamusume umamusume = new Umamusume();
        boolean keepRunning = true;
        while(keepRunning){
            keepRunning = umamusume.doState();
        }
    }
}