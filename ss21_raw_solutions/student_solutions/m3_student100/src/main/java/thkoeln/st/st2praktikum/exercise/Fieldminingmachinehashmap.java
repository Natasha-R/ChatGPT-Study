package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class Fieldminingmachinehashmap {
    private HashMap<UUID, UUID> fieldminingmachinehashmap = new HashMap<UUID, UUID>();

    public HashMap<UUID, UUID> getFieldminingmachinehashmap() {
        return fieldminingmachinehashmap;
    }

    public void add(UUID miningMachineUUID, UUID fieldUUID) {
        fieldminingmachinehashmap.put(miningMachineUUID, fieldUUID);
    }
    public Fieldminingmachinehashmap() {
    }

}

