package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.Vector2DRoom;

public interface Occupied extends Identifiable {

    Boolean isOccupied(Vector2DRoom position);
}
