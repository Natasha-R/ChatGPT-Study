package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class Fieldminingmachinehashmap {
    HashMap<UUID, UUID> fieldminingmachinehashmap = new HashMap<UUID, UUID>();

    public Fieldminingmachinehashmap() {
        this.fieldminingmachinehashmap = fieldminingmachinehashmap;
    }

    public void add(UUID miningmachineuuid, UUID fielduuid) {
        fieldminingmachinehashmap.put(miningmachineuuid,fielduuid );
    }
}

