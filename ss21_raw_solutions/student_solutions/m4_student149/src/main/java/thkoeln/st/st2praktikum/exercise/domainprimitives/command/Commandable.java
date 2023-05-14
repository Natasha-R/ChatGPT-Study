package thkoeln.st.st2praktikum.exercise.domainprimitives.command;

public interface Commandable {
    Boolean executeCommand(MyCommand command);
}
