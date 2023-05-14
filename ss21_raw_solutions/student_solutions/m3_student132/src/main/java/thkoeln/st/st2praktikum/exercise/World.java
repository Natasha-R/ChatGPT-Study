package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.space.Space;
import thkoeln.st.st2praktikum.exercise.space.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.TransportTechnology;
import thkoeln.st.st2praktikum.exercise.transporttechnology.TransportTechnologyRepository;

import java.util.Arrays;
import java.util.UUID;

@Component
public class World implements Cloud {


    private final SpaceRepository spaceRepository;
    private final CleaningDeviceRepository cleaningDeviceRepository;
    private final TransportTechnologyRepository transportTechnologyRepository;

    @Autowired
    public World(
            SpaceRepository spaceRepository,
            CleaningDeviceRepository cleaningDeviceRepository,
            TransportTechnologyRepository transportTechnologyRepository
    ) {
        this.spaceRepository = spaceRepository;
        this.cleaningDeviceRepository = cleaningDeviceRepository;
        this.transportTechnologyRepository = transportTechnologyRepository;
    }


    public UUID addSpace(Integer height, Integer width) {
        final Space space = new Space(height, width);
        this.spaceRepository.save(space);
        return space.getSpaceId();
    }

    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        this.spaceRepository.findById(spaceId).get().addBarrier(obstacle);
    }

    public UUID addTransportTechnology(String transportTechnology) {
        final TransportTechnology transportTechnologyObj = new TransportTechnology(transportTechnology);
        this.transportTechnologyRepository.save(transportTechnologyObj);
        return transportTechnologyObj.getTransportTechnologyId();
    }

    public UUID addConnection(UUID transportTechnologyId, UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate) {
        final Connection connection = new Connection(transportTechnologyId, sourceSpaceId, sourceCoordinate, destinationSpaceId, destinationCoordinate);
        this.spaceRepository.findById(sourceSpaceId).get().addConnection(connection);
        return connection.getConnectionId();
    }

    public UUID addCleaningDevice(String name) {
        final CleaningDevice cleaningDevice = new CleaningDevice(name);
        this.cleaningDeviceRepository.save(cleaningDevice);
        return cleaningDevice.getCleaningDeviceId();
    }


    public Boolean executeCommand(UUID cleaningDeviceId, Order order) {
        try {
            return this.cleaningDeviceRepository.findById(cleaningDeviceId).get().executeCommand(order, this);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            System.out.println(Arrays.toString(exception.getStackTrace()));
        }
        return false;
    }


    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId) {
        return this.cleaningDeviceRepository.findById(cleaningDeviceId).get().getSpaceId();
    }

    public Coordinate getCoordinate(UUID cleaningDeviceId) {
        return this.cleaningDeviceRepository.findById(cleaningDeviceId).get().getCoordinate();
    }

    @Override
    public CleaningDeviceRepository getCleaningDevices() {
        return this.cleaningDeviceRepository;
    }

    @Override
    public SpaceRepository getSpaces() {
        return this.spaceRepository;
    }
}
