package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.StringParser.MovableDeviceCommandParser;
import thkoeln.st.st2praktikum.exercise.StringParser.ObstacleParser;
import thkoeln.st.st2praktikum.exercise.StringParser.PointParser;
import thkoeln.st.st2praktikum.exercise.World.Movable.*;
import thkoeln.st.st2praktikum.exercise.World.Movable.Command.CommandTypeEnum;
import thkoeln.st.st2praktikum.exercise.World.Obstacle.Obstacle;
import thkoeln.st.st2praktikum.exercise.World.Obstacle.ObstacleCollection;
import thkoeln.st.st2praktikum.exercise.World.Point;
import thkoeln.st.st2praktikum.exercise.World.Space.Space;
import thkoeln.st.st2praktikum.exercise.World.Space.SpaceCollection;

import java.util.UUID;
import java.util.regex.MatchResult;

public class CleaningDeviceService {
    // COLLECTION
    private final MovableCollection movableCollection = new MovableCollection();
    private final SpaceCollection spaceCollection = new SpaceCollection();

    // PARSER
    private final PointParser pointParser = new PointParser();
    private final ObstacleParser obstacleStringParser = new ObstacleParser();
    private final MovableDeviceCommandParser movableDeviceParser = new MovableDeviceCommandParser();

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space newSpace = new Space(height, width);
        this.spaceCollection.add(newSpace);

        return newSpace.getUUID();
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacleString the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     */
    public void addObstacle(UUID spaceId, String obstacleString) {
        Space demandedSpace = this.spaceCollection.findByUUID(spaceId);
        MatchResult matchResult = this.obstacleStringParser.parse(obstacleString);

        Point startingPoint = new Point(Integer.parseInt(matchResult.group(1)), Integer.parseInt(matchResult.group(2)));
        Point endPoint = new Point(Integer.parseInt(matchResult.group(3)), Integer.parseInt(matchResult.group(4)));

        if(startingPoint.getX() == endPoint.getX())
        {
            if(startingPoint.getY() > endPoint.getX())
            {
                Point tmp = startingPoint;
                startingPoint = endPoint;
                endPoint = tmp;
            }
        }


        if(startingPoint.getY() == endPoint.getY())
        {
            if(startingPoint.getX() > endPoint.getX())
            {
                Point tmp = startingPoint;
                startingPoint = endPoint;
                endPoint = tmp;
            }
        }

        Obstacle someObstacle = new Obstacle(
                startingPoint,
                endPoint
        );

        ObstacleCollection spaceObstacleCollection = demandedSpace.getObstacleCollection();
        spaceObstacleCollection.add(someObstacle);
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
        MatchResult pointParserResult;

        Space sourceSpace = this.spaceCollection.findByUUID(sourceSpaceId);
        pointParserResult = this.pointParser.parse(sourceCoordinate);
        Point sourcePoint = new Point(Integer.parseInt(pointParserResult.group(1)), Integer.parseInt(pointParserResult.group(2)));

        Space destinationSpace = this.spaceCollection.findByUUID(destinationSpaceId);
        pointParserResult = this.pointParser.parse(destinationCoordinate);
        Point destinationPoint = new Point(Integer.parseInt(pointParserResult.group(1)), Integer.parseInt(pointParserResult.group(2)));

        return sourceSpace.addConnectionToSpace(sourcePoint, destinationSpace, destinationPoint);
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice newCleaningDevice = new CleaningDevice(this, name);
        this.movableCollection.add(newCleaningDevice);

        return newCleaningDevice.getUUID();
    }

    /**
     * This method lets the cleaning device execute a command.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,space-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on gridcells with a connection to another space
     * "[en,<space-id>]" for setting the initial space where a cleaning device is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, String commandString) {
        IMovable movableDevice = this.movableCollection.findByUUID(cleaningDeviceId);

        MatchResult matchResult = this.movableDeviceParser.parse(commandString);

        CommandTypeEnum commandType;
        try {
            commandType = CommandTypeEnum.valueOf(matchResult.group(1));
        }
        catch (IllegalArgumentException e) {
            commandType = CommandTypeEnum.move;
        }

        switch (commandType)
        {
            case en: // initial space for cleaning device
                if(movableDevice.isPlaced())
                {
                    return false;
                }

                UUID targetSpaceUUID = UUID.fromString(matchResult.group(2));
                Space targetSpace = this.spaceCollection.findByUUID(targetSpaceUUID);

                if(this.movableCollection.isFieldBlocked(targetSpace, new Point(0, 0)))
                {
                    return false;
                }

                movableDevice.setSpace(targetSpace);

                break;
            case tr: // transport
                if(!(movableDevice instanceof ITransferable))
                {
                    return false;
                }

                System.out.println(String.format("Pos %s %s on space %s", movableDevice.getPosition().getX(), movableDevice.getPosition().getY(), movableDevice.getSpace().getUUID()));
                boolean transportResult = ((ITransferable) movableDevice).useSpaceConnection();

                System.out.println(String.format("Transport device %s result: %b new space %s", movableDevice.getUUID(), transportResult, movableDevice.getSpace().getUUID()));

                return transportResult;
            case move: // bewegen
                MovementDirectionEnum movementDirection = MovementDirectionEnum.valueOf(matchResult.group(1));
                int steps = Integer.parseInt(matchResult.group(2));

                System.out.println(String.format("Move by %d in %s for %s", steps, movementDirection, movableDevice.getUUID()));

                boolean result = movableDevice.move(movementDirection, steps);

                System.out.println(String.format("Move result %b", result));
                return result;
        }

        return true;
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId) {
        IMovable movable = this.movableCollection.findByUUID(cleaningDeviceId);
        Space movableSpace = movable.getSpace();

        if(null == movableSpace)
        {
            return null;
        }

        return movableSpace.getUUID();
    }

    /**
     * This method returns the coordinates a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordiantes of the cleaning device encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID cleaningDeviceId){
        System.out.println(String.format("Device %s coordinates %s", cleaningDeviceId, this.movableCollection.findByUUID(cleaningDeviceId).getPosition().toString()));
        return this.movableCollection.findByUUID(cleaningDeviceId).getPosition().toString();
    }

    public MovableCollection getMovableCollection() {
        return movableCollection;
    }
}
