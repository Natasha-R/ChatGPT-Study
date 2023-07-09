package thkoeln.st.st2praktikum.exercise.world2.miningMaschine;

import thkoeln.st.st2praktikum.exercise.world2.Cloud;
import thkoeln.st.st2praktikum.exercise.world2.coordinate.Coordinate;

import java.util.UUID;

public interface Spawnable {
    Miningmaschine getMiningMaschine (UUID MiningMaschineId);
    boolean isSpawned();
    boolean spawnMiningMaschine(UUID fieldId, Coordinate coordinate, boolean blocked, Cloud world);
    boolean tunnelMininngMaschine(Cloud world);
}
