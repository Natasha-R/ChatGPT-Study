package thkoeln.st.st2praktikum.exercise;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TidyUpRobotService {



    List<Room> rooms = new ArrayList<>();
    List<TidyUpRobot> tidyUpRobots = new ArrayList<>();
    List<Connections> connections = new ArrayList<>();





    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {


        Room room = new Room();
        room.id = UUID.randomUUID();
        room.height = height;
        room.width = width;
        rooms.add(room);

        return room.id;

    }


    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID roomId, String barrierString) {

        Room room = getRoom(roomId);
        room.barriers.add(barrierString.replaceAll("\\D+",""));
    }

    /**
     * This method adds a traversable connection between two rooms. Connections only work in one direction.
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate) {


        Connections connection = new Connections();
        connection.sourceRoomId = sourceRoomId;
        connection.sourceCoordinates = sourceCoordinate;
        connection.destinationRoomID = destinationRoomId;
        connection.destinationCoordinates = destinationCoordinate;
        connection.connectionId = UUID.randomUUID();
        connections.add(connection);
        getRoom(sourceRoomId).connections.add(connection);

        return connection.connectionId;
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {

        TidyUpRobot tidyUpRobot = new TidyUpRobot();
        tidyUpRobot.name = name;
        tidyUpRobot.tidyUpRobotId = UUID.randomUUID();
        tidyUpRobot.xPosition = 99;
        tidyUpRobot.yPosition = 99;
        tidyUpRobot.roomId = UUID.randomUUID();


        tidyUpRobots.add(tidyUpRobot);
        return tidyUpRobot.tidyUpRobotId;

    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,room-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on tiles with a connection to another room
     * "[en,<room-id>]" for setting the initial room where a tidy-up robot is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, String commandString) {



        switch ( commandString.substring(1,3)){

            case "tr" : return transportTidyUpRobot( tidyUpRobotId , commandString.substring(4,40));

            case "en" : return spawnTidyUpRobot( tidyUpRobotId , commandString.substring(4,40));

            case "no" : return moveNorth( tidyUpRobotId ,commandString);

            case "ea" : return moveEast ( tidyUpRobotId ,commandString);

            case "so" : return moveSouth( tidyUpRobotId ,commandString);

            case "we" : return moveWest( tidyUpRobotId ,commandString);

        }

        return true;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){


        return getTidyUpRobot(tidyUpRobotId).roomId;
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordiantes of the tidy-up robot encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID tidyUpRobotId){
        return "("+getTidyUpRobot(tidyUpRobotId).xPosition+","+getTidyUpRobot(tidyUpRobotId).yPosition+")";
    }

    public Boolean spawnTidyUpRobot ( UUID tidyUpRobot , String roomId ){

        for ( int i = 0 ; i < tidyUpRobots.size() ; i++){
            if (tidyUpRobots.get(i).xPosition == 0 && tidyUpRobots.get(i).yPosition == 0){
                return false;
            }
        }
        getTidyUpRobot(tidyUpRobot).xPosition = 0;
        getTidyUpRobot(tidyUpRobot).yPosition = 0;
        getTidyUpRobot(tidyUpRobot).roomId = UUID.fromString(roomId);
        return true;
    }

    public Boolean transportTidyUpRobot(UUID tidyUpRobot , String roomId ){


        for ( int i = 0 ; i < connections.size() ; i++) {


                if (connections.get(i).sourceRoomId.toString() .equals(getTidyUpRobot(tidyUpRobot).roomId.toString()) && connections.get(i).sourceCoordinates.equals(getCoordinates(tidyUpRobot)) ){

                        getTidyUpRobot(tidyUpRobot).xPosition = Character.getNumericValue(connections.get(i).destinationCoordinates.charAt(1));
                        getTidyUpRobot(tidyUpRobot).yPosition = Character.getNumericValue(connections.get(i).destinationCoordinates.charAt(3));
                        getTidyUpRobot(tidyUpRobot).roomId = connections.get(i).destinationRoomID;
                        return true;

                }

        }
        return false;
    }

    public Boolean moveNorth ( UUID tidyUpRobot , String directionAndSteps ){

        if (getTidyUpRobot(tidyUpRobot).yPosition + Character.getNumericValue(directionAndSteps.charAt(4)) > getRoom(getTidyUpRobot(tidyUpRobot).roomId).height){
            getTidyUpRobot(tidyUpRobot).yPosition = getRoom(getTidyUpRobot(tidyUpRobot).roomId).height;
            return true;
        }

        if (!isFieldFree(tidyUpRobot , directionAndSteps.charAt(1), Character.getNumericValue(directionAndSteps.charAt(4)))){
            return true;
        }

        getTidyUpRobot(tidyUpRobot).yPosition = getTidyUpRobot(tidyUpRobot).yPosition + Character.getNumericValue(directionAndSteps.charAt(4));
        return true;
    }

    public Boolean moveEast ( UUID tidyUpRobot , String directionAndSteps ){

        if (getTidyUpRobot(tidyUpRobot).xPosition + Character.getNumericValue(directionAndSteps.charAt(4)) > getRoom(getTidyUpRobot(tidyUpRobot).roomId).width){
            getTidyUpRobot(tidyUpRobot).xPosition  = getRoom(getTidyUpRobot(tidyUpRobot).roomId).width;
            return true;
        }
        if (!isFieldFree(tidyUpRobot , directionAndSteps.charAt(1), Character.getNumericValue(directionAndSteps.charAt(4)))){
            return true;
        }
        getTidyUpRobot(tidyUpRobot).xPosition += Character.getNumericValue(directionAndSteps.charAt(4));
        return true;

    }

    public Boolean moveSouth ( UUID tidyUpRobot , String directionAndSteps ){

        if (getTidyUpRobot(tidyUpRobot).yPosition - Character.getNumericValue(directionAndSteps.charAt(4)) < 0){
            getTidyUpRobot(tidyUpRobot).yPosition = 0;
            return true;
        }
        if (!isFieldFree(tidyUpRobot , directionAndSteps.charAt(1), Character.getNumericValue(directionAndSteps.charAt(4)))){
            return true;
        }
        getTidyUpRobot(tidyUpRobot).yPosition -= Character.getNumericValue(directionAndSteps.charAt(4));

        return true;
    }

    public Boolean moveWest ( UUID tidyUpRobot , String directionAndSteps ) {

        if (getTidyUpRobot(tidyUpRobot).xPosition - Character.getNumericValue(directionAndSteps.charAt(4)) < 0 ){
            getTidyUpRobot(tidyUpRobot).xPosition = 0;
            return true;
        }
        if (!isFieldFree(tidyUpRobot , directionAndSteps.charAt(1), Character.getNumericValue(directionAndSteps.charAt(4)))){
            return true;
        }
        getTidyUpRobot(tidyUpRobot).xPosition -= Character.getNumericValue(directionAndSteps.charAt(4));
        return true;

    }






    public  Boolean isFieldFree (UUID tidyUpRobotID , Character direction , Integer steps ){


        char[][] array = new char[getRoom(getTidyUpRobot(tidyUpRobotID).roomId).width][getRoom(getTidyUpRobot(tidyUpRobotID).roomId).height];

        int barrierLenght;

        for ( int  i = 0 ; i < getRoom(getTidyUpRobot(tidyUpRobotID).roomId).barriers.size() ; i++) {
            String a = getRoom(getTidyUpRobot(tidyUpRobotID).roomId).barriers.get(i);
            //Y Barriere
            if (getRoom(getTidyUpRobot(tidyUpRobotID).roomId).barriers.get(i).charAt(0) == getRoom(getTidyUpRobot(tidyUpRobotID).roomId).barriers.get(i).charAt(2)) {
                barrierLenght = Character.getNumericValue(getRoom(getTidyUpRobot(tidyUpRobotID).roomId).barriers.get(i).charAt(3)) - Character.getNumericValue(getRoom(getTidyUpRobot(tidyUpRobotID).roomId).barriers.get(i).charAt(1));

                for (int j = 0; j < barrierLenght; j++) {
                    array[Character.getNumericValue(getRoom(getTidyUpRobot(tidyUpRobotID).roomId).barriers.get(i).charAt(1) + j)][Character.getNumericValue(getRoom(getTidyUpRobot(tidyUpRobotID).roomId).barriers.get(i).charAt(0))] = 'X';
                }
            }else{

            //X Barriere
            barrierLenght = Character.getNumericValue(getRoom(getTidyUpRobot(tidyUpRobotID).roomId).barriers.get(i).charAt(2)) - Character.getNumericValue(getRoom(getTidyUpRobot(tidyUpRobotID).roomId).barriers.get(i).charAt(0));

            for (int j = 0; j < barrierLenght ; j++) {
                array[Character.getNumericValue(getRoom(getTidyUpRobot(tidyUpRobotID).roomId).barriers.get(i).charAt(1))][Character.getNumericValue(getRoom(getTidyUpRobot(tidyUpRobotID).roomId).barriers.get(i).charAt(0)) + j] = 'X';
            }
            }
        }



        if ( direction == 'n') {




            for ( int i = 1 ; i < steps; i++) {
                if (array[Character.getNumericValue(getCoordinates(tidyUpRobotID).charAt(3)) + i][Character.getNumericValue(getCoordinates(tidyUpRobotID).charAt(1))] == 'X') {
                    getTidyUpRobot(tidyUpRobotID).yPosition = Character.getNumericValue(getCoordinates(tidyUpRobotID).charAt(3)) + i -1;
                    return false;
                }
            }
        }
        if ( direction == 'e') {
            for ( int i = 0 ; i < steps ; i++){
            if (array[Character.getNumericValue(getCoordinates(tidyUpRobotID).charAt(3))][Character.getNumericValue(getCoordinates(tidyUpRobotID).charAt(1))+i] == 'X') {
                getTidyUpRobot(tidyUpRobotID).xPosition = Character.getNumericValue(getCoordinates(tidyUpRobotID).charAt(1))+i -1;
                return false;
            }
            }
        }
        if ( direction == 's') {
            for ( int i = 0 ; i < steps ; i++){
            if (array[Character.getNumericValue(getCoordinates(tidyUpRobotID).charAt(3))-i][Character.getNumericValue(getCoordinates(tidyUpRobotID).charAt(1))] == 'X') {
                getTidyUpRobot(tidyUpRobotID).yPosition = Character.getNumericValue(getCoordinates(tidyUpRobotID).charAt(3)) -i +1;
                return false;
            }
            }
        }
        if ( direction == 'w') {
            for ( int i = 0; i < steps ; i++){
            if (array[Character.getNumericValue(getCoordinates(tidyUpRobotID).charAt(3))][Character.getNumericValue(getCoordinates(tidyUpRobotID).charAt(1))-i] == 'X') {
                getTidyUpRobot(tidyUpRobotID).xPosition = Character.getNumericValue(getCoordinates(tidyUpRobotID).charAt(1)) -i +1;
                return false;
            }
            }
        }

        return true;
    }



    public Room getRoom ( UUID roomId){

        for ( int i  = 0 ; i < rooms.size() ; i++){
            if ( rooms.get(i).id.toString().equals(roomId.toString())){
                return rooms.get(i);
            }
        }
        return null;
    }

    public TidyUpRobot getTidyUpRobot ( UUID tidyUpRobotId){

        for ( int i  = 0 ; i < tidyUpRobots.size() ; i++){
            if ( tidyUpRobots.get(i).tidyUpRobotId.toString().equals(tidyUpRobotId.toString())){
                return tidyUpRobots.get(i);
            }
        }
        return null;
    }

    public String tidyUpRobotField( UUID tidyUpRobot , String roomId ) {


        return "";
    }
}
