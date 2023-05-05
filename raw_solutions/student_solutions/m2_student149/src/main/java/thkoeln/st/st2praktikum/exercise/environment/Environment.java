package thkoeln.st.st2praktikum.exercise.environment;

import thkoeln.st.st2praktikum.exercise.device.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.environment.position.EnvironmentPosition;
import thkoeln.st.st2praktikum.exercise.environment.transition.ConnectedTransition;
import thkoeln.st.st2praktikum.exercise.exception.UUIDElementNotFoundException;
import thkoeln.st.st2praktikum.exercise.uuid.UUIDElement;

import java.util.*;
import java.util.stream.Collectors;

public class Environment {

    private final Map<UUID, Space> spaces = new HashMap<>();
    private final Map<UUID, CleaningDevice> cleaningDevices = new HashMap<>();

    private final Map<Space, List<ConnectedTransition>> connectedTransitionsOfSpace = new HashMap<>();

    private final Map<? extends Class<? extends UUIDElement>, Map<UUID, ? extends UUIDElement>> uuidElementMaps = Map.of(
            Space.class, spaces,
            CleaningDevice.class, cleaningDevices);


    public UUID addSpace(Integer height, Integer width) {
        Space newSpace = new Space(width, height);
        spaces.put(newSpace.getUuid(), newSpace);

        return newSpace.getUuid();
    }

    public UUID addCleaningDevice(String name) {
        CleaningDevice newCleaningDevice = new CleaningDevice(this, name);
        cleaningDevices.put(newCleaningDevice.getUuid(), newCleaningDevice);

        return newCleaningDevice.getUuid();
    }

    public UUID addConnection(EnvironmentPosition sourcePosition, EnvironmentPosition destinationPosition) {
        ConnectedTransition connectedTransition = new ConnectedTransition(sourcePosition, destinationPosition);

        addToConnectedTransitionsOfSpace(sourcePosition.getSpace(), connectedTransition);
        addToConnectedTransitionsOfSpace(destinationPosition.getSpace(), connectedTransition);

        return connectedTransition.getUuid();
    }

    public List<EnvironmentPosition> getDevicePositions(Space space) {
        return cleaningDevices.values().stream()
                .map(cleaningDevice -> cleaningDevice.getCurrentPosition())
                .filter(environmentPosition -> environmentPosition != null && environmentPosition.getSpace().equals(space))
                .collect(Collectors.toList());
    }

    public List<ConnectedTransition> getConnectedTransitions(Space space) {
        return connectedTransitionsOfSpace.getOrDefault(space, Collections.emptyList());
    }

    public Space getSpace(UUID spaceId) throws UUIDElementNotFoundException {
        if (spaces.containsKey(spaceId)) {
            return spaces.get(spaceId);
        } else {
            throw new UUIDElementNotFoundException(Space.class, spaceId);
        }
    }

    public CleaningDevice getCleaningDevice(UUID cleaningDeviceId) throws UUIDElementNotFoundException {
        if (cleaningDevices.containsKey(cleaningDeviceId)) {
            return cleaningDevices.get(cleaningDeviceId);
        } else {
            throw new UUIDElementNotFoundException(CleaningDevice.class, cleaningDeviceId);
        }
    }

    private void addToConnectedTransitionsOfSpace(Space space, ConnectedTransition connectedTransition) {
        List<ConnectedTransition> connectedTransitionsOfSpaceList = connectedTransitionsOfSpace.get(space);
        if (connectedTransitionsOfSpaceList != null) {
            connectedTransitionsOfSpaceList.add(connectedTransition);
        } else {
            connectedTransitionsOfSpace.put(connectedTransition.getFromPosition().getSpace(), new ArrayList<>(Arrays.asList(connectedTransition)));
        }
    }
}
