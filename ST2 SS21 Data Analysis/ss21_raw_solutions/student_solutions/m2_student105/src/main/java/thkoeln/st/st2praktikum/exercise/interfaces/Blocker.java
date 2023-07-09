package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.MoveCommand;

public interface Blocker {
    boolean blocks(MoveCommand moveCommand);
}
