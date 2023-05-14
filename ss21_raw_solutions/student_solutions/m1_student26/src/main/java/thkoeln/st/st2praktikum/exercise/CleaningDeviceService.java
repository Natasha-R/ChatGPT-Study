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
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID spaceId, String wallString) {
        spaceStorage.getObject(spaceId).getWalls().add(new Wall(wallString));
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
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,space-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on gridcells with a connection to another space
     * "[en,<space-id>]" for setting the initial space where a cleaning device is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a wall or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, String commandString) {



        switch (commandString.substring(1,3)){
            case "tr": return transportCleaningDevice(cleaningDeviceId, commandString);
            case "en": return spawnCleaningDevice(cleaningDeviceId, commandString);
            case "no": return moveCleaningDevice(cleaningDeviceId, commandString, "y", 1);
            case "ea": return moveCleaningDevice(cleaningDeviceId, commandString, "x", 1);
            case "so": return moveCleaningDevice(cleaningDeviceId, commandString, "y", -1);
            case "we": return moveCleaningDevice(cleaningDeviceId, commandString, "x", -1);
        }
        return false;
    }


    private Boolean moveCleaningDevice(UUID cleaningDeviceId, String commandString, String movementAxis, int direction){

        int input_distance = Integer.parseInt(commandString.substring(4).replace(']', ' ').trim());
        int moveable_distance = checkCollisionwithWallForMoveableDistance(cleaningDeviceId, movementAxis, input_distance, direction);

        if(movementAxis == "x"){
            cleaningDeviceStorage.getObject(cleaningDeviceId).setX(moveable_distance);
        }else if (movementAxis == "y"){
            cleaningDeviceStorage.getObject(cleaningDeviceId).setY(moveable_distance);
        }
        return true;
    }



    private int checkCollisionwithWallForMoveableDistance(UUID cleaningDeviceId, String movementAxis, int input_distance, int direction) {

        UUID tempSpace = cleaningDeviceStorage.getObject(cleaningDeviceId).getCurrentSpace();
        ArrayList<Wall> tempWalls = spaceStorage.getObject(tempSpace).getWalls();
        int x = cleaningDeviceStorage.getObject(cleaningDeviceId).getX();
        int y = cleaningDeviceStorage.getObject(cleaningDeviceId).getY();
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

            String segments[] = wall.getWallstring().split("-");
            int wallLength1 = 0;
            int wallLength2 = 0;
            int wallPos1 = 0;

            if(movementAxis == "x"){
                wallLength1 = coordianteGetter.getYfromString(segments[0]);
                wallLength2 = coordianteGetter.getYfromString(segments[1]);
                wallPos1 = coordianteGetter.getXfromString(segments[0]);
            }else if(movementAxis == "y"){
                wallLength1 = coordianteGetter.getXfromString(segments[0]);
                wallLength2 = coordianteGetter.getXfromString(segments[1]);
                wallPos1 = coordianteGetter.getYfromString(segments[0]);
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


    private Boolean transportCleaningDevice(UUID cleaningDeviceId, String commandString){
        UUID temp = UUID.fromString(commandString.substring(4, commandString.length()-1));
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

    private Boolean checkTransport(String Coordiante1, String Coordiante2, UUID SpaceId, UUID cleaningDeviceId, CleaningDevice tempCleaningDevice){
        if(tempCleaningDevice.getX() != coordianteGetter.getXfromString(Coordiante1) && tempCleaningDevice.getY() != coordianteGetter.getYfromString(Coordiante1)){
            return false;
        }
        cleaningDeviceStorage.getObject(cleaningDeviceId).setCurrentSpace(SpaceId);
        cleaningDeviceStorage.getObject(cleaningDeviceId).setX(coordianteGetter.getXfromString(Coordiante2));
        cleaningDeviceStorage.getObject(cleaningDeviceId).setY(coordianteGetter.getYfromString(Coordiante2));
        return true;

    }

    private Boolean spawnCleaningDevice(UUID cleaningDeviceId, String commandString){
        UUID temp = UUID.fromString(commandString.substring(4, commandString.length()-1));
        if(checkAtPositionCleaningDevice(cleaningDeviceId, temp, 0,0)){
            return false;
        }
        cleaningDeviceStorage.getObject(cleaningDeviceId).setCurrentSpace(temp);
        cleaningDeviceStorage.getObject(cleaningDeviceId).setX(0);
        cleaningDeviceStorage.getObject(cleaningDeviceId).setY(0);
        return true;
    }

    private Boolean checkAtPositionCleaningDevice(UUID cleaningDeviceId, UUID spaceId, int posX, int posY){
        ArrayList<CleaningDevice> cleaningDevices = cleaningDeviceStorage.getList();
        for (CleaningDevice element: cleaningDevices){
            if(element.getCurrentSpace()!= null &&(!element.getUuid().equals(cleaningDeviceId) && element.getCurrentSpace().equals(spaceId) && element.getX() == posX && element.getY() == posY )){
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
    public String getCoordinates(UUID cleaningDeviceId){
        return ("(" + cleaningDeviceStorage.getObject(cleaningDeviceId).getX() + "," + cleaningDeviceStorage.getObject(cleaningDeviceId).getY() + ")");
    }



}
