package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public interface Transportable {
    boolean transport(HashMap<UUID,Connection> connections, UUID fieldUUID);
}
