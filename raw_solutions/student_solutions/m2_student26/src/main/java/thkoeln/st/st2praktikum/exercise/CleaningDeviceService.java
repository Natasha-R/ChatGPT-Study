package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class CleaningDeviceService {


    private CoordianteGetterFromString coordianteGetter = new CoordianteGetterFromString();

    private SpaceStorage spaceStorage = new SpaceStorage();
    private ConnectionStorage connectionStorage = new ConnectionStorage();
    private CleaningDeviceStorage cleaningDeviceStorage = new CleaningDeviceStorage();





    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        spaceStorage.addObject(new Space(height, width));
        return spaceStorage.getObjectByIndex(spaceStorage.size()-1).getUuid();
    }

    /**
     * This method adds a wall to a given space.
     * @param spaceId the ID of the space the wall shall be placed on
     * @param wall the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID spaceId, Wall wall) {

        Space space = spaceStorage.getObject(spaceId);

        if(!(space.checkCoordinateWithinSpace(wall.getStart())) || !(space.checkCoordinateWithinSpace(wall.getEnd()))){
            throw new WrongFormatException("Coordinates must be within the Space");
        }

        spaceStorage.getObject(spaceId).getWalls().add(wall);
    }


    /**
     * This method adds a traversable connection between two spaces. Connections only work in one direction.
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate) {

        Space sourceSpace = spaceStorage.getObject(sourceSpaceId);
        Space destinationSpace = spaceStorage.getObject(destinationSpaceId);

        if(!(sourceSpace.checkCoordinateWithinSpace(sourceCoordinate)) || !(destinationSpace.checkCoordinateWithinSpace(destinationCoordinate))){
            throw new WrongFormatException("Coordinates must be within the Space");
        }

        connectionStorage.addObject(new Connection(sourceSpaceId,sourceCoordinate,destinationSpaceId,destinationCoordinate));
        return connectionStorage.getObjectByIndex(connectionStorage.size()-1).getUuid();


    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {

        cleaningDeviceStorage.addObject(new CleaningDevice(name));
        return cleaningDeviceStorage.getObjectByIndex(cleaningDeviceStorage.size()-1).getUuid();

    }

    /**
     * This method lets the cleaning device execute a command.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param command the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,space-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on gridcells with a connection to another space
     * "[en,<space-id>]" for setting the initial space where a cleaning device is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a wall or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Command command) {



        switch (command.getCommandType()){
            case TRANSPORT: return transportCleaningDevice(cleaningDeviceId, command);
            case ENTER: return spawnCleaningDevice(cleaningDeviceId, command);
            case NORTH: return moveCleaningDevice(cleaningDeviceId, command, "y", 1);
            case EAST: return moveCleaningDevice(cleaningDeviceId, command, "x", 1);
            case SOUTH: return moveCleaningDevice(cleaningDeviceId, command, "y", -1);
            case WEST: return moveCleaningDevice(cleaningDeviceId, command, "x", -1);
        }
        return false;
    }


    private Boolean moveCleaningDevice(UUID cleaningDeviceId, Command command, String movementAxis, int direction){

        int input_distance = command.getNumberOfSteps();
        int moveable_distance = checkCollisionwithWallForMoveableDistance(cleaningDeviceId, movementAxis, input_distance, direction);
        CleaningDevice tempCleaningdevice = cleaningDeviceStorage.getObject(cleaningDeviceId);

        if(movementAxis == "x"){
            cleaningDeviceStorage.getObject(cleaningDeviceId).setCoordiante(new Coordinate(moveable_distance,tempCleaningdevice.getCoordiante().getY()));
        }else if (movementAxis == "y"){
            cleaningDeviceStorage.getObject(cleaningDeviceId).setCoordiante(new Coordinate(tempCleaningdevice.getCoordiante().getX(),moveable_distance));
        }
        return true;
    }



    private int checkCollisionwithWallForMoveableDistance(UUID cleaningDeviceId, String movementAxis, int input_distance, int direction) {

        UUID tempSpace = cleaningDeviceStorage.getObject(cleaningDeviceId).getCurrentSpace();
        ArrayList<Wall> tempWalls = spaceStorage.getObject(tempSpace).getWalls();
        int x = cleaningDeviceStorage.getObject(cleaningDeviceId).getCoordiante().getX();
        int y = cleaningDeviceStorage.getObject(cleaningDeviceId).getCoordiante().getY();
        int currentspace_width = spaceStorage.getObject(cleaningDeviceStorage.getObject(cleaningDeviceId).getCurrentSpace()).getWidth()-1;
        int currentspace_height = spaceStorage.getObject(cleaningDeviceStorage.getObject(cleaningDeviceId).getCurrentSpace()).getHeight()-1;


        if ( movementAxis == "x") {
            return getMovableDistance(currentspace_width,tempWalls,x,y,direction*input_distance,"x");
        }

        else if (movementAxis == "y"){
            return getMovableDistance(currentspace_height,tempWalls,y,x,direction*input_distance,"y");
        }
        //Error
        return -1;
    }

    private int getMovableDistance(int currentspace_length, ArrayList<Wall>tempWalls, int movementCoordinate, int otherCoordiante, int direction_input_distance, String movementAxis){
        int temp_xy = movementCoordinate + direction_input_distance;

        for (Wall wall : tempWalls) {

            Coordinate segments[] = new Coordinate[2];
            segments[0] =wall.getStart();
            segments[1] =wall.getEnd();
            int wallLength1 = 0;
            int wallLength2 = 0;
            int wallPos1 = 0;

            if(movementAxis == "x"){
                wallLength1 = segments[0].getY();
                wallLength2 = segments[1].getY();
                wallPos1 = segments[0].getX();
            }else if(movementAxis == "y"){
                wallLength1 = segments[0].getX();
                wallLength2 = segments[1].getX();
                wallPos1 = segments[0].getY();
            }

            if (wallLength1 < otherCoordiante && otherCoordiante < wallLength2) {
                if (movementCoordinate < wallPos1 && wallPos1 < temp_xy) {
                    temp_xy = wallPos1 - 1;
                } else if (wallPos1 < movementCoordinate && temp_xy < wallPos1) {
                    temp_xy = wallPos1 + 1;
                }
            }
        }
        if (temp_xy > currentspace_length) {
            temp_xy = currentspace_length;
        } else if (temp_xy < 0) {
            temp_xy = 0;
        }
        return temp_xy;
    }


    private Boolean transportCleaningDevice(UUID cleaningDeviceId, Command command){
        UUID temp = command.getGridId();
        CleaningDevice tempCleaningDevice = cleaningDeviceStorage.getObject(cleaningDeviceId);
        ArrayList<Connection>connections = connectionStorage.getList();

        for (Connection element : connections){
            if (element.getDestinationSpaceId().equals(temp) && element.getSourceSpaceId().equals(this.getCleaningDeviceSpaceId(cleaningDeviceId))){
                return checkTransport(element.getSourceCoordinate(),element.getDestinationCoordinate(),element.getDestinationSpaceId(),cleaningDeviceId,tempCleaningDevice);
            }
            else if (element.getSourceSpaceId().equals(temp) && element.getDestinationSpaceId().equals(this.getCleaningDeviceSpaceId(cleaningDeviceId))){
                return checkTransport(element.getDestinationCoordinate(),element.getSourceCoordinate(),element.getSourceSpaceId(), cleaningDeviceId, tempCleaningDevice);
            }
        }
        return false;
    }

    private Boolean checkTransport(Coordinate coordiante1, Coordinate coordiante2, UUID spaceId, UUID cleaningDeviceId, CleaningDevice tempCleaningDevice){
        if(tempCleaningDevice.getCoordiante().getX() != coordiante1.getX()  && tempCleaningDevice.getCoordiante().getY() != coordiante1.getY()){
            return false;
        }
        if(checkAtPositionCleaningDevice(cleaningDeviceId,spaceId, coordiante2.getX(), coordiante2.getY())){
            return false;
        }

        cleaningDeviceStorage.getObject(cleaningDeviceId).setCurrentSpace(spaceId);
        cleaningDeviceStorage.getObject(cleaningDeviceId).setCoordiante(new Coordinate(coordiante2.getX(),coordiante2.getY()));
        return true;

    }

    private Boolean spawnCleaningDevice(UUID cleaningDeviceId, Command command){
        UUID temp = command.getGridId();
        if(checkAtPositionCleaningDevice(cleaningDeviceId, temp, 0,0)){
            return false;
        }
        cleaningDeviceStorage.getObject(cleaningDeviceId).setCurrentSpace(temp);
        cleaningDeviceStorage.getObject(cleaningDeviceId).setCoordiante(new Coordinate(0,0));
        return true;
    }

    private Boolean checkAtPositionCleaningDevice(UUID cleaningDeviceId, UUID spaceId, int posX, int posY){
        ArrayList<CleaningDevice> cleaningDevices = cleaningDeviceStorage.getList();
        for (CleaningDevice element: cleaningDevices){
            if(element.getCurrentSpace()!= null &&(!element.getUuid().equals(cleaningDeviceId) && element.getCurrentSpace().equals(spaceId) && element.getCoordiante().getX() == posX && element.getCoordiante().getY() == posY )){
                return true;
            }
        }
        return false;
    }


    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        return cleaningDeviceStorage.getObject(cleaningDeviceId).getCurrentSpace();
    }

    /**
     * This method returns the coordinates a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordiantes of the cleaning device encoded as a String, e.g. "(2,4)"
     */
    public Coordinate getCoordinate(UUID cleaningDeviceId){
        return cleaningDeviceStorage.getObject(cleaningDeviceId).getCoordiante();
    }



}
