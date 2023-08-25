package thkoeln.st.st2praktikum.exercise.command;

import thkoeln.st.st2praktikum.exercise.environment.position.Position;
import thkoeln.st.st2praktikum.exercise.exception.FailedExecutionException;
import thkoeln.st.st2praktikum.exercise.exception.StoppedExecutionException;
import thkoeln.st.st2praktikum.exercise.execution.Enterable;

import java.util.UUID;

public class EnterCommand extends Command<Enterable, UUID> {

    public EnterCommand(String argument) {
        super(CommandType.ENTER, UUID.fromString(argument));
    }

    @Override
    public void execute(Enterable executable) throws StoppedExecutionException, FailedExecutionException {
        executable.enter(argument, Position.of(0, 0));
    }
}
