package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import thkoeln.st.st2praktikum.exercise.entity.*;
import thkoeln.st.st2praktikum.exercise.repository.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;


@Service
public class CleaningDeviceService {
    @Autowired
    CleaningDeviceRepository cleaningDeviceRepo;

    @Autowired
    ConnectionRepository connectionRepo;

    @Autowired
    ObstacleRepository obstacleRepo;

    @Autowired
    SpaceRepository spaceRepo;

    @Autowired
    TransportCategoryRepository transportCategoryRepo;


    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        UUID spaceId = UUID.randomUUID();
        Space space = new Space(spaceId, height, width);
        spaceRepo.save(space);
        return spaceId;
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        try {
            UUID obstacleId = UUID.randomUUID();

            Space space = spaceRepo.findById(spaceId).orElseThrow(() -> new EntityNotFoundException());

            ObstacleEntity obstacleEntity = new ObstacleEntity(obstacleId,obstacle,space);
            space.addObstacle(obstacleEntity);

            obstacleRepo.save(obstacleEntity);
            spaceRepo.save(space);

        }
        catch (Error e) {
            System.out.println(e);
        }

        catch (NumberFormatException e) {
            System.out.println("Points must be a number");
        }
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        UUID transportCategoryId = UUID.randomUUID();
        TransportCategory transportCategory = new TransportCategory(transportCategoryId,category);
        transportCategoryRepo.save(transportCategory);
        return transportCategoryId;
    }

    /**
     * This method adds a traversable connection between two spaces based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate) {
        UUID connectionId = UUID.randomUUID();

        Space sourceSpace = spaceRepo.findById(sourceSpaceId).orElseThrow( () -> new EntityNotFoundException());
        Space destinationSpace = spaceRepo.findById(destinationSpaceId).orElseThrow( () -> new EntityNotFoundException());

        if(sourceCoordinate.getX() >= sourceSpace.getWidth() || sourceCoordinate.getY() >= sourceSpace.getHeight())
            throw new RuntimeException("source coordinates out of bounds");

        if(destinationCoordinate.getX() >= destinationSpace.getWidth() || destinationCoordinate.getY() >= destinationSpace.getHeight())
            throw new RuntimeException("destination coordinates out of bounds");

        Connection connection = new Connection(connectionId,sourceSpace,destinationSpace,sourceCoordinate,destinationCoordinate);
        connectionRepo.save(connection);


        return connectionId;
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        UUID cleaningDeviceId = UUID.randomUUID();


        CleaningDevice cleaningDevice = new CleaningDevice(cleaningDeviceId,name);
        cleaningDeviceRepo.save(cleaningDevice);


        return cleaningDeviceId;
    }

    /**
     * This method lets the cleaning device execute a task.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Task task) {
        switch (task.getTaskType()) {
            case NORTH: Integer stepsNorth = task.getNumberOfSteps();
                return executeNorth(cleaningDeviceId, stepsNorth);

            case EAST: Integer stepsEast = task.getNumberOfSteps();
                return executeEast(cleaningDeviceId, stepsEast);

            case SOUTH: Integer stepsSouth = task.getNumberOfSteps();
                return executeSouth(cleaningDeviceId, stepsSouth);

            case WEST: Integer stepsWest = task.getNumberOfSteps();
                return executeWest(cleaningDeviceId, stepsWest);

            case ENTER: UUID spaceId = task.getGridId();
                return executeInitialization(cleaningDeviceId,spaceId);

            case TRANSPORT: UUID spaceId2 = task.getGridId();
                return executeTransport(cleaningDeviceId,spaceId2);

            default:
                System.out.println("non valid Task");
                break;

        }

        return false;
    }

    private Boolean checkForNorthEastObstacles(UUID spaceId, Integer positionX, Integer positionY) {
        Space space = spaceRepo.initializeObstacles(spaceId);

        for(Obstacle obstacle : space.getObstacles()) {
            //Vertical
            if(obstacle.getStart().getX().equals(obstacle.getEnd().getX()) & !obstacle.getStart().getY().equals(obstacle.getEnd().getY())) {
                if(positionX.equals(obstacle.getStart().getX())) {
                    int sourceDestDiff = obstacle.getEnd().getY() - obstacle.getStart().getY();
                    for(int counter = obstacle.getStart().getY(); counter < sourceDestDiff ; counter++) {
                        if(counter == positionY) {
                            System.out.println("U have hit an obstacle");
                            return false;
                        }
                    }
                }

            }
            //Horizontal
            if(obstacle.getStart().getY().equals(obstacle.getEnd().getY()) & !obstacle.getStart().getX().equals(obstacle.getEnd().getX())) {
                if(positionY.equals(obstacle.getStart().getY())) {
                    int sourceDestDiff = obstacle.getEnd().getX() - obstacle.getStart().getX();
                    for(int counter = obstacle.getStart().getX(); counter < sourceDestDiff ; counter++) {
                        if(counter == positionX) {
                            System.out.println("U have hit an obstacle");
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private Boolean checkForSouthWestObstacles(UUID spaceId, Integer moveToX, Integer moveToY) {

        Space space = spaceRepo.initializeObstacles(spaceId);

        for(Obstacle obstacle : space.getObstacles()) {
            //Vertical
            if(obstacle.getStart().getX().equals(obstacle.getEnd().getX()) & !obstacle.getStart().getY().equals(obstacle.getEnd().getY())) {
                if(moveToX.equals(obstacle.getStart().getX()) || obstacle.getStart().getX().equals(moveToX)) {
                    int sourceDestDiff = obstacle.getEnd().getY() - obstacle.getStart().getY();
                    for(int counter = obstacle.getStart().getY(); counter < sourceDestDiff ; counter++) {
                        if(counter == moveToY) {
                            return false;
                        }
                    }
                }
            }
            //Horizontal
            if(obstacle.getStart().getY().equals(obstacle.getEnd().getY()) & !obstacle.getStart().getX().equals(obstacle.getEnd().getX())) {
                if(moveToY.equals(obstacle.getStart().getY()) || obstacle.getStart().getY().equals(moveToY)) {
                    int sourceDestDiff = obstacle.getEnd().getX() - obstacle.getStart().getX();
                    for(int counter = obstacle.getStart().getX(); counter < sourceDestDiff ; counter++) {
                        if(counter == moveToX) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private Boolean checkForOtherDevices(UUID spaceId, Integer positionX, Integer positionY) {

        Space space = spaceRepo.initializeCleaningDevices(spaceId);

        for(CleaningDevice cleaningDevice : space.getCleaningDevices()) {
            if(cleaningDevice.getLocationX().equals(positionX) && cleaningDevice.getLocationY().equals(positionY)) {
                return false;
            }
        }
        return true;
    }

    private Boolean executeTransport(UUID cleaningDeviceId, UUID spaceId) {
        try {

            List<Connection> connectionList = connectionRepo.findAll();
            CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow( () -> new EntityNotFoundException());


            for(Connection connection : connectionList) {
                if(connection.getDestinationSpace().getSpaceId().equals(spaceId) && connection.getSourceSpace().getSpaceId().equals(cleaningDevice.getSpace().getSpaceId())) {
                    if(getCleaningDeviceCoordinate(cleaningDeviceId).equals(connection.getSourceCoordinates())) {
                        setCoordinate(cleaningDeviceId, connection.getDestinationCoordinates(), connection.getDestinationSpace());
                        return true;
                    }
                }
            }
        }

        catch (NullPointerException e) {
            System.out.println("Cleaning Device or Space or Connection needs to be initialized");
        }
        return false;
    }

    private Boolean executeNorth(UUID cleaningDeviceId, Integer steps) {
        try {

            CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow( () -> new EntityNotFoundException());
           // Space space = spaceRepo.findById(cleaningDevice.getSpace().getSpaceId()).orElseThrow( () -> new EntityNotFoundException());
            Space space = spaceRepo.initializeObstacles(cleaningDevice.getSpace().getSpaceId());

            for(int counter = 0; counter < steps; counter++) {
                if(cleaningDevice.getLocationY() + 1 < space.getHeight()) {
                    if(checkForNorthEastObstacles(cleaningDevice.getSpace().getSpaceId(), cleaningDevice.getLocationX(), cleaningDevice.getLocationY() + 1)) {
                        if(checkForOtherDevices(cleaningDevice.getSpace().getSpaceId(),cleaningDevice.getLocationX() + 1, cleaningDevice.getLocationY())) {
                            cleaningDevice.setLocationY(cleaningDevice.getLocationY() + 1);
                            cleaningDeviceRepo.save(cleaningDevice);
                        }
                        else
                            return true;
                    }
                    else
                        throw new Error("hit Obstacle");

                }
                else
                    return true;
            }
        }

        catch (NullPointerException e) {
            System.out.println("Cleaning Device needs to be initialized");
        }

        catch (Error e) {
            System.out.println(e);
            return true;
        }

        return true;
    }

    private Boolean executeEast(UUID cleaningDeviceId, Integer steps) {
        try {
            CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow( () -> new EntityNotFoundException());
            Space space = spaceRepo.findById(cleaningDevice.getSpace().getSpaceId()).orElseThrow( () -> new EntityNotFoundException());

            for(int counter = 0; counter < steps; counter++) {
                if(cleaningDevice.getLocationX() + 1 < space.getWidth()) {
                    if(checkForNorthEastObstacles(cleaningDevice.getSpace().getSpaceId(), cleaningDevice.getLocationX() + 1, cleaningDevice.getLocationY())) {
                        if(checkForOtherDevices(cleaningDevice.getSpace().getSpaceId(),cleaningDevice.getLocationX() + 1, cleaningDevice.getLocationY())) {
                            cleaningDevice.setLocationX(cleaningDevice.getLocationX() + 1);
                            cleaningDeviceRepo.save(cleaningDevice);
                        }
                        else
                            return true;
                    }
                    else
                        return true;
                }
                else
                    return true;
            }
        }
        catch (NullPointerException e) {
            System.out.println("Cleaning Device needs to be initialized");
        }
        return true;
    }

    private Boolean executeSouth(UUID cleaningDeviceId, Integer steps) {
        try {

            CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow( () -> new EntityNotFoundException());
            Space space = spaceRepo.findById(cleaningDevice.getSpace().getSpaceId()).orElseThrow( () -> new EntityNotFoundException());

            for(int counter = 0; counter < steps; counter++) {
                if(cleaningDevice.getLocationY() - 1 >= 0) {
                    if(checkForSouthWestObstacles(space.getSpaceId(),cleaningDevice.getLocationX(), cleaningDevice.getLocationY() + 1)) {
                        if(checkForOtherDevices(space.getSpaceId(),cleaningDevice.getLocationX(), cleaningDevice.getLocationY() + 1)) {
                            cleaningDevice.setLocationY(cleaningDevice.getLocationY() - 1);
                            cleaningDeviceRepo.save(cleaningDevice);
                        }
                        else
                            return true;
                    }
                    else
                        return true;

                }
                else
                    return true;
            }
        }

        catch (NullPointerException e) {
            System.out.println("Cleaning Device needs to be initialized");
        }
        return true;
    }

    private Boolean executeWest(UUID cleaningDeviceId, Integer steps) {
        try {

            CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow( () -> new EntityNotFoundException());
            Space space = spaceRepo.findById(cleaningDevice.getSpace().getSpaceId()).orElseThrow( () -> new EntityNotFoundException());

            for(int counter = 0; counter < steps; counter++) {
                if(cleaningDevice.getLocationX() - 1 >= 0) {
                    if(checkForSouthWestObstacles(space.getSpaceId(), cleaningDevice.getLocationX() - 1, cleaningDevice.getLocationY())) {
                        if(checkForOtherDevices(space.getSpaceId(), cleaningDevice.getLocationX() - 1, cleaningDevice.getLocationY())) {
                            cleaningDevice.setLocationX(cleaningDevice.getLocationX() - 1);
                            cleaningDeviceRepo.save(cleaningDevice);
                        }
                        else
                            return true;
                    }
                    else
                        return true;
                }
                else
                    return true;
            }
        }
        catch (NullPointerException e) {
            System.out.println("Cleaning Device needs to be initialized");
        }

        return true;
    }

    private Boolean executeInitialization(UUID cleaningDeviceId, UUID spaceId) {

        Space space = spaceRepo.initializeCleaningDevices(spaceId);
        CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow( () -> new EntityNotFoundException());

        Coordinate spaceInitializationCoordinates = new Coordinate("(0,0)");

        for(CleaningDevice device : space.getCleaningDevices()) {
            if(getCleaningDeviceCoordinate(device.getCleaningDeviceId()).equals(spaceInitializationCoordinates)){
                return false;
            }
        }

        cleaningDevice.setSpace(space);
        cleaningDevice.setLocationX(0);
        cleaningDevice.setLocationY(0);

        space.addCleaningDevice(cleaningDevice);

        cleaningDeviceRepo.save(cleaningDevice);
        spaceRepo.save(space);

        return true;
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        UUID spaceId = UUID.randomUUID();

        CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow( () -> new EntityNotFoundException());

        return cleaningDevice.getSpace().getSpaceId();
    }

    /**
     * This method returns the coordinate a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordinate of the cleaning device
     */
    public Coordinate getCleaningDeviceCoordinate(UUID cleaningDeviceId){

        CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow( () -> new EntityNotFoundException());
        Coordinate coordinate = new Coordinate(cleaningDevice.getLocationX(),cleaningDevice.getLocationY());
        return coordinate;
    }

    public void setCoordinate(UUID cleaningDeviceId, Coordinate coordinates, Space destSpace) {

        CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow( () -> new EntityNotFoundException());
        Space space = destSpace;

        cleaningDevice.setLocationX(coordinates.getX());
        cleaningDevice.setLocationY(coordinates.getY());
        cleaningDevice.setSpace(space);

        cleaningDeviceRepo.save(cleaningDevice);
    }


}
