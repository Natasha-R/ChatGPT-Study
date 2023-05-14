package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Commandable{

    boolean transport(UUID maintenanceDroidId, String command);
}
