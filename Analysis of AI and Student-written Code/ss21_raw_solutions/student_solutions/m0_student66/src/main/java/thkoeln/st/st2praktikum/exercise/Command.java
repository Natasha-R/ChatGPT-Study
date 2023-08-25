package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

@Getter
public class Command {
    private String direction;
    private int steps;

    public Command(String commandString){
        String[] commandStringArray = commandString.split(",");
        direction = commandStringArray[0].substring(1);
        steps = Integer.parseInt(commandStringArray[1].substring(0, commandStringArray[1].length()-1));
    }
}
