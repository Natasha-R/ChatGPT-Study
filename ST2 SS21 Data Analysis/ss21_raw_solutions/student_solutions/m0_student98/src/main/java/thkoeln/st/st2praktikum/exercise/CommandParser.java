package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

public class CommandParser {

    @Getter
    private Orientation orientation;

    @Getter
    private Integer stepLength;

    private CommandParser(Orientation orientation, Integer stepLength) {
        this.orientation = orientation;
        this.stepLength = stepLength;
    }

    public static CommandParser StringToCommand(String command) {
        int stepLength;
        Orientation orientation;
        String commandNoBraces = command.replaceAll("[\\[\\]]", "");
        String[] commandSplit = commandNoBraces.split(",");

        switch (commandSplit[0]) {
            case "no": orientation = Orientation.NO; break;
            case "ea": orientation = Orientation.EA; break;
            case "so": orientation = Orientation.SO; break;
            case "we": orientation = Orientation.WE; break;
            default: orientation = null;
        }
        stepLength = Integer.parseInt(commandSplit[1]);
        return new CommandParser(orientation, stepLength);
    }
}
