package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.Vector2DRoom;

public interface Connectable extends Identifiable {

    Vector2DRoom getFromPosition();

    Vector2DRoom getToPosition();
}
