package thkoeln.st.st2praktikum.exercise.command;

import thkoeln.st.st2praktikum.exercise.CommandType;
import thkoeln.st.st2praktikum.exercise.exception.FailedExecutionException;
import thkoeln.st.st2praktikum.exercise.exception.NegativeMovementStepsException;
import thkoeln.st.st2praktikum.exercise.exception.StoppedExecutionException;
import thkoeln.st.st2praktikum.exercise.execution.Movable;
import thkoeln.st.st2praktikum.exercise.execution.movement.Direction;
import thkoeln.st.st2praktikum.exercise.execution.movement.Movement;

public class MoveCommand extends MyCommand<Movable, Integer> {

    protected Movement movement;

    public MoveCommand(CommandType type, String argument) throws NegativeMovementStepsException {
        this(type, Integer.parseInt(argument));
    }

    public MoveCommand(CommandType type, Integer steps) throws NegativeMovementStepsException {
        super(type, steps);
        movement = new Movement(Direction.getValue(type.getCode()), this.argument);
    }

    @Override
    public void execute(Movable executable) throws StoppedExecutionException, FailedExecutionException {
        executable.move(movement);
    }
}
