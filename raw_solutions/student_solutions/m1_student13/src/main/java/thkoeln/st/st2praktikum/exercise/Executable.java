package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public interface Executable {
    public boolean execute(String commandString, HashMap<UUID,Field> fieldHashMap, HashMap<UUID,Connection>connectionHashMap);


}
