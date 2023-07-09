package thkoeln.st.st2praktikum.exercise.tidyuprobot;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;

public interface Robot {
    public void addCommand(Command command);
    public void deleteCommand();
}
