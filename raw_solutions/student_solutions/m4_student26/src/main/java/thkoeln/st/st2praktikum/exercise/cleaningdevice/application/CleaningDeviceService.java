package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.WrongFormatException;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.space.application.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Connection;
import thkoeln.st.st2praktikum.exercise.space.domain.Wall;


import java.util.List;
import java.util.UUID;


@Service
public class CleaningDeviceService {

    @Autowired
    private CleaningDeviceRepository cleaningDeviceRepository;
    @Autowired
    private SpaceRepository spaceRepository;






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
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a wall or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Command command) {

        CleaningDevice tempCleaningdevice = cleaningDeviceRepository.findById(cleaningDeviceId).get();
        tempCleaningdevice.addCommand(command);
        cleaningDeviceRepository.save(tempCleaningdevice);

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
        System.out.println(tempCleaningdevice.getCoordinate());
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

            if (wallLength1 <= otherCoordiante && otherCoordiante < wallLength2) {
                if (movementCoordinate < wallPos1 && wallPos1 <= temp_xy) {
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
     * This method returns the coordinate a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordinate of the cleaning device
     */
    public Coordinate getCleaningDeviceCoordinate(UUID cleaningDeviceId){
        return cleaningDeviceRepository.findById(cleaningDeviceId).get().getCoordinate();
    }


    public CleaningDevice save (CleaningDevice cleaningDevice){
        return cleaningDeviceRepository.save(cleaningDevice);
    }

    public Iterable<CleaningDevice> findAll() {
        return cleaningDeviceRepository.findAll();
    }

    public CleaningDevice findById( UUID id ) {
        CleaningDevice cleaningDevice = cleaningDeviceRepository.findById( id ).orElseThrow( () ->
                new WrongFormatException( "No cleaningdevice with ID " + id + " can be found.") );
        return cleaningDevice;
    }

    public void deleteById( UUID id )  {
        CleaningDevice cleaningDevice = cleaningDeviceRepository.findById( id ).orElseThrow( () ->
                new WrongFormatException( "No cleaningdevice with ID " + id + " can be found.") );
        cleaningDeviceRepository.delete( cleaningDevice );
    }






}
