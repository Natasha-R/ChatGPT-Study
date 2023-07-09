package thkoeln.st.st2praktikum.exercise;

public interface Passable extends Identifiable {

    Boolean isPassable(XYMovable movement);

    String debugPassableObjectToString();
}
