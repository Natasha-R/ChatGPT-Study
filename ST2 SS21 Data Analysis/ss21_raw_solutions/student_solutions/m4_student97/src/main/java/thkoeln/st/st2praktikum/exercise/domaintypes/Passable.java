package thkoeln.st.st2praktikum.exercise.domaintypes;

import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement.Moveable;

public interface Passable {

    Boolean isPassable(Moveable movement);
}
