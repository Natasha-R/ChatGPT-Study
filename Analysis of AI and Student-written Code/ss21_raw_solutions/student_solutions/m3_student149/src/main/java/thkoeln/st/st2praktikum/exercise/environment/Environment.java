package thkoeln.st.st2praktikum.exercise.environment;

import thkoeln.st.st2praktikum.exercise.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.CleaningDeviceService;
import thkoeln.st.st2praktikum.exercise.Vector2D;
import thkoeln.st.st2praktikum.exercise.environment.transition.ConnectedTransition;
import thkoeln.st.st2praktikum.exercise.environment.transition.TransportTechnology;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class Environment {

    private CleaningDeviceService service;

    public Environment(CleaningDeviceService service) {
        this.service = service;
    }

    public Space addSpace(Integer height, Integer width) {
        return new Space(width, height);
    }

    public TransportTechnology addTransportTechnology(String name) {
        return new TransportTechnology(name);
    }

    public CleaningDevice addCleaningDevice(String name) {
        return new CleaningDevice(this, name);
    }

    public CleaningDevice getCleaningDevice(UUID cleaningDeviceId) {
        return service.getCleaningDevice(cleaningDeviceId);
    }

    public void updateCleaningDevice(CleaningDevice cleaningDevice) {
        service.updateCleaningDevice(cleaningDevice);
    }

    public ConnectedTransition addConnection(UUID transportTechnologyId, Space sourceSpace, Vector2D sourcePosition, Space destinationSpace, Vector2D destinationPosition) {
        return new ConnectedTransition(sourceSpace, sourcePosition,
                destinationSpace, destinationPosition, service.getTransportTechnology(transportTechnologyId));
    }

    public List<Vector2D> getDeviceVector2Ds(Space space) {
        return service.getCleaningDevice().stream()
                .filter(cleaningDevice -> Objects.equals(cleaningDevice.getSpace(), space))
                .map(cleaningDevice -> cleaningDevice.getVector2D())
                .collect(Collectors.toList());
    }

    public List<ConnectedTransition> getConnectedTransitions(Space space) {
        return service.getConnectedTransitions().stream()
                .filter(connectedTransition -> Objects.equals(space, connectedTransition.getFromSpace()) ||
                        Objects.equals(space, connectedTransition.getToSpace()))
                .collect(Collectors.toList());
    }

    public Space getSpace(UUID spaceId) {
        return service.getSpace(spaceId);
    }
}
