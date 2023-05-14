package thkoeln.st.st2praktikum.entities;

public class Command {
    private String action;
    private String argument;

    public Command(String commandString){
        fetchActionAndArgument(commandString);
    }

    private void fetchActionAndArgument(String commandString){
        String[] commandArray = commandString.split(",");
        action = commandArray[0].substring(1);
        argument = commandArray[1].substring(0, commandArray[1].length()-1);
    }

    public String getAction() {
        return action;
    }

    public String getArgument() {
        return argument;
    }
}
