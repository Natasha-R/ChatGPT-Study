package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.interfaces.Controller;

public class InvalidCommand extends Command {

    @Override
    public boolean executeOn(Controller controller) {
        return false;
    }
}
