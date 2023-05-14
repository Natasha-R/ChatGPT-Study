package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.command.*;
import thkoeln.st.st2praktikum.exercise.exception.ParseException;
import thkoeln.st.st2praktikum.exercise.parser.CommandParser;

import java.util.UUID;

/**
 * Nicht wundern: Ich nutze die neu vorgegebenen Klassen als Wrapper-Klassen, da meine Struktur bereits in M1 dem Design
 * entsprochen hat, allerdings teilweise mit erweiterter Funktion, daher ist ein einfaches Umschreiben nicht direkt
 * möglich (außer bei CommandType)...
 */
public class Command {

    private static CommandParser parser = new CommandParser();

    private MyCommand command;

    public Command(CommandType commandType, Integer numberOfSteps) {
        this(commandType, numberOfSteps.toString());
    }

    public Command(CommandType commandType, UUID gridId) {
        this(commandType, gridId.toString());
    }

    public Command(CommandType commandType, String argument) {
        this.command = CommandFactory.buildCommand(commandType, argument);
    }

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) throws ParseException {
        command = parser.parse(commandString);
    }

    public CommandType getCommandType() {
        return command.getType();
    }

    public Integer getNumberOfSteps() {
        return command instanceof MoveCommand ? ((MoveCommand) command).getArgument() : null;
    }

    public UUID getGridId() {
        if (command instanceof TransportCommand) return ((TransportCommand) command).getArgument();
        else if (command instanceof EnterCommand) return ((EnterCommand) command).getArgument();
        else return null;
    }

    public MyCommand getMyCommand() {
        return command;
    }
}
