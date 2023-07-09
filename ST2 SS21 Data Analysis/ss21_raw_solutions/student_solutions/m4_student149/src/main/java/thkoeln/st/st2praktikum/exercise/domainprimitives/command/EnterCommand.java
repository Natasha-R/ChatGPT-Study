package thkoeln.st.st2praktikum.exercise.domainprimitives.command;

import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.exception.FailedExecutionException;
import thkoeln.st.st2praktikum.exercise.exception.StoppedExecutionException;
import thkoeln.st.st2praktikum.exercise.execution.Enterable;

import java.util.UUID;

public class EnterCommand extends MyCommand<Enterable, UUID> {

    public EnterCommand(String argument) {
        this(UUID.fromString(argument));
    }

    public EnterCommand(UUID uuid) {
        super(CommandType.ENTER, uuid);
    }

    @Override
    public void execute(Enterable executable) throws StoppedExecutionException, FailedExecutionException {
        executable.enter(argument, new Vector2D(0, 0));
    }
}
