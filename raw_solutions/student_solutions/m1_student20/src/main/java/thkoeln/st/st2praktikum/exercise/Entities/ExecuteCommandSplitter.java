package thkoeln.st.st2praktikum.exercise.Entities;

public class ExecuteCommandSplitter {

    public String [] splittCommandString(String commandString){
        commandString = commandString.replace("[","");
        commandString = commandString.replace("]","");
        String [] splittedCommandString = commandString.split(",");
        return splittedCommandString;
    }

}
