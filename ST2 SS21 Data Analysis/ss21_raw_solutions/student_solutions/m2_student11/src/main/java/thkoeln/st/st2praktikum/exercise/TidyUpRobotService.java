package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.TaskFailedException;

import java.util.ArrayList;
import java.util.UUID;

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
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID roomId, Obstacle obstacle) {
        for (thkoeln.st.st2praktikum.exercise.room room : rooms) {
            if (room.ID.toString().equals(roomId.toString())) {
                //check for obstacle out of bounds
                if(obstacle.getStart().getY() > room.height ||obstacle.getStart().getX() > room.width) throw new TaskFailedException();
                if(obstacle.getEnd().getY() > room.height ||obstacle.getEnd().getX() > room.width) throw new TaskFailedException();
                room.addObstacle(obstacle);
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
    public UUID addConnection(UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate) {
        for (thkoeln.st.st2praktikum.exercise.room room : rooms){
            if(room.ID.equals(sourceRoomId)){
                if(sourceCoordinate.getY() > room.height ||sourceCoordinate.getX() > room.width) throw new TaskFailedException();
            }
            else if(room.ID.equals(destinationRoomId)){
                if(destinationCoordinate.getY() > room.height ||destinationCoordinate.getX() > room.width) throw new TaskFailedException();
            }
        }
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
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a obstacle or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        switch(task.getTaskType()){
            case TRANSPORT:return transportTidyRobot(task.getGridId(), tidyUpRobotId);
            case ENTER:return spawnTidyRobot(task.getGridId(), tidyUpRobotId);
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:moveTidyRobot(task.getDirection(), task.getNumberOfSteps(),tidyUpRobotId); return true;
            default:System.out.println("FAULTY COMMAND");
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
     * @return the coordinates of the tidy-up robot
     */
    public Coordinate getCoordinate(UUID tidyUpRobotId){
        for (thkoeln.st.st2praktikum.exercise.tidyUpRobot tidyUpRobot : tidyUpRobots) {
            if (tidyUpRobot.ID.toString().equals(tidyUpRobotId.toString())) {
                return tidyUpRobot.getCoordinates();
            }
        }
        return null;
    }

    private boolean transportTidyRobot(UUID destUUID, UUID tidyUpRobotId){
        for (thkoeln.st.st2praktikum.exercise.tidyUpRobot tidyUpRobot : tidyUpRobots) {
            if (tidyUpRobot.ID == tidyUpRobotId) {
                //robot found!, now get the desired connection
                for (thkoeln.st.st2praktikum.exercise.connection connection : Connections) {
                    //check if destination is right, connection and robot in same room, robot on right coordinates
                    if (connection.dest.equals(destUUID) && connection.source.equals(tidyUpRobot.roomid) && connection.sourceCords.equals(tidyUpRobot.getCoordinates())) {
                        //no other robot on dest coordinates in that room
                        for(thkoeln.st.st2praktikum.exercise.tidyUpRobot blockingRobot : tidyUpRobots){
                            if(blockingRobot.roomid != null) {
                                if (blockingRobot.roomid.equals(connection.dest) && blockingRobot.coordinate.equals(connection.destCords))
                                    return false;
                            }
                        }

                        //connection found
                        tidyUpRobot.setRoom(connection.dest);
                        tidyUpRobot.setCoordinatesFromString(connection.destCords.toString());
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //returns true if there is either a border in the frame or a robot on the next frame
    private boolean borderInFront(Coordinate cords, DirectionsType Direction, UUID ROOM, UUID ROBOTID){
        //get the room
        int x = cords.getX();
        int y = cords.getY();
        int roomindex = -1;
        for(int i = 0; i<rooms.size(); i++) {
            if (rooms.get(i).ID.equals(ROOM)) {
                roomindex = i;
                break;
            }
        }
        //check for a border, first look for borders
        for(int i = 0; i < rooms.get(roomindex).Obstacles.size(); i++){
            //search for an obstacle in that direction first check if coordinates of border are right, then look if the border is in desired direction
            if(rooms.get(roomindex).Obstacles.get(i).blocksCoordinateInDirection(cords, Direction)){
                return true;
            }
        }
        //check for a robot in front
        //need to know next coordinate using x, y & direction
        switch(Direction){
            //north go up plus y
            case NORTH: y++; break;
            //south go down minus y
            case SOUTH: y--; break;
            //east go right plus x
            case EAST: x++; break;
            //west go left minus x
            case WEST: x--; break;
        }
        //check for robots in front -> on next coordinate
        for (thkoeln.st.st2praktikum.exercise.tidyUpRobot tidyUpRobot : tidyUpRobots) {
            //robot on next coordinate in room is not you -> possible issues with frame borders
            if (tidyUpRobot.coordinate.getX() == x && tidyUpRobot.coordinate.getY() == y && !tidyUpRobot.ID.equals(ROBOTID) && ROOM.equals(tidyUpRobot.roomid)) {
                System.out.println("Robot in front!");
                return true;
            }
        }
        return false;
    }
    private boolean spawnTidyRobot(UUID spawnUUID, UUID tidyUpRobotId){
        for(int i = 0; i < tidyUpRobots.size(); i++){
            //get the robot
            if(tidyUpRobots.get(i).ID.equals(tidyUpRobotId)){
                //check, if not yet initialized
                if(tidyUpRobots.get(i).roomid == null){
                    //check if theres already a robot on spawn
                    for(thkoeln.st.st2praktikum.exercise.tidyUpRobot robot : tidyUpRobots){
                        //check if the robot you found is not you and in the right room
                        if(robot.coordinate.getX() == 0 && robot.coordinate.getY() == 0 && spawnUUID.equals(robot.roomid) && !robot.ID.equals(tidyUpRobots.get(i).ID)){
                            return false; //the robot will ALWAYS spawn at 0,0
                        }
                    }
                    //initialize
                    tidyUpRobots.get(i).setRoom(spawnUUID);
                    System.out.println("Robot spawned!");
                    return true;
                }else{
                    //return false if the robot has been spawned already (stops for loop)
                    return false;
                }
            }
        }
        return false;
    }
    private void moveTidyRobot(DirectionsType dir, int steps, UUID tidyUpRobotID){
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
                case NORTH: {
                    // plus y
                    if(!borderInFront(tidyUpRobots.get(robotIndex).coordinate,dir,tidyUpRobots.get(robotIndex).roomid, tidyUpRobots.get(robotIndex).ID) && tidyUpRobots.get(robotIndex).coordinate.getY()+1 < rooms.get(roomIndex).height){
                        tidyUpRobots.get(robotIndex).coordinate //y++
                                = new Coordinate(tidyUpRobots.get(robotIndex).coordinate.getX(), tidyUpRobots.get(robotIndex).coordinate.getY() + 1);
                    }break;
                }
                case EAST: {
                    // plus x
                    if(!borderInFront(tidyUpRobots.get(robotIndex).coordinate,dir,rooms.get(roomIndex).ID, tidyUpRobots.get(robotIndex).ID) && tidyUpRobots.get(robotIndex).coordinate.getX()+1 < rooms.get(roomIndex).width){
                        tidyUpRobots.get(robotIndex).coordinate //x++
                                = new Coordinate(tidyUpRobots.get(robotIndex).coordinate.getX() + 1, tidyUpRobots.get(robotIndex).coordinate.getY());
                    }break;
                }
                case SOUTH: {
                    // minus y
                    if(!borderInFront(tidyUpRobots.get(robotIndex).coordinate, dir, rooms.get(roomIndex).ID, tidyUpRobots.get(robotIndex).ID) && tidyUpRobots.get(robotIndex).coordinate.getY()-1 >= 0){
                        tidyUpRobots.get(robotIndex).coordinate //y--
                                = new Coordinate(tidyUpRobots.get(robotIndex).coordinate.getX(), tidyUpRobots.get(robotIndex).coordinate.getY() - 1);
                    }break;
                }
                case WEST: {
                    // minus x
                    if(!borderInFront(tidyUpRobots.get(robotIndex).coordinate, dir, rooms.get(roomIndex).ID, tidyUpRobots.get(robotIndex).ID) && tidyUpRobots.get(robotIndex).coordinate.getX()-1 >= 0){
                        tidyUpRobots.get(robotIndex).coordinate //x--
                                = new Coordinate(tidyUpRobots.get(robotIndex).coordinate.getX() -1, tidyUpRobots.get(robotIndex).coordinate.getY());
                    }break;
                }
                default: System.out.println("unsupported direction");
            }
        }
    }
}
