package thkoeln.st.st2praktikum.exercise;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class MiningMachineRepo {
    private final HashMap<UUID, MiningMachine> repo = new HashMap<>();
    public MiningMachine get(UUID uuid){
        return repo.get(uuid);
    }
    public void put(UUID uuid, MiningMachine field){
        repo.put(uuid, field);
    }
    public Collection<MiningMachine> values() {
        return repo.values();
    }
}
