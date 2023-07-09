package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class FieldRepo {
    private final HashMap<UUID, Field> repo = new HashMap<>();
    public Field get(UUID uuid){
        return repo.get(uuid);
    }
    public void put(UUID uuid, Field field){
        repo.put(uuid, field);
    }
}
