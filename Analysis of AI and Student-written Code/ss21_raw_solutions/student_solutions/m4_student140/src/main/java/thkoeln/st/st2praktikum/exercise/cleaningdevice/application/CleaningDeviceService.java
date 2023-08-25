package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.space.domain.Connection;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.repository.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.repository.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.repository.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Obstacle;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;


@Service
public class CleaningDeviceService {

    @Autowired
    protected SpaceRepository spaceRepo;

    @Autowired
    protected CleaningDeviceRepository cleaningDeviceRepo;

    @Autowired
    protected ConnectionRepository connectionRepo;
    
    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {

        CleaningDevice cleaningDevice = new CleaningDevice( name );

        cleaningDeviceRepo.save( cleaningDevice );

        return cleaningDevice.getCleaningDeviceId();
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
        CleaningDevice newCleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId).orElseThrow(() -> new EntityNotFoundException());



        switch (task.getTaskType()) {
            case NORTH: newCleaningDevice.setTasks( task ); cleaningDeviceRepo.save( newCleaningDevice );
                return executeNorth(cleaningDeviceId, task.getNumberOfSteps());

            case EAST: newCleaningDevice.setTasks( task ); cleaningDeviceRepo.save( newCleaningDevice );
                return executeEast( cleaningDeviceId, task.getNumberOfSteps() );

            case SOUTH:
                return executeSouth( cleaningDeviceId, task.getNumberOfSteps() );

            case WEST:
                return executeWest( cleaningDeviceId, task.getNumberOfSteps() );

            case ENTER: newCleaningDevice.setTasks( task ); cleaningDeviceRepo.save( newCleaningDevice );
                return executeInitialization( cleaningDeviceId , task.getGridId() );

            case TRANSPORT:
                return executeTransport( cleaningDeviceId,task.getGridId() );

            default:
                System.out.println("non valid Task");
                break;
        }

        return false;
    }

    private Boolean executeNorth(UUID cleaningDeviceId, Integer steps) {
        try {
            CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId)
                    .orElseThrow( () -> new EntityNotFoundException() );
            Space space = spaceRepo.findById( cleaningDevice.getSpace().getSpaceId() )
                    .orElseThrow( () -> new EntityNotFoundException() );
            UUID spaceId = space.getSpaceId();

            for(int counter = 0; counter < steps; counter++) {
                Integer deviceLocationX = cleaningDevice.getLocationX();
                Integer deviceLocationY = cleaningDevice.getLocationY();

                if( deviceLocationY + 1 < space.getHeight() ) {
                    if( checkForNorthEastObstacles( spaceId, deviceLocationX , deviceLocationY + 1 ) ) {
                        if( checkForOtherDevices (spaceId ,deviceLocationX + 1, deviceLocationY ) ) {
                            cleaningDevice.setLocationY( deviceLocationY + 1 );

                            cleaningDeviceRepo.save( cleaningDevice );

                        } else return true;
                    } else return true;
                } else return true;
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
            CleaningDevice cleaningDevice = cleaningDeviceRepo.findById( cleaningDeviceId )
                    .orElseThrow( () -> new EntityNotFoundException() );
            Space space = spaceRepo.findById( cleaningDevice.getSpace().getSpaceId() )
                    .orElseThrow( () -> new EntityNotFoundException() );
            UUID spaceId = space.getSpaceId();

            for( int counter = 0 ; counter < steps ; counter++ ) {
                Integer deviceLocationX = cleaningDevice.getLocationX();
                Integer deviceLocationY = cleaningDevice.getLocationY();

                if( deviceLocationX + 1 < space.getWidth() ) {
                    if( checkForNorthEastObstacles( spaceId , deviceLocationX + 1, deviceLocationY ) ) {
                        if(checkForOtherDevices( spaceId ,deviceLocationX + 1, deviceLocationY ) ) {
                            cleaningDevice.setLocationX( deviceLocationX + 1 );
                            cleaningDeviceRepo.save(cleaningDevice);
                        }
                        else return true;
                    }
                    else return true;
                }
                else return true;
            }
        }
        catch ( NullPointerException e ) {
            System.out.println("Cleaning Device needs to be initialized");
        }
        return true;
    }

    private Boolean executeSouth(UUID cleaningDeviceId, Integer steps) {
        try {
            CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId)
                    .orElseThrow( () -> new EntityNotFoundException() );
            Space space = spaceRepo.findById(cleaningDevice.getSpace().getSpaceId())
                    .orElseThrow( () -> new EntityNotFoundException() );
            UUID spaceId = space.getSpaceId();

            for( int counter = 0; counter < steps; counter++ ) {
                Integer deviceLocationX = cleaningDevice.getLocationX();
                Integer deviceLocationY = cleaningDevice.getLocationY();

                if( deviceLocationY - 1 >= 0) {
                    if( checkForSouthWestObstacles( spaceId , deviceLocationX , deviceLocationY + 1 ) ) {
                        if( checkForOtherDevices( spaceId , deviceLocationX , deviceLocationY + 1 ) ) {

                            cleaningDevice.setLocationY( deviceLocationY - 1 );
                            cleaningDeviceRepo.save( cleaningDevice );

                        } else return true;
                    } else return true;
                } else return true;
            }
        }
        catch (NullPointerException e) {
            System.out.println("Cleaning Device needs to be initialized");
        }
        return true;
    }

    private Boolean executeWest(UUID cleaningDeviceId, Integer steps) {
        try {
            CleaningDevice cleaningDevice = cleaningDeviceRepo.findById( cleaningDeviceId )
                    .orElseThrow( () -> new EntityNotFoundException() );
            Space space = spaceRepo.findById( cleaningDevice.getSpace().getSpaceId() )
                    .orElseThrow( () -> new EntityNotFoundException() );
            UUID spaceId = space.getSpaceId();

            for(int counter = 0; counter < steps; counter++) {
                Integer deviceLocationX = cleaningDevice.getLocationX();
                Integer deviceLocationY = cleaningDevice.getLocationY();

                if(deviceLocationX - 1 >= 0) {
                    if(checkForSouthWestObstacles( spaceId , deviceLocationX - 1 , deviceLocationY ) ) {
                        if(checkForOtherDevices( spaceId , deviceLocationX - 1 , deviceLocationY ) ) {

                            cleaningDevice.setLocationX( deviceLocationX - 1 );
                            cleaningDeviceRepo.save( cleaningDevice );

                        } else return true;
                    } else return true;
                } else return true;
            }
        }
        catch (NullPointerException e) {
            System.out.println("Cleaning Device needs to be initialized");
        }
        return true;
    }

    private Boolean executeInitialization(UUID cleaningDeviceId, UUID spaceId) {

        Space space = spaceRepo.initializeCleaningDevices(spaceId);
        CleaningDevice cleaningDevice = cleaningDeviceRepo.findById(cleaningDeviceId)
                .orElseThrow( () -> new EntityNotFoundException());

        for( CleaningDevice device : space.getCleaningDevices() ) {
            if( getCleaningDeviceCoordinate( device.getCleaningDeviceId() ).equals( new Coordinate("(0,0)") ) )
                return false;
        }

        cleaningDevice.setSpace( space );
        cleaningDevice.setLocationX( 0 );
        cleaningDevice.setLocationY( 0 );

        space.addCleaningDevice( cleaningDevice );

        cleaningDeviceRepo.save( cleaningDevice );
        spaceRepo.save( space );

        return true;
    }

    private Boolean executeTransport(UUID cleaningDeviceId, UUID spaceId) {
        try {

            List<Connection> connectionList = connectionRepo.findAll();
            CleaningDevice cleaningDevice = cleaningDeviceRepo.findById( cleaningDeviceId )
                    .orElseThrow( () -> new EntityNotFoundException());


            for( Connection connection : connectionList ) {

                UUID sourceSpaceId = connection.getSourceSpace().getSpaceId();
                UUID destinationSpaceId = connection.getDestinationSpace().getSpaceId();

                if( destinationSpaceId.equals( spaceId ) && sourceSpaceId.equals( cleaningDevice.getSpace().getSpaceId() ) ) {
                    if( getCleaningDeviceCoordinate( cleaningDeviceId ).equals( connection.getSourceCoordinates() ) ) {
                        setCoordinate( cleaningDeviceId , connection.getDestinationCoordinates() , connection.getDestinationSpace() );

                        return true;
                    }
                }
            }
        }

        catch ( NullPointerException e ) {
            System.out.println("Cleaning Device or Space or Connection needs to be initialized");
        }
        return false;
    }



    private Boolean checkForNorthEastObstacles(UUID spaceId , Integer moveToX , Integer moveToY) {
        Space space = spaceRepo.initializeObstacles( spaceId );

        for( Obstacle obstacle : space.getObstacles() ) {

            Integer obstacleStartX = obstacle.getStart().getX();
            Integer obstacleStartY = obstacle.getStart().getY();
            Integer obstacleEndX = obstacle.getEnd().getX();
            Integer obstacleEndY = obstacle.getEnd().getY();

            //Vertical check
            if( obstacleStartX.equals( obstacleEndX ) & !obstacleStartY.equals( obstacleEndY ) ) {
                if( moveToX.equals( obstacleStartX ) ) {
                    int sourceDestDiff = obstacleEndY - obstacleStartY;
                    for( int counter = obstacleStartY ; counter < sourceDestDiff ; counter++ ) {
                        if( counter == moveToY ) {
                            return false;
                        }
                    }
                }

            }
            //Horizontal check
            if( obstacleStartY.equals( obstacleEndY ) & !obstacleStartX.equals( obstacleEndX ) ) {
                if( moveToY.equals( obstacleStartY ) ) {
                    int sourceDestDiff = obstacleEndX - obstacleStartX;
                    for( int counter = obstacleStartX ; counter < sourceDestDiff ; counter++ ) {
                        if( counter == moveToX ) {
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

            Integer obstacleStartX = obstacle.getStart().getX();
            Integer obstacleStartY = obstacle.getStart().getY();
            Integer obstacleEndX = obstacle.getEnd().getX();
            Integer obstacleEndY = obstacle.getEnd().getY();

            //Vertical check
            if( obstacleStartX.equals( obstacleEndX ) & !obstacleStartY.equals( obstacleEndY ) ) {
                if( moveToX.equals( obstacleStartX ) ) {
                    int sourceDestDiff = obstacleEndY - obstacleStartY;
                    for( int counter = obstacleStartY ; counter < sourceDestDiff ; counter++ ) {
                        if( counter == moveToY ) {
                            return false;
                        }
                    }
                }
            }
            //Horizontal check
            if( obstacleStartY.equals( obstacleEndY ) & !obstacleStartX.equals( obstacleEndX ) ) {
                if( moveToY.equals( obstacleStartY ) ) {
                    int sourceDestDiff = obstacleEndX - obstacleStartX;
                    for( int counter = obstacleStartX ; counter < sourceDestDiff ; counter++ ) {
                        if( counter == moveToX ) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private Boolean checkForOtherDevices(UUID spaceId, Integer moveToX, Integer moveToY) {

        Space space = spaceRepo.initializeCleaningDevices(spaceId);

        for( CleaningDevice cleaningDevice : space.getCleaningDevices() ) {
            Integer deviceLocationX = cleaningDevice.getLocationX();
            Integer deviceLocationY = cleaningDevice.getLocationY();

            if( deviceLocationX.equals( moveToX ) && deviceLocationY.equals( moveToY ) ) {
                return false;
            }
        }

        return true;
    }



    private void setCoordinate(UUID cleaningDeviceId, Coordinate coordinates, Space destSpace) {

        CleaningDevice cleaningDevice = cleaningDeviceRepo.findById( cleaningDeviceId )
                .orElseThrow( () -> new EntityNotFoundException() );

        cleaningDevice.setLocationX( coordinates.getX() );
        cleaningDevice.setLocationY( coordinates.getY() );
        cleaningDevice.setSpace( destSpace );

        cleaningDeviceRepo.save( cleaningDevice );
    }


    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){

        CleaningDevice cleaningDevice = cleaningDeviceRepo.findById( cleaningDeviceId )
                .orElseThrow( () -> new EntityNotFoundException() );

        return cleaningDevice.getSpace().getSpaceId();
    }

    /**
     * This method returns the coordinate a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordinate of the cleaning device
     */
    public Coordinate getCleaningDeviceCoordinate(UUID cleaningDeviceId){
        CleaningDevice cleaningDevice = cleaningDeviceRepo.findById( cleaningDeviceId )
                .orElseThrow( () -> new EntityNotFoundException() );

        Coordinate coordinate = new Coordinate( cleaningDevice.getLocationX() , cleaningDevice.getLocationY() );

        return coordinate;
    }
}
