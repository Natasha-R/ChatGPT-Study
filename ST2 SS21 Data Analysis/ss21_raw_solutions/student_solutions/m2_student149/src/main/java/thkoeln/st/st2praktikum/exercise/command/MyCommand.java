package thkoeln.st.st2praktikum.exercise.command;

import thkoeln.st.st2praktikum.exercise.CommandType;
import thkoeln.st.st2praktikum.exercise.exception.FailedExecutionException;
import thkoeln.st.st2praktikum.exercise.exception.StoppedExecutionException;
import thkoeln.st.st2praktikum.exercise.execution.Executable;

public abstract class MyCommand<E extends Executable, A> {

    protected CommandType type;
    protected A argument;

    public MyCommand(CommandType type, A argument) {
        this.type = type;
        this.argument = argument;
    }

    public CommandType getType() {
        return type;
    }

    public A getArgument() {
        return argument;
    }

    public abstract void execute(E executable) throws StoppedExecutionException, FailedExecutionException;
}
