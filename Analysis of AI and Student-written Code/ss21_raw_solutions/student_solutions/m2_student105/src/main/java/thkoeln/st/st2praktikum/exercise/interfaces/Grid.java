package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.MoveCommand;

import java.util.UUID;

public interface Grid {

    void addBlocker(Blocker blocker);
    void removeBlocker(Blocker blocker);

    UUID getId();

    int getWidth();
    int getHeight();

    boolean isBlocked(MoveCommand mc);
}
