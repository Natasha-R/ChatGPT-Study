package thkoeln.st.st2praktikum.exercise.command;

import thkoeln.st.st2praktikum.exercise.exception.FailedExecutionException;
import thkoeln.st.st2praktikum.exercise.exception.StoppedExecutionException;
import thkoeln.st.st2praktikum.exercise.execution.Movable;
import thkoeln.st.st2praktikum.exercise.execution.movement.Direction;
import thkoeln.st.st2praktikum.exercise.execution.movement.Movement;

public class MoveCommand extends Command<Movable, Integer> {

    protected Movement movement;

    public MoveCommand(CommandType type, String argument) {
        super(type, Integer.parseInt(argument));
        movement = new Movement(Direction.getValue(type.getCode()), this.argument);
    }

    @Override
    public void execute(Movable executable) throws StoppedExecutionException, FailedExecutionException {
        executable.move(movement);
    }
}
