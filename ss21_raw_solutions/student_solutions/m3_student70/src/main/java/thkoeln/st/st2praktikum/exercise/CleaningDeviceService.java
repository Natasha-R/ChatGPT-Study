package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class CleaningDeviceService {
    private final Controller controller;

    @Autowired
    public CleaningDeviceService(Controller controller) {
        this.controller = controller;
    }


    /**
     * This method creates a new space.
     *
     * @param height the height of the space
     * @param width  the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        var f = new Space(height, width);
        controller.getSpaceRepositry().save(f);
        return f.getId();
    }

    /**
     * This method adds a wall to a given space.
     *
     * @param spaceId the ID of the space the wall shall be placed on
     * @param wall    the end points of the wall
     */
    public void addWall(UUID spaceId, Wall wall) {
        var field = controller.getSpaceRepositry().get(spaceId);
        field.getWalls().add(wall);
        controller.getSpaceRepositry().save(field);
    }

    /**
     * This method adds a transport category
     *
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        var ts = new TransportCategory(category);
        controller.getTransportCategoryRepositry().save(ts);
        return ts.getId();
    }

    /**
     * This method adds a traversable connection between two spaces based on a transport category. Connections only work in one direction.
     *
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceSpaceId       the ID of the space where the entry point of the connection is located
     * @param sourcePoint         the point of the entry point
     * @param destinationSpaceId  the ID of the space where the exit point of the connection is located
     * @param destinationPoint    the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceSpaceId, Point sourcePoint,
                              UUID destinationSpaceId, Point destinationPoint) {
        var transportCategory = controller.getTransportCategoryRepositry().get(transportCategoryId);
        var srcSpace = controller.getSpaceRepositry().get(sourceSpaceId);

        var c = new Connection(sourcePoint, destinationPoint);
        controller.getConnectionRepositry().save(c);

        transportCategory.getConnections().add(c);
        controller.getTransportCategoryRepositry().save(transportCategory);

        srcSpace.getConnections().put(destinationSpaceId, c);
        controller.getSpaceRepositry().save(srcSpace);
        return c.getId();    }

    /**
     * This method adds a new cleaning device
     *
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        var mm = new CleaningDevice(name, new Point(0,0), null);
        controller.getCleaningDeviceRepositry().save(mm);
        return mm.getId();
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
        return controller.execute(cleaningDeviceId, task);
    }



    /**
     * This method returns the space-ID a cleaning device is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId) {
        var mm = controller.getCleaningDeviceRepositry().findById(cleaningDeviceId).get();
        return mm.getSpaceId();
    }

    /**
     * This method returns the point a cleaning device is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the point of the cleaning device
     */
    public Point getCleaningDevicePoint(UUID cleaningDeviceId) {
        var mm = controller.getCleaningDeviceRepositry().findById(cleaningDeviceId).get();
        return mm.getPoint();
    }





}
