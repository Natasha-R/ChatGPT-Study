package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class Machinecoordinateshashmap {

    private HashMap<UUID, int[]> miningmachinecoordinatehashmap = new HashMap<>();

    public HashMap<UUID, int[]> getMiningmachinecoordinatehashmap() {
        return miningmachinecoordinatehashmap;
    }

    public Machinecoordinateshashmap() {
        this.miningmachinecoordinatehashmap = miningmachinecoordinatehashmap;
    }
    public void add(UUID fieldUUID, int[] coordinates) {
        miningmachinecoordinatehashmap.put(fieldUUID, coordinates);
    }


}
