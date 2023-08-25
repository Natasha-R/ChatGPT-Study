package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {

    @Getter
    private String command;
    @Getter
    private String option;

    private String INITIALISATION_COMMAND = "en";
    private String TRAVEL_COMMAND = "tr";
    private ArrayList<String> MOVE_COMMANDS = new ArrayList<>(Arrays.asList("we", "so", "no", "ea"));

    public Command(String command) {
        final String regex = "\\[(.{2}),(.*)\\]";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(command);

        while (matcher.find()) {
            this.command = matcher.group(1);
            this.option = matcher.group(2);
        }
    }

    public boolean isInitialisationCommand() {
        return this.command.equals(this.INITIALISATION_COMMAND);
    }

    public boolean isTransportCommand() {
        return this.command.equals(this.TRAVEL_COMMAND);
    }

    public boolean isMoveCommand() {
        return this.MOVE_COMMANDS.contains(this.command);
    }
}
