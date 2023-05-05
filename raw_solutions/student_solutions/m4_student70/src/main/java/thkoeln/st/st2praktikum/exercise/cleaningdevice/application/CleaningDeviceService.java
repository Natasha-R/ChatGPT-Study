package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.repo.CleaningDeviceRepositry;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.Wall;
import thkoeln.st.st2praktikum.exercise.space.repo.SpaceRepositry;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.repo.ConnectionRepositry;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CleaningDeviceService {

    @Autowired
    CleaningDeviceRepositry cleaningDeviceRepositry;
    @Autowired
    SpaceRepositry spaceRepositry;
    @Autowired
    ConnectionRepositry connectionRepositry;

    /**
     * This method adds a new cleaning device
     *
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name);
        cleaningDeviceRepositry.save(cleaningDevice);
        return cleaningDevice.getCleaningDeviceId();
    }

    /**
     * This method lets the cleaning device execute a task.
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @param task             the given task
     *                         NORTH, WEST, SOUTH, EAST for movement
     *                         TRANSPORT for transport - only works on grid cells with a connection to another space
     *                         ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the cleaning device hits a wall or
     * another cleaning device, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Task task) {

        Optional<CleaningDevice> cleaningDevice = cleaningDeviceRepositry.findById(cleaningDeviceId);
        if (!cleaningDevice.isPresent()) return false;
        cleaningDevice.get().getTasks().add(task);
        cleaningDeviceRepositry.save(cleaningDevice.get());
        switch (task.getTaskType()) {
            case ENTER:
                return initialCleaningDevice(cleaningDevice.get(), task.getGridId());
            case TRANSPORT:
                return transportCleaningDevice(cleaningDevice.get(), task.getGridId());
            default:
                return moveCleaningDevice(cleaningDevice.get(), task);
        }


    }

    private boolean moveCleaningDevice(CleaningDevice cleaningDevice, Task task) {


        Optional<Space> space = spaceRepositry.findById(cleaningDevice.getSpaceId());
        if (!space.isPresent()) return false;
        for (int i = 1; i <= task.getNumberOfSteps(); i++) {
            if (!space.get().isCoordinateExistAndEmpty(cleaningDevice.getPoint(), task.getTaskType()))
                return false;
            for (Wall wall : space.get().getWalls())
                if (wall.isCross(cleaningDevice.getPoint(), task.getTaskType())) return false;

            cleaningDevice.move(task.getTaskType());
            cleaningDeviceRepositry.save(cleaningDevice);
        }
        return true;
    }


    public boolean transportCleaningDevice(CleaningDevice cleaningDevice, UUID destinationSpaceId) {
        Optional<Space> sourceSpace = spaceRepositry.findBycleaningDeviceId(cleaningDevice.getCleaningDeviceId());
        Optional<Space> destinationSpace = spaceRepositry.findById((destinationSpaceId));
        List<Connection> connections = connectionRepositry.findBySourceAndDestination(cleaningDevice.getSpaceId(), destinationSpaceId);
        if (!sourceSpace.isPresent() || !destinationSpace.isPresent()) return false;
        System.out.println(cleaningDevice.getSpaceId().toString());
        for (Connection connection : connections) {
            final boolean isDestinationRight = connection.getDestinationSpaceId().equals((destinationSpaceId));
            final boolean isDviceInTrPosition = cleaningDevice.getPoint().equals(connection.getSourceCoordinate());
            final boolean isDestinationCoordinateEmpty = destinationSpace.get()
                    .isCoordinateExistAndEmpty(connection.getDestinationCoordinate(), TaskType.TRANSPORT);

            if (isDestinationRight && isDviceInTrPosition && isDestinationCoordinateEmpty) {

                sourceSpace.get().getCleaningDevices().remove(cleaningDevice);
                destinationSpace.get().getCleaningDevices().add(cleaningDevice);
                cleaningDevice.setPoint(connection.getDestinationCoordinate());
                cleaningDevice.setSpaceId(destinationSpaceId);
                cleaningDeviceRepositry.save(cleaningDevice);
                spaceRepositry.save(sourceSpace.get());
                spaceRepositry.save(destinationSpace.get());
                System.out.println(cleaningDevice.getSpaceId().toString());
                return true;
            }
        }
        return false;
    }


    // put the dvice in given place
    public boolean initialCleaningDevice(CleaningDevice cleaningDevice, UUID spaceId) {
        Point point = new Point(0, 0);
        Optional<Space> space = spaceRepositry.findById((spaceId));

        if (space.isPresent() && space.get().isCoordinateExistAndEmpty(point, TaskType.ENTER)) {
            space.get().getCleaningDevices().add(cleaningDevice);
            spaceRepositry.save(space.get());
            cleaningDevice.setSpaceId(spaceId);
            cleaningDevice.setPoint(point);
            cleaningDeviceRepositry.save(cleaningDevice);
        } else return false;


        return true;
    }


    /**
     * This method returns the space-ID a cleaning device is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId) {
        Optional<Space> space = spaceRepositry.findBycleaningDeviceId(cleaningDeviceId);
        if (space.isPresent()) return space.get().getSpaceId();
        throw new UnsupportedOperationException();
    }

    /**
     * This method returns the point a cleaning device is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the point of the cleaning device
     */
    public Point getCleaningDevicePoint(UUID cleaningDeviceId) {
        Optional<CleaningDevice> cleaningDevice = cleaningDeviceRepositry.findById(cleaningDeviceId);
        if (cleaningDevice.isPresent())
            return cleaningDevice.get().getPoint();

        throw new UnsupportedOperationException();
    }
}
