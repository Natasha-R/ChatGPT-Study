package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.NoConnectionException;
import thkoeln.st.st2praktikum.exercise.exceptions.NoFieldException;
import thkoeln.st.st2praktikum.exercise.exceptions.NotSpawnedYetException;

public interface Moveable {
    public boolean move(OrderType orderType, Cloud world) throws NotSpawnedYetException, NoFieldException;

    public boolean travel(Cloud world) throws NoFieldException, NoConnectionException;
}
