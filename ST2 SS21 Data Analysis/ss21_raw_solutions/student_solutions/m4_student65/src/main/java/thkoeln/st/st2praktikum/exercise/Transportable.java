package thkoeln.st.st2praktikum.exercise;


import java.util.HashMap;
import java.util.UUID;
import java.util.List;

public interface Transportable {
    boolean transport(List<Connection> connections, UUID fieldUUID);
}
