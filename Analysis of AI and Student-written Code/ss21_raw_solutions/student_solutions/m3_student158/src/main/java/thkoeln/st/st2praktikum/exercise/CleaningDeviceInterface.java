package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.repositories.SpaceRepository;

import java.util.UUID;

public interface CleaningDeviceInterface {

    public Boolean executeCommand(Task command, SpaceRepository spaceRepository);

    public UUID getId();

    public UUID getSpaceId();

    public Coordinate getCoordinate();
}
