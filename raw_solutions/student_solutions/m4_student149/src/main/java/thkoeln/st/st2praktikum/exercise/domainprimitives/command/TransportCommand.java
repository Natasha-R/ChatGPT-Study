package thkoeln.st.st2praktikum.exercise.domainprimitives.command;

import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;
import thkoeln.st.st2praktikum.exercise.exception.FailedExecutionException;
import thkoeln.st.st2praktikum.exercise.exception.StoppedExecutionException;
import thkoeln.st.st2praktikum.exercise.execution.Traversable;

import java.util.UUID;

public class TransportCommand extends MyCommand<Traversable, UUID> {

    public TransportCommand(String argument) {
        this(UUID.fromString(argument));
    }

    public TransportCommand(UUID uuid) {
        super(CommandType.TRANSPORT, uuid);
    }

    @Override
    public void execute(Traversable executable) throws StoppedExecutionException, FailedExecutionException {
        executable.traverse(argument);
    }
}
