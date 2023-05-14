package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface DeviceInterface {

    public Boolean executeCommand(String[] command);

    public UUID getId();

    public UUID getSpaceId();

    public Position getPosition();
}
