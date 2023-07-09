package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.MoveCommand;
import thkoeln.st.st2praktikum.exercise.SpawnCommand;
import thkoeln.st.st2praktikum.exercise.TransportCommand;

public interface Controller {
    void move(MoveCommand mc);
    boolean transport(TransportCommand tc);
    boolean spawn(SpawnCommand sc);

}
