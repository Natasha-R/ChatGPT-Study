package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TidyUpRobotService {
   private HashMap<UUID,TidyUpRobot> robotHashMap= new HashMap<>();
   private HashMap<UUID,Room> roomHashMap= new HashMap<>();

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {

        Room room=new Room(height,width);
        UUID id =UUID.randomUUID();
        roomHashMap.put(id,room);
        return id;

    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID roomId, String barrierString) {
               Room room=roomHashMap.get(roomId);

        room.buildBarrier(barrierString);
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
        UUID connectionID;

       connectionID= roomHashMap.get(sourceRoomId).addConnection(sourceRoomId,sourceCoordinate,destinationRoomId,destinationCoordinate);
       if (connectionID==null)
       {
           throw new UnsupportedOperationException();
       }
       else
       return connectionID;

    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot=new TidyUpRobot(name);
        UUID id=UUID.randomUUID();
        robotHashMap.put(id,tidyUpRobot);

        return id;
    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,room-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on gridcells with a connection to another room
     * "[en,<room-id>]" for setting the initial room where a tidy-up robot is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, String commandString) {
        //Deklaration
        CommandEncoder commandEncoder=new CommandEncoder(commandString);
        String[] resultTemp = commandEncoder.encoding();
        String command=resultTemp[0];
        String commandValue=resultTemp[1];

        //Methodenaufrufe


        switch (command) {
            case "tr":
               return robotHashMap.get(tidyUpRobotId).traverse(UUID.fromString(commandValue),roomHashMap.get(getTidyUpRobotRoomId(tidyUpRobotId)));
            case "en":
                if (spaceIsFree(UUID.fromString(commandValue))) {
                    robotHashMap.get(tidyUpRobotId).en(UUID.fromString(commandValue));
                    return true;
                }
                else

                return false;
            case "no":
                robotHashMap.get(tidyUpRobotId).moveNorth(Integer.parseInt(commandValue),roomHashMap.get(getTidyUpRobotRoomId(tidyUpRobotId)));
                return true;
            case "ea":
                robotHashMap.get(tidyUpRobotId).moveEast(Integer.parseInt(commandValue),roomHashMap.get(getTidyUpRobotRoomId(tidyUpRobotId)));
                return true;
            case "so":
                robotHashMap.get(tidyUpRobotId).moveSouth(Integer.parseInt(commandValue),roomHashMap.get(getTidyUpRobotRoomId(tidyUpRobotId)));
                return true;
            case "we":
                robotHashMap.get(tidyUpRobotId).moveWest(Integer.parseInt(commandValue),roomHashMap.get(getTidyUpRobotRoomId(tidyUpRobotId)));
                return true;
        }
        
        throw new UnsupportedOperationException();
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        if (robotHashMap.get(tidyUpRobotId).getNow_in_room()!=null) {
            return robotHashMap.get(tidyUpRobotId).getNow_in_room();
        }
        else
        throw new UnsupportedOperationException();
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordiantes of the tidy-up robot encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID tidyUpRobotId){
        if (tidyUpRobotId!=null) {
            return robotHashMap.get(tidyUpRobotId).getCoordinates();
        }
        else
        throw new UnsupportedOperationException();



        //test
    }
    public void ausgabe(UUID roomId) {
        roomHashMap.get(roomId).ausgeben();

    }
    public boolean spaceIsFree(UUID roomid ){
        for (Map.Entry<UUID, TidyUpRobot> entry : robotHashMap.entrySet()) {
            UUID key = entry.getKey();
            TidyUpRobot value = entry.getValue();

            if (value.posx==0&& value.posy==0&&value.getNow_in_room().equals( roomid)) {

                return false;
            }
        }

        return true;


    }

    }

