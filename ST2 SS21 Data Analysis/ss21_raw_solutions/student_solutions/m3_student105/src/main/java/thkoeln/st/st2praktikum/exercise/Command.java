package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.ParseException;
import thkoeln.st.st2praktikum.exercise.interfaces.Controller;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Command {

    public abstract boolean executeOn(Controller ship);

    public static Command fromString(String commandString, UUID droidId, Spaceship ship) {
        try {
            Pattern p = Pattern.compile("\\[([a-z]{2}),([\\d\\-A-Fa-f]*)\\]");
            Matcher matcher = p.matcher(commandString);
            MaintenanceDroid affectedDroid = ship.findDroidById(droidId);

            if (matcher.matches()) {
                String commandTypeOrDirection = matcher.group(1).toLowerCase();

                if (commandTypeOrDirection.equals("tr")) {
                    return new TransportCommand(ship.findDeckById(UUID.fromString(matcher.group(2))), affectedDroid);
                } else if (commandTypeOrDirection.equals("en")) {
                    return new SpawnCommand(ship.findDeckById(UUID.fromString(matcher.group(2))), affectedDroid);
                } else {
                    return MoveCommand.build(matcher.group(1), matcher.group(2), affectedDroid);
                }
            } else {
                throw new ParseException("Could not parse Command string");
            }
        } catch (ParseException pe) {
            return new InvalidCommand();
        }
    }
}
