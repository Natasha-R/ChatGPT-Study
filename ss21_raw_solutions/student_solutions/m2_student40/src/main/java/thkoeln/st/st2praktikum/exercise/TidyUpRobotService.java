package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TidyUpRobotService {

    private List<Room> rooms = new ArrayList<Room>();
    private List<Robot> robots = new ArrayList<Robot>();

    private Room getRoomByID(UUID roomID) {
        for (Room room : this.rooms) {
            if (room.getId().equals(roomID)) {
                return room;
            }
        }
        throw new UnsupportedOperationException("Invalid roomID " + roomID);
    }

    private Robot getRobotByID(UUID robotID) {
        for (Robot robot : this.robots) {
            if (robot.getId().equals(robotID))
                return robot;
        }

        throw new UnsupportedOperationException("Invalid robotID");
    }

    private boolean isRobotOnField(Room room, Coordinate position){
        for( Robot robot : robots ){
            if( robot.getCurrentRoom() == null ) {
                continue;
            }
            if( robot.getCurrentRoom().getId().equals(room.getId())){
                if( robot.getPosition().equals(position)){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean enterRobotInRoom(Robot robot, Order order){
        if( !isRobotOnField(getRoomByID(order.getGridId()), new Coordinate(0,0))) {
            return robot.setRoom(getRoomByID(order.getGridId()));
        }
        return false;
    }

    private boolean transportRobot(Robot robot, Order order){
        Room robotRoom = robot.getCurrentRoom();
        Tile robotTile = robotRoom.getTile(robot.getPosition());
        if( robotTile == null ) {
            return false;
        }
        if(robotTile.isConnection()) {
            Connection connection = (Connection) robotTile;
            if(connection.canUse(order.getGridId())){
                if(!isRobotOnField(connection.getDestinationRoom(), connection.getDestinationPosition())) {
                    return robot.switchRoom(connection.getDestinationRoom(), connection.getDestinationPosition());
                }
            }
        }

        return false;
    }

    private void performMove(Robot robot, int amountX, int amountY){
        Coordinate start = robot.getPosition();
        Room room = robot.getCurrentRoom();

        if( start.getX() + amountX < 0 || start.getY() + amountY < 0 ){
            return;
        }

        Coordinate destination = new Coordinate(start.getX() + amountX, start.getY() + amountY);
        if( room.isInBoundaries(destination)){
            Tile destinationTile = room.getTile(destination);
            if( destinationTile != null ) {
                if (!destinationTile.isWall()){
                    if (!isRobotOnField(room, destination)) {
                        robot.move(amountX, amountY);
                    }
                }
            }
        }
    }

    private void moveRobot(Robot robot, OrderType direction){
        switch (direction){
            case NORTH:{
                performMove(robot, 0, 1);
                break;
            }
            case SOUTH:{
                performMove(robot, 0, -1);
                break;
            }
            case WEST:{
                performMove(robot, -1, 0);
                break;
            }
            case EAST:{
                performMove(robot, 1, 0);
                break;
            }
        }
    }

    private void moveRobot(Robot robot, Order order){
        if( robot.canMove() ) {
            for (int i = 0; i < order.getNumberOfSteps(); i++) {
                moveRobot(robot, order.getOrderType());
            }
        }
    }

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(width, height);
        this.rooms.add(room);

        return room.getId();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall) {
        getRoomByID(roomId).addWall(wall);
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
        Connection newConnection = new Connection(getRoomByID(destinationRoomId), destinationCoordinate);
        getRoomByID(sourceRoomId).addConnection(sourceCoordinate, newConnection);

        return newConnection.getId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        Robot newRobot = new Robot(name);
        this.robots.add(newRobot);

        return newRobot.getId();
    }

    /**
     * This method lets the tidy-up robot execute a order.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Order order) {

        Robot currentRobot = getRobotByID(tidyUpRobotId);

        switch(order.getOrderType()){
            case ENTER:{
                return enterRobotInRoom(currentRobot, order);
            }
            case TRANSPORT:{
                return transportRobot(currentRobot, order);
            }
            case WEST:
            case EAST:
            case SOUTH:
            case NORTH:{
                moveRobot(currentRobot, order);
            }
        }

        return true;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return getRobotByID(tidyUpRobotId).getCurrentRoom().getId();
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinates of the tidy-up robot
     */
    public Coordinate getCoordinate(UUID tidyUpRobotId){
        return getRobotByID(tidyUpRobotId).getPosition();
    }
}
