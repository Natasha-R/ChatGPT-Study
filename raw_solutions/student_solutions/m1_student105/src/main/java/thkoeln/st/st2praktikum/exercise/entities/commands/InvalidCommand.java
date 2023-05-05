package thkoeln.st.st2praktikum.exercise.entities.commands;

import thkoeln.st.st2praktikum.exercise.entities.Spaceship;

public class InvalidCommand extends Command {

    @Override
    public boolean executeOn(Spaceship ship) {
        return false;
    }
}
