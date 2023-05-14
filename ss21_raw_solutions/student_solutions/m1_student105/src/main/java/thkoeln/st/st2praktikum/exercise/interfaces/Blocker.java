package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.entities.commands.MoveCommand;

public interface Blocker {
    boolean blocks(MoveCommand moveCommand);
}
