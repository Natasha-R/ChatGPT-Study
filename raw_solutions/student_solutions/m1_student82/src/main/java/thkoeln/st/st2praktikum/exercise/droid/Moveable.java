package thkoeln.st.st2praktikum.exercise.droid;

import thkoeln.st.st2praktikum.exercise.exceptions.NoConnectionException;
import thkoeln.st.st2praktikum.exercise.exceptions.NoFieldException;
import thkoeln.st.st2praktikum.exercise.exceptions.NotSpawnedYetException;
import thkoeln.st.st2praktikum.exercise.map.Cloud;

public interface Moveable {

    public boolean move(Direction direction, Cloud world) throws NotSpawnedYetException, NoFieldException;

    public boolean travel(Cloud world) throws NoFieldException, NoConnectionException;
}
