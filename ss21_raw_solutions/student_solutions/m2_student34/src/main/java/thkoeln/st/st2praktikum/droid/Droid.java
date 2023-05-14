package thkoeln.st.st2praktikum.droid;

import thkoeln.st.st2praktikum.map.Obstacle;
import thkoeln.st.st2praktikum.map.Position;

public interface Droid extends Obstacle {
    boolean move(Command command);

    Position getPosition();

    String getName();
}
