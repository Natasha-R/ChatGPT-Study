package thkoeln.st.st2praktikum.exercise.command;

import thkoeln.st.st2praktikum.exercise.exception.FailedExecutionException;
import thkoeln.st.st2praktikum.exercise.exception.StoppedExecutionException;
import thkoeln.st.st2praktikum.exercise.execution.Traversable;

import java.util.UUID;

public class TraverseCommand extends Command<Traversable, UUID> {

    public TraverseCommand(String argument) {
        super(CommandType.TRAVERSE, UUID.fromString(argument));
    }

    @Override
    public void execute(Traversable executable) throws StoppedExecutionException, FailedExecutionException {
        executable.traverse(argument);
    }
}
