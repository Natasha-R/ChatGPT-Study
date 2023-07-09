package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CleaningDeviceService {


    private CoordianteGetterFromString coordianteGetter = new CoordianteGetterFromString();

    @Autowired
    private SpaceRepository spaceRepository;
    @Autowired
    private CleaningDeviceRepository cleaningDeviceRepository;





    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space tempspace = new Space(height, width);
        spaceRepository.save( tempspace );
        return tempspace.getUuid();
    }

    /**
     * This method adds a wall to a given space.
     * @param spaceId the ID of the space the wall shall be placed on
     * @param wall the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID spaceId, Wall wall) {

        Space space = spaceRepository.findById(spaceId).get();

        if(!(space.checkCoordinateWithinSpace(wall.getStart())) || !(space.checkCoordinateWithinSpace(wall.getEnd()))){
            throw new WrongFormatException("Coordinates must be within the Space");
        }

        space.getWalls().add(wall);
        spaceRepository.save(space);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        return UUID.randomUUID();
    }



    /**
     * This method adds a traversable connection between two spaces based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate) {

        Space sourceSpace = spaceRepository.findById(sourceSpaceId).get();
        Space destinationSpace = spaceRepository.findById(destinationSpaceId).get();

        if(!(sourceSpace.checkCoordinateWithinSpace(sourceCoordinate)) || !(destinationSpace.checkCoordinateWithinSpace(destinationCoordinate))){
            throw new WrongFormatException("Coordinates must be within the Space");
        }

        Connection temp = new Connection(sourceSpaceId,sourceCoordinate,destinationSpaceId,destinationCoordinate);
        sourceSpace.getConnections().add(temp);
        spaceRepository.save(sourceSpace);
        return temp.getUuid();


    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {

        CleaningDevice temp = new CleaningDevice(name);
        cleaningDeviceRepository.save(temp);
        return temp.getUuid();

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
        CleaningDevice tempCleaningdevice = cleaningDeviceRepository.findById(cleaningDeviceId).get();

        if(movementAxis == "x"){
            tempCleaningdevice.setCoordinate(new Coordinate(moveable_distance,tempCleaningdevice.getCoordinate().getY()));
        }else if (movementAxis == "y"){
            tempCleaningdevice.setCoordinate(new Coordinate(tempCleaningdevice.getCoordinate().getX(),moveable_distance));
        }
        cleaningDeviceRepository.save(tempCleaningdevice);
        return true;
    }



    private int checkCollisionwithWallForMoveableDistance(UUID cleaningDeviceId, String movementAxis, int input_distance, int direction) {

        CleaningDevice tempcleaningdevice = cleaningDeviceRepository.findById(cleaningDeviceId).get();
        UUID tempSpace = tempcleaningdevice.getSpaceId();
        List<Wall> tempWalls = spaceRepository.findById(tempSpace).get().getWalls();
        int x = tempcleaningdevice.getCoordinate().getX();
        int y = tempcleaningdevice.getCoordinate().getY();
        int currentspace_width = spaceRepository.findById(tempSpace).get().getWidth()-1;
        int currentspace_height = spaceRepository.findById(tempSpace).get().getHeight()-1;


        if ( movementAxis == "x") {
            return getMovableDistance(currentspace_width,tempWalls,x,y,direction*input_distance,"x");
        }

        else if (movementAxis == "y"){
            return getMovableDistance(currentspace_height,tempWalls,y,x,direction*input_distance,"y");
        }
        //Error
        return -1;
    }

    private int getMovableDistance(int currentspace_length, List<Wall>tempWalls, int movementCoordinate, int otherCoordiante, int direction_input_distance, String movementAxis){
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
        CleaningDevice tempCleaningDevice = cleaningDeviceRepository.findById(cleaningDeviceId).get();
        UUID tempspace = tempCleaningDevice.getSpaceId();

        for (Connection element : spaceRepository.findById(tempspace).get().getConnections()){
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
        if(tempCleaningDevice.getCoordinate().getX() != coordiante1.getX()  && tempCleaningDevice.getCoordinate().getY() != coordiante1.getY()){
            return false;
        }
        if(checkAtPositionCleaningDevice(cleaningDeviceId,spaceId, coordiante2.getX(), coordiante2.getY())){
            return false;
        }


        tempCleaningDevice.setSpaceId(spaceId);
        tempCleaningDevice.setCoordinate(new Coordinate(coordiante2.getX(),coordiante2.getY()));
        cleaningDeviceRepository.save(tempCleaningDevice);
        return true;

    }

    private Boolean spawnCleaningDevice(UUID cleaningDeviceId, Command command){
        UUID temp = command.getGridId();
        if(checkAtPositionCleaningDevice(cleaningDeviceId, temp, 0,0)){
            return false;
        }
        CleaningDevice tempcleaningdevice = cleaningDeviceRepository.findById(cleaningDeviceId).get();

        tempcleaningdevice.setSpaceId(temp);
        tempcleaningdevice.setCoordinate(new Coordinate(0,0));
        cleaningDeviceRepository.save(tempcleaningdevice);
        return true;
    }

    private Boolean checkAtPositionCleaningDevice(UUID cleaningDeviceId, UUID spaceId, int posX, int posY){
        for (CleaningDevice element: cleaningDeviceRepository.findAll()){
            if(element.getSpaceId()!= null &&(!element.getUuid().equals(cleaningDeviceId) && element.getSpaceId().equals(spaceId) && element.getCoordinate().getX() == posX && element.getCoordinate().getY() == posY )){
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


        return cleaningDeviceRepository.findById(cleaningDeviceId).get().getSpaceId();
    }

    /**
     * This method returns the coordinates a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordiantes of the cleaning device encoded as a String, e.g. "(2,4)"
     */
    public Coordinate getCleaningDeviceCoordinate(UUID cleaningDeviceId){
        return cleaningDeviceRepository.findById(cleaningDeviceId).get().getCoordinate();
    }



}
