package thkoeln.st.st2praktikum.exercise.maintenanceDroid;

import thkoeln.st.st2praktikum.exercise.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.exception.MaintenanceDroidPositionIsNull;

import java.util.UUID;

public interface MaintenanceDroidInterface  {
    UUID getMaintenanceDroidID();
    MaintenanceDroidPosition getMaintenanceDroidPosition() throws MaintenanceDroidPositionIsNull;

}
