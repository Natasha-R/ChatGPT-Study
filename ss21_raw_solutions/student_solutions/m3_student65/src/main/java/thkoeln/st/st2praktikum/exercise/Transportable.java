package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Connection;

import java.util.HashMap;
import java.util.UUID;

public interface Transportable {
    boolean transport(HashMap<UUID, Connection> connections, UUID fieldUUID);
}
