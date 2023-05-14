package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import thkoeln.st.st2praktikum.ProjectApplication;
import thkoeln.st.st2praktikum.exercise.entity.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.entity.Connection;
import thkoeln.st.st2praktikum.exercise.entity.Obstacle;
import thkoeln.st.st2praktikum.exercise.entity.Space;
import thkoeln.st.st2praktikum.exercise.repository.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.repository.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.repository.ObstacleRepository;
import thkoeln.st.st2praktikum.exercise.repository.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.service.ConnectionService;
import thkoeln.st.st2praktikum.exercise.service.DeviceService;
import thkoeln.st.st2praktikum.exercise.service.ObstacleService;
import thkoeln.st.st2praktikum.exercise.service.SpaceService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;


@Configurable
public class CleaningDeviceService {


    private SpaceRepository getSpaceRepository() {
        ApplicationContext context = SpringContext.getAppContext();
        SpaceService service = (SpaceService) context.getBean("spaceService");
        return service.getSpaceRepository();
    }

    private CleaningDeviceRepository getCleaningDeviceRepository() {
        ApplicationContext context = SpringContext.getAppContext();
        DeviceService service = (DeviceService) context.getBean("deviceService");
        return service.getCleaningDeviceRepository();
    }

    private ObstacleRepository getObstacleRepository() {
        ApplicationContext context = SpringContext.getAppContext();
        ObstacleService service = (ObstacleService) context.getBean("obstacleService");
        return service.getObstacleRepository();
    }

    public ConnectionRepository getConnectionRepository() {
        ApplicationContext context = SpringContext.getAppContext();
        ConnectionService service = (ConnectionService) context.getBean("connectionService");
        return service.getConnectionRepository();
    }

    public CleaningDeviceService() {
        try {
            ApplicationContext context = SpringContext.getAppContext();
            SpaceService service = (SpaceService) context.getBean("spaceService");
        }
        catch (NullPointerException e) {
            ProjectApplication projectApplication;
            projectApplication = new ProjectApplication();
            projectApplication.main(new String[]{});
        }
    }

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */


    public UUID addSpace(Integer height, Integer width) {
        UUID spaceId = UUID.randomUUID();
        SpaceRepository spaceRepo = getSpaceRepository();
        Space space = new Space(spaceId, height, width);
        spaceRepo.save(space);
        return spaceId;
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacleString the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     */
    public void addObstacle(UUID spaceId, String obstacleString) {
        try {
            UUID obstacleId = UUID.randomUUID();

            ObstacleRepository obstacleRepo = getObstacleRepository();
            SpaceRepository spaceRepo = getSpaceRepository();

            if(obstacleString.split("-").length != 2)
                throw new Error("command needs to be seperated with '-'");

            String[] sourceDestinationSplit = obstacleString.split("-");

            if(sourceDestinationSplit[0].split(",").length != 2)
                throw new Error("left 2 numbers must be seperated with ',");

            String[] sourceXYSplit = sourceDestinationSplit[0].split(",");

            if(!sourceXYSplit[0].startsWith("("))
                throw new Error("left command must start with '('");

            Integer sourceX = Integer.parseInt(sourceXYSplit[0].substring(1));

            if(!sourceXYSplit[1].endsWith(")"))
                throw new Error("left command must end with ')'");

            Integer sourceY = Integer.parseInt(sourceXYSplit[1].substring(0, sourceXYSplit[1].length() - 1));

            if(sourceDestinationSplit[1].split(",").length != 2) {
                throw new Error("right 2 numbers must be seperated with ','");
            }
            String[] destXYSplit = sourceDestinationSplit[1].split(",");

            if(!destXYSplit[0].startsWith("("))
                throw new Error("Right command must start with '('");

            Integer destX = Integer.parseInt(destXYSplit[0].substring(1));

            if(!destXYSplit[1].endsWith(")"))
                throw new Error("Right command must end with ')'");

            Integer destY = Integer.parseInt(destXYSplit[1].substring(0, 1));

            Space space = spaceRepo.findById(spaceId).orElseThrow(() -> new EntityNotFoundException());

            Obstacle obstacle = new Obstacle(obstacleId, sourceX, sourceY, destX, destY, space);
            obstacleRepo.save(obstacle);
        }
        catch (Error e) {
            System.out.println(e);
        }

        catch (NumberFormatException e) {
            System.out.println("Points must be a number");
        }

    }

    /**
     * This method adds a traversable connection between two spaces. Connections only work in one direction.
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate) {
        UUID connectionId = UUID.randomUUID();

        ConnectionRepository connectionRepo = getConnectionRepository();
        SpaceRepository spaceRepo = getSpaceRepository();

        Space sourceSpace = spaceRepo.findById(sourceSpaceId).orElseThrow( () -> new EntityNotFoundException());
        Space destinationSpace = spaceRepo.findById(destinationSpaceId).orElseThrow( () -> new EntityNotFoundException());

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

        CleaningDeviceRepository cleaningDeviceRepo = getCleaningDeviceRepository();

        CleaningDevice cleaningDevice = new CleaningDevice(cleaningDeviceId,name);
        cleaningDeviceRepo.save(cleaningDevice);


        return cleaningDeviceId;
    }

    /**
     * This method lets the cleaning device execute a command.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,space-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on squares with a connection to another space
     * "[en,<space-id>]" for setting the initial space where a cleaning device is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, String commandString) {
        try {
            if(commandString.split(",").length != 2)
                throw new Error("command must be divided by ','");

            String[] commandSplit = commandString.split(",");

            String leftCommand = commandSplit[0].substring(1);

            if(!commandSplit[0].startsWith("["))
                throw new Error("command must start with '['");

            if(!commandSplit[1].endsWith("]"))
                throw new Error("command must start with ']'");

            switch (leftCommand) {
                case "no": Integer stepsNorth = Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1));
                    return executeNorth(cleaningDeviceId, stepsNorth);

                case "ea": Integer stepsEast = Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1));
                    return executeEast(cleaningDeviceId, stepsEast);

                case "so": Integer stepsSouth = Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1));
                    return executeSouth(cleaningDeviceId, stepsSouth);

                case "we": Integer stepsWest = Integer.parseInt(commandSplit[1].substring(0,commandSplit[1].length() - 1));
                    return executeWest(cleaningDeviceId, stepsWest);

                case "en": UUID spaceId = UUID.fromString(commandSplit[1].substring(0,commandSplit[1].length() - 1));
                    return executeInitialization(cleaningDeviceId,spaceId);

                case "tr": UUID spaceId2 = UUID.fromString(commandSplit[1].substring(0,commandSplit[1].length() - 1));
                    return executeTransport(cleaningDeviceId,spaceId2);

                default: System.out.println("non valid left command input");

            }
        }
        catch (Error e) {
            System.out.println(e);
        }

        catch (NumberFormatException e) {
            System.out.println("wrong input type");
        }

        return false;
    }


    private Boolean checkForNorthEastObstacles(UUID spaceId, Integer positionX, Integer positionY) {
        SpaceRepository spaceRepo = getSpaceRepository();

        Space space = spaceRepo.initializeObstacles(spaceId);

        for(Obstacle obstacle : space.getObstacles()) {
            //Vertical
            if(obstacle.getSourceX().equals(obstacle.getDestX()) & !obstacle.getSourceY().equals(obstacle.getDestY())) {
                if(positionX.equals(obstacle.getSourceX())) {
                    int sourceDestDiff = obstacle.getDestY() - obstacle.getSourceY();
                    for(int counter = obstacle.getSourceY(); counter < sourceDestDiff ; counter++) {
                        if(counter == positionY) {
                            System.out.println("U have hit an obstacle");
                            return false;
                        }
                    }
                }

            }
            //Horizontal
            if(obstacle.getSourceY().equals(obstacle.getDestY()) & !obstacle.getSourceX().equals(obstacle.getDestX())) {
                if(positionY.equals(obstacle.getSourceY())) {
                    int sourceDestDiff = obstacle.getDestX() - obstacle.getSourceX();
                    for(int counter = obstacle.getSourceX(); counter < sourceDestDiff ; counter++) {
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
        SpaceRepository spaceRepo = getSpaceRepository();

        Space space = spaceRepo.initializeObstacles(spaceId);

        for(Obstacle obstacle : space.getObstacles()) {
            //Vertical
            if(obstacle.getSourceX().equals(obstacle.getDestX()) & !obstacle.getSourceY().equals(obstacle.getDestY())) {
                if(moveToX.equals(obstacle.getSourceX()) || obstacle.getSourceX().equals(moveToX)) {
                    int sourceDestDiff = obstacle.getDestY() - obstacle.getSourceY();
                    for(int counter = obstacle.getSourceY(); counter < sourceDestDiff ; counter++) {
                        if(counter == moveToY) {
                            return false;
                        }
                    }
                }
            }
            //Horizontal
            if(obstacle.getSourceY().equals(obstacle.getDestY()) & !obstacle.getSourceX().equals(obstacle.getDestX())) {
                if(moveToY.equals(obstacle.getSourceY()) || obstacle.getSourceY().equals(moveToY)) {
                    int sourceDestDiff = obstacle.getDestX() - obstacle.getSourceX();
                    for(int counter = obstacle.getSourceX(); counter < sourceDestDiff ; counter++) {
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
        SpaceRepository spaceRepo = getSpaceRepository();

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
            CleaningDeviceRepository cleaningDeviceRepo = getCleaningDeviceRepository();
            SpaceRepository spaceRepo = getSpaceRepository();
            ConnectionRepository connectionRepo = getConnectionRepository();

            List<Connection> connectionList = connectionRepo.findAll();
            CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow( () -> new EntityNotFoundException());


            for(Connection connection : connectionList) {
                if(connection.getDestinationSpace().getSpaceId().equals(spaceId) && connection.getSourceSpace().getSpaceId().equals(cleaningDevice.getSpace().getSpaceId())) {
                    if(getCoordinates(cleaningDeviceId).equals(connection.getSourceCoordinates())) {
                        setCoordinates(cleaningDeviceId, connection.getDestinationCoordinates(), connection.getDestinationSpace());
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
            CleaningDeviceRepository cleaningDeviceRepo = getCleaningDeviceRepository();
            SpaceRepository spaceRepo = getSpaceRepository();

            CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow( () -> new EntityNotFoundException());
            Space space = spaceRepo.findById(cleaningDevice.getSpace().getSpaceId()).orElseThrow( () -> new EntityNotFoundException());

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


    private Boolean executeEast(UUID cleaningDeviceId, Integer steps) {
        try {
            CleaningDeviceRepository cleaningDeviceRepo = getCleaningDeviceRepository();
            SpaceRepository spaceRepo = getSpaceRepository();

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
            CleaningDeviceRepository cleaningDeviceRepo = getCleaningDeviceRepository();
            SpaceRepository spaceRepo = getSpaceRepository();

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
            CleaningDeviceRepository cleaningDeviceRepo = getCleaningDeviceRepository();
            SpaceRepository spaceRepo = getSpaceRepository();

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
        SpaceRepository spaceRepo = getSpaceRepository();
        CleaningDeviceRepository cleaningDeviceRepo = getCleaningDeviceRepository();

        Space space = spaceRepo.initializeCleaningDevices(spaceId);
        CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow( () -> new EntityNotFoundException());

        for(CleaningDevice device : space.getCleaningDevices()) {
            if(getCoordinates(device.getCleaningDeviceId()).equals("(0,0)")){
                return false;
            }
        }

        cleaningDevice.setSpace(space);
        cleaningDevice.setLocationX(0);
        cleaningDevice.setLocationY(0);

        cleaningDeviceRepo.save(cleaningDevice);

        return true;
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        UUID spaceId = UUID.randomUUID();

        CleaningDeviceRepository cleaningDeviceRepo = getCleaningDeviceRepository();

        CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow( () -> new EntityNotFoundException());

        return cleaningDevice.getSpace().getSpaceId();
    }

    /**
     * This method returns the coordinates a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordiantes of the cleaning device encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID cleaningDeviceId){
        CleaningDeviceRepository cleaningDeviceRepo = getCleaningDeviceRepository();

        CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow( () -> new EntityNotFoundException());

        return "(" + cleaningDevice.getLocationX() + "," + cleaningDevice.getLocationY() + ")";
    }

    public void setCoordinates(UUID cleaningDeviceId, String coordinates, Space destSpace) {
        CleaningDeviceRepository cleaningDeviceRepo = getCleaningDeviceRepository();

        CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow( () -> new EntityNotFoundException());
        Space space = destSpace;

        String[] coordinatesSplit = coordinates.split(",");

        Integer destLocationX = Integer.parseInt(coordinatesSplit[0].substring(1));
        Integer destLocationY = Integer.parseInt(coordinatesSplit[1].substring(0,coordinatesSplit[1].length() - 1));

        cleaningDevice.setLocationX(destLocationX);
        cleaningDevice.setLocationY(destLocationY);
        cleaningDevice.setSpace(space);

        cleaningDeviceRepo.save(cleaningDevice);
    }



}
