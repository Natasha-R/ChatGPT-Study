package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
import java.util.ArrayList;

public class TidyUpRobotService {

    ArrayList<room> rooms = new ArrayList<>();
    ArrayList<tidyUpRobot> tidyUpRobots = new ArrayList<>();
    ArrayList<connection> Connections = new ArrayList<>();
    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        room newroom = new room(height, width);
        rooms.add(newroom);
        return newroom.ID;
    }

    /**
     * This method adds a obstacle to a given room.
     * @param roomId the ID of the room the obstacle shall be placed on
     * @param obstacleString the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     */
    public void addObstacle(UUID roomId, String obstacleString) {
        //get room with required id, run add obstacle on the room
        for (thkoeln.st.st2praktikum.exercise.room room : rooms) {
            if (room.ID.toString().equals(roomId.toString())) {
                room.addObstacle(obstacleString);
                break;
            }
        }
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
        connection connect = new connection(sourceRoomId, sourceCoordinate, destinationRoomId, destinationCoordinate);
        Connections.add(connect);
        return connect.ID;
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        tidyUpRobot newrobot = new tidyUpRobot(name);
        tidyUpRobots.add(newrobot);
        return newrobot.ID;
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
     *      (Movement commands are always successful, even if the tidy-up robot hits a obstacle or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, String commandString) {
        System.out.println(">>Command received: "+ commandString + " RobotID: " + tidyUpRobotId.toString());
        commandString = commandString.replace("[","").replace("]","");
        String[] instruction = commandString.split(",");
        switch(instruction[0]){
            case "tr":return transportTidyRobot(UUID.fromString(instruction[1]), tidyUpRobotId);
            case "en":return spawnTidyRobot(UUID.fromString(instruction[1]), tidyUpRobotId);
            case "no":
            case "so":
            case "ea":
            case "we":moveTidyRobot(instruction[0], Integer.parseInt(instruction[1]),tidyUpRobotId); return true;
            default:System.out.println("FAULTY COMMAND");
        }
        return false;
    }

    //transport robot via connection.
    //parameters are instruction[1] (destination UUID), tidyuprobotid
    private boolean transportTidyRobot(UUID destUUID, UUID tidyUpRobotId){
        for (thkoeln.st.st2praktikum.exercise.tidyUpRobot tidyUpRobot : tidyUpRobots) {
            if (tidyUpRobot.ID == tidyUpRobotId) {
                //robot found!, now get the desired connection
                for (thkoeln.st.st2praktikum.exercise.connection connection : Connections) {
                    //check if destination is right, connection and robot in same room, robot on right coordinates
                    if (connection.dest.equals(destUUID) && connection.source.equals(tidyUpRobot.roomid) && connection.sourceCords.equals(tidyUpRobot.getCoordinates())) {
                        //connection found
                        tidyUpRobot.setRoom(connection.dest);
                        tidyUpRobot.setCoordinatesFromString(connection.destCords);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //setting room
    //parameters: tidyuprobotid, roomid
    private boolean spawnTidyRobot(UUID spawnUUID, UUID tidyUpRobotId){
        for(int i = 0; i < tidyUpRobots.size(); i++){
            //get the robot
            if(tidyUpRobots.get(i).ID.equals(tidyUpRobotId)){
                //check, if not yet initialized
                if(tidyUpRobots.get(i).roomid == null){
                    //check if theres already a robot on spawn
                    for(thkoeln.st.st2praktikum.exercise.tidyUpRobot robot : tidyUpRobots){
                        //check if the robot you found is not you and in the right room
                        if(robot.x == 0 && robot.y == 0 && spawnUUID.equals(robot.roomid) && !robot.ID.equals(tidyUpRobots.get(i).ID)){
                            return false; //the robot will ALWAYS spawn at 0,0
                        }
                    }
                    //initialize
                    tidyUpRobots.get(i).setRoom(spawnUUID);
                    return true;
                }else{
                    //return false if the robot has been spawned already (stops for loop)
                    return false;
                }
            }
        }
        return false;
    }

    private void moveTidyRobot(String dir, int steps, UUID tidyUpRobotID){
        int robotIndex = -1;
        int roomIndex = -1;
        //get robot
        for(int i = 0; i< tidyUpRobots.size();i++){
            if(tidyUpRobots.get(i).ID.toString().equals(tidyUpRobotID.toString())){
                robotIndex = i;
                break;
            }
        }
        //kill if room of robot is not yet initialized
        if(tidyUpRobots.get(robotIndex).roomid == null){
            System.out.println("I'm sorry, but the robot has not yet been spawned!");
            return;
        }
        //get room
        for(int i = 0; i < rooms.size(); i++){
            if(rooms.get(i).ID.toString().equals(tidyUpRobots.get(robotIndex).roomid.toString())){
                roomIndex = i;
                break;
            }
        }
        //move the robot in steps, checking for obstacles each step and possible borders
        for(int i = 0; i< steps; i++) {
            switch (dir) {
                case "no": {
                    // plus y
                    if(!borderInFront(tidyUpRobots.get(robotIndex).x,tidyUpRobots.get(robotIndex).y,"no",tidyUpRobots.get(robotIndex).roomid, tidyUpRobots.get(robotIndex).ID) && tidyUpRobots.get(robotIndex).y+1 < rooms.get(roomIndex).height){
                        tidyUpRobots.get(robotIndex).y++;
                    }break;
                }
                case "ea": {
                    // plus x
                    if(!borderInFront(tidyUpRobots.get(robotIndex).x,tidyUpRobots.get(robotIndex).y, "ea",rooms.get(roomIndex).ID, tidyUpRobots.get(robotIndex).ID) && tidyUpRobots.get(robotIndex).x+1 < rooms.get(roomIndex).width){
                        tidyUpRobots.get(robotIndex).x++;
                    }break;
                }
                case "so": {
                    // minus y
                    if(!borderInFront(tidyUpRobots.get(robotIndex).x,tidyUpRobots.get(robotIndex).y, "so", rooms.get(roomIndex).ID, tidyUpRobots.get(robotIndex).ID) && tidyUpRobots.get(robotIndex).y-1 >= 0){
                        tidyUpRobots.get(robotIndex).y--;
                    }break;
                }
                case "we": {
                    // minus x
                    if(!borderInFront(tidyUpRobots.get(robotIndex).x,tidyUpRobots.get(robotIndex).y, "we", rooms.get(roomIndex).ID, tidyUpRobots.get(robotIndex).ID) && tidyUpRobots.get(robotIndex).x-1 >= 0){
                        tidyUpRobots.get(robotIndex).x--;
                    }break;
                }
                default: System.out.println("unsupported direction");
            }
        }
    }

    //returns true if there is either a border in the frame or a robot on the next frame
    private boolean borderInFront(int x, int y, String direction, UUID ROOM, UUID ROBOTID){
        //get the room
        int roomindex = -1;
        for(int i = 0; i<rooms.size(); i++) {
            if (rooms.get(i).ID.equals(ROOM)) {
                roomindex = i;
                break;
            }
        }
        //Get direction of desire
        int dir = -1;
        switch(direction) {
            case "no": dir = 0; break;
            case "so": dir = 1; break;
            case "ea": dir = 2; break;
            case "we": dir = 3; break;
        }
        //check for a border, first look for borders
        for(int i = 0; i < rooms.get(roomindex).Obstacles.size(); i++){
            //search for an obstacle in that direction first check if coordinates of border are right, then look if the border is in desired direction
            if(rooms.get(roomindex).Obstacles.get(i).get(0) == x && rooms.get(roomindex).Obstacles.get(i).get(1) == y && rooms.get(roomindex).Obstacles.get(i).get(2) == dir){
                return true;
            }
        }
        //check for a robot in front
        //need to know next coordinate using x, y & direction
        switch(dir){
            //north go up plus y
            case 0: y++; break;
            //south go down minus y
            case 1: y--; break;
            //east go right plus x
            case 2: x++; break;
            //west go left minus x
            case 3: x--; break;
        }
        //check for robots in front -> on next coordinate
        for (thkoeln.st.st2praktikum.exercise.tidyUpRobot tidyUpRobot : tidyUpRobots) {
            //robot on next coordinate in room is not you -> possible issues with frame borders
            if (tidyUpRobot.x == x && tidyUpRobot.y == y && !tidyUpRobot.ID.equals(ROBOTID) && ROOM.equals(tidyUpRobot.roomid)) {
                return true;
            }
        }
        return false;
    }
    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        for (thkoeln.st.st2praktikum.exercise.tidyUpRobot tidyUpRobot : tidyUpRobots) {
            if (tidyUpRobot.ID.equals(tidyUpRobotId)) {
                return tidyUpRobot.roomid;
            }
        }
        return null;
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordiantes of the tidy-up robot encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID tidyUpRobotId){
        for(int i = 0; i<tidyUpRobots.size(); i++){
            if(tidyUpRobots.get(i).ID.toString().equals(tidyUpRobotId.toString())){
                return tidyUpRobots.get(i).getCoordinates();
            }
        }
        return null;
    }
}