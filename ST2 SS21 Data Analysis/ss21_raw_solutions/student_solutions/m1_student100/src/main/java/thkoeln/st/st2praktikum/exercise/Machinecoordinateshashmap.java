package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class Machinecoordinateshashmap {

        HashMap<UUID, int[]> miningmachinecoordinatehashmap = new HashMap<>();

        public Machinecoordinateshashmap() {
            this.miningmachinecoordinatehashmap = miningmachinecoordinatehashmap;
        }

        public void add(UUID miningmachineuuid, int[] miningmachinecoordinates) {
           miningmachinecoordinatehashmap.put(miningmachineuuid, miningmachinecoordinates );
        }



}
