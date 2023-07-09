package thkoeln.st.st2praktikum.exercise.droid;

import thkoeln.st.st2praktikum.exercise.exceptions.NoFieldException;
import thkoeln.st.st2praktikum.exercise.map.Cloud;

import java.util.UUID;

public interface Spawnable {
    Droid getDroid (UUID droidId);

    boolean isSpawned();

    public boolean spawn(Cloud world, UUID spaceShipId) throws NoFieldException;

}
