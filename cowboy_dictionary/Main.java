/*
 * 
 */

 import java.util.*;
 import java.io.*;

 interface StateFunction{
    public boolean invoke();
 }

 enum DictionaryState {
    IDLE,
    DEFINITION_SEARCH, //search by definition
    GENERAL_SEARCH, //search using general words associated with the word
    TERM_SEARCH //search for the term, not the definition
 }

 class CowboySearch {
    private DictionaryState state;
    private HashMap<DictionaryState, StateFunction> cbySearch;
    Scanner userInput = new Scanner(System.in);

    public static Scanner defScanner;
    public static Scanner genScanner;
    public static Scanner termScanner;

    public CowboySearch(){
        cbySearch = new HashMap<>();
        cbySearch.put(DictionaryState.IDLE, () -> { return idle();});
        cbySearch.put(DictionaryState.DEFINITION_SEARCH, () -> {return searchDef();});
        cbySearch.put(DictionaryState.GENERAL_SEARCH, () -> {return searchGen();});
        cbySearch.put(DictionaryState.TERM_SEARCH, () -> {return searchTerm();});

        state = DictionaryState.IDLE;
    }

    public void changeState(DictionaryState newState){
        if (state != newState){
            state = newState;
        }
    }

    private boolean doState(){
        return cbySearch.get(state).invoke();
    }

    private boolean idle(){
        System.out.println("What do you want to search? \n1. Definition search \n 2. General search \n3. Term Search\n?");
        String newInput = userInput.nextLine();
        if (newInput.equals("1")){
            changeState(DictionaryState.DEFINITION_SEARCH);
        }
        else if (newInput.equals("2")){
            changeState(DictionaryState.GENERAL_SEARCH);
        }
        else if (newInput.equals("3")){
            changeState(DictionaryState.TERM_SEARCH);
        }
        else{
            System.out.println("Please select a proper option, bozo!");
        }
        return true;
    }
    private boolean searchDef(){
        try{
            File DefObj = new File("definitions.txt");
            defScanner = new Scanner(DefObj);
        }
        catch (FileNotFoundException e){
            System.out.println("File not found :(");
        }
    }
    private boolean searchGen(){}
    private boolean searchTerm(){}

 }