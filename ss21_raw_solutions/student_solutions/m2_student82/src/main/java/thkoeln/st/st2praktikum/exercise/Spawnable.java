package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.NoFieldException;

import java.util.UUID;

public interface Spawnable {
    Droid getDroid (UUID droidId);

    boolean isSpawned();

    public boolean spawn(Cloud world, UUID spaceShipId) throws NoFieldException;
}
