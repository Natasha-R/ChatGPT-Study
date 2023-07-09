package thkoeln.st.st2praktikum.interfacies;

import thkoeln.st.st2praktikum.exercise.Space;

import java.util.UUID;

public interface SpaceInterface {

    public UUID creatSpace(Integer height, Integer width);

    public void addWall(UUID spaceId, String wallString);

    public UUID addConnection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate);

    public Space getSpaceById(UUID spaceId);

    public void removeCleaningDevice(UUID spaceId,UUID cleaningDeviceId);

}
