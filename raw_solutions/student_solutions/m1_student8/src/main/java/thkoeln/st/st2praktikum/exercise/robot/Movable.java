package thkoeln.st.st2praktikum.exercise.robot;

import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.position.Positionable;
import thkoeln.st.st2praktikum.exercise.room.Roomable;

public interface Movable extends Identifiable
{
    public void moveNorth(String steps);

    public void moveEast(String steps);

    public void moveSouth(String steps);

    public void moveWest(String steps);

    public boolean placeMovable(Roomable room);

    public String showActualPosition();

    public Positionable getActualPosition();
}
