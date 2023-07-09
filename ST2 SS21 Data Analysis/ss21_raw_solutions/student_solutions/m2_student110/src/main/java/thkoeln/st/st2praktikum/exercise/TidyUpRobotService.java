package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TidyUpRobotService{

    public List<TidyUpRobot> allTidyUpRobots = new ArrayList<>();
    public List<CurrentCoordinate>allCurrentRobotCoordinates = new ArrayList<>();
    public List<Room> allRooms = new ArrayList<>();
    public List<BarrierInRoom> allRoomBarriers = new ArrayList<>();
    public List<Connection> allConnections = new ArrayList<>();

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room newRoom = new Room(height,width);
        allRooms.add(newRoom);
        return newRoom.returnUUID();
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {
        Barrier newBarrier = new Barrier(roomId,barrier.getBarrierString());
        allRoomBarriers.addAll(newBarrier.dissolveAllBarrierStrings());

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
        Connection newConnection = new Connection(sourceRoomId,sourceCoordinate,destinationRoomId,destinationCoordinate);
        allConnections.add(newConnection);
        return newConnection.returnUUID();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot newTidyUpRobot = new TidyUpRobot(name);
        allTidyUpRobots.add(newTidyUpRobot);
        return newTidyUpRobot.returnUUID();
    }

    /**
     * This method lets the tidy-up robot execute a order.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Order order) {
        CurrentCoordinate CurrentCord = getCurrentRobotCoordinate(tidyUpRobotId);
        if (order.isValidOrder(order)){
            if (order.getOrderString().contains("no")||order.getOrderString().contains("so")||order.getOrderString().contains("ea")||order.getOrderString().contains("we")){
                checkIfBarrierContact(getCurrentRobotCoordinate(tidyUpRobotId),order.getOrderString(),getTidyUpRobotRoomId(tidyUpRobotId));
            }
            else if (order.getOrderString().contains("tr")){
                if (!transportRobot(tidyUpRobotId,CurrentCord)) return false;
            }
            else if (order.getOrderString().contains("en")){
                if (!setInitialRoom(UUID.fromString(order.getOrderString().substring(4, order.getOrderString().length()-1)),tidyUpRobotId))return false;
            }
            else throw new UnsupportedOperationException();
        }
        return true;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        for (int i =0; i<allTidyUpRobots.size();i++){
            if (allTidyUpRobots.get(i).returnUUID().equals(tidyUpRobotId) ){
                return allTidyUpRobots.get(i).getRoomUUID();
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
        for (int i=0; i<allCurrentRobotCoordinates.size(); i++){
            if (allCurrentRobotCoordinates.get(i).returnUUID().equals(tidyUpRobotId)){
                return new Coordinate(allCurrentRobotCoordinates.get(i).getX(),allCurrentRobotCoordinates.get(i).getY());
            }
        }
        return null;
    }


    public CurrentCoordinate checkIfBarrierContact(CurrentCoordinate currentCoordinate, String commandString, UUID roomId){
        Integer moveAmmount = Integer.parseInt(commandString.substring(4,5));
        //Norden
        if ( commandString.contains("no")){
            return moveNorth(currentCoordinate,commandString,roomId,moveAmmount);
        }
        //süden
        else if (commandString.contains("so")) {
            return moveSouth(currentCoordinate,commandString,roomId,moveAmmount);
        }
        //Osten
        else if (commandString.contains("ea")){
            return moveEast(currentCoordinate,commandString,roomId,moveAmmount);
        }
        //Westen
        else if (commandString.contains("we")){

            return moveWest(currentCoordinate,commandString,roomId,moveAmmount);
        }
        return null;
    }

    public CurrentCoordinate moveNorth(CurrentCoordinate currentCoordinate, String commandString, UUID roomId, Integer moveAmmount){
        Integer xPointer = currentCoordinate.getX();
        Integer yPointer = currentCoordinate.getY();
        for (int i = 0; i<allRoomBarriers.size();i++){


            if (Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(3,4))>currentCoordinate.getY() && Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(3,4))<= (currentCoordinate.getY()+moveAmmount) && Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(1,2)) == currentCoordinate.getX() && allRoomBarriers.get(i).returnUUID().equals(roomId) ){
                currentCoordinate.setY(Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(3,4))-1);
                return currentCoordinate;
            }
        }
        currentCoordinate.setY(currentCoordinate.getY() + moveAmmount);
        if (currentCoordinate.getY()>=getRoom(roomId).checkRoomBoundariesY(allRooms)){
            currentCoordinate.setY(getRoom(roomId).checkRoomBoundariesY(allRooms));
        }
        if (!checkIfNextCoordinateIsBlocked(currentCoordinate.getX(),currentCoordinate.getY(),roomId,currentCoordinate.returnUUID())){
            currentCoordinate.setX(xPointer);
            currentCoordinate.setY(yPointer);
        }
        return currentCoordinate;
    }



    public CurrentCoordinate moveSouth(CurrentCoordinate currentCoordinate, String commandString, UUID roomId, Integer moveAmmount){
        Integer xPointer = currentCoordinate.getX();
        Integer yPointer = currentCoordinate.getY();
        for (int i = 0; i <allRoomBarriers.size(); i++) {

            if (Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(3,4)) < currentCoordinate.getY() && Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(3,4)) >= (currentCoordinate.getY() - moveAmmount) && Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(1,2)) == currentCoordinate.getX() && allRoomBarriers.get(i).returnUUID().equals(roomId)) {
                currentCoordinate.setY(Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(3,4)) + 1);
                return currentCoordinate;
            }
        }

        currentCoordinate.setY(currentCoordinate.getY() - moveAmmount);
        if (currentCoordinate.getY()<0)currentCoordinate.setY(0);
        if (!checkIfNextCoordinateIsBlocked(currentCoordinate.getX(),currentCoordinate.getY(),roomId,currentCoordinate.returnUUID())){
            currentCoordinate.setX(xPointer);
            currentCoordinate.setY(yPointer);
        }
        return currentCoordinate;
    }



    public CurrentCoordinate moveEast(CurrentCoordinate currentCoordinate, String commandString, UUID roomId, Integer moveAmmount){
        Integer xPointer = currentCoordinate.getX();
        Integer yPointer = currentCoordinate.getY();
        for (int i = 0; i<allRoomBarriers.size(); i++){

            if (Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(1,2))> currentCoordinate.getX() && Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(1,2))<= (currentCoordinate.getX()+moveAmmount) && Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(3,4)) == currentCoordinate.getY() && allRoomBarriers.get(i).returnUUID().equals(roomId) ) {
                currentCoordinate.setX(Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(1,2))-1);
                return currentCoordinate;
            }
        }
        currentCoordinate.setX(currentCoordinate.getX() + moveAmmount);
        if (currentCoordinate.getX()>=getRoom(roomId).checkRoomBoundariesX(allRooms)){
            currentCoordinate.setX(getRoom(roomId).checkRoomBoundariesX(allRooms));
        }
        if (!checkIfNextCoordinateIsBlocked(currentCoordinate.getX(),currentCoordinate.getY(),roomId,currentCoordinate.returnUUID())){
            currentCoordinate.setX(xPointer);
            currentCoordinate.setY(yPointer);
        }
        return currentCoordinate;
    }

    public CurrentCoordinate moveWest(CurrentCoordinate currentCoordinate, String commandString, UUID roomId, Integer moveAmmount){
        Integer xPointer = currentCoordinate.getX();
        Integer yPointer = currentCoordinate.getY();


        for (int i = 0; i<allRoomBarriers.size(); i++){

            if (Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(1,2))< currentCoordinate.getX() && Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(1,2))>= (currentCoordinate.getX()-moveAmmount) && Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(3,4)) == currentCoordinate.getY() && allRoomBarriers.get(i).returnUUID().equals(roomId) ) {
                currentCoordinate.setX(Integer.parseInt(allRoomBarriers.get(i).getSolvevBarrierString().substring(1,2)));
                return currentCoordinate;
            }
        }
        currentCoordinate.setX(currentCoordinate.getX() - moveAmmount);
        if (currentCoordinate.getX()<0)currentCoordinate.setX(0);

        if (!checkIfNextCoordinateIsBlocked(currentCoordinate.getX(),currentCoordinate.getY(),roomId,currentCoordinate.returnUUID())){
            currentCoordinate.setX(xPointer);
            currentCoordinate.setY(yPointer);
        }

        return currentCoordinate;
    }


    public Boolean transportRobot(UUID tidyUpRobotId, CurrentCoordinate currentCoordinate) {



        for (int o=0;o<allConnections.size(); o++){

            if (Integer.parseInt(allConnections.get(o).getSourceCoordinatAsString().substring(1,2)) == currentCoordinate.getX() && Integer.parseInt(allConnections.get(o).getSourceCoordinatAsString().substring(3,4))==currentCoordinate.getY()){

                for (int i = 0; i < allTidyUpRobots.size(); i++) {
                    if (allTidyUpRobots.get(i).returnUUID().equals(tidyUpRobotId)) {
                        allTidyUpRobots.get(i).setRoomUUID(allConnections.get(o).getDestinationRoomId());
                        for (int a=0;a<allCurrentRobotCoordinates.size();a++){
                            if (allCurrentRobotCoordinates.get(a).returnUUID().equals(tidyUpRobotId)){
                                allCurrentRobotCoordinates.get(a).setX(Integer.parseInt(allConnections.get(o).getDestinationCoordinatAsString().substring(1,2)));
                                allCurrentRobotCoordinates.get(a).setY(Integer.parseInt(allConnections.get(o).getDestinationCoordinatAsString().substring(3,4)));
                            }
                        }
                    }
                }

                if (!checkIfNextCoordinateIsBlocked(currentCoordinate.getX(),currentCoordinate.getY(),getTidyUpRobotRoomId(currentCoordinate.returnUUID()),currentCoordinate.returnUUID())){
                    throw  new IllegalArgumentException("Transfer to other room not possible because destination is blocked");
                }
                else return true;



            }
        }
        return false;
    }
    public Boolean setInitialRoom(UUID newRoomUuid, UUID tidyUpRobotId){
        for (int i = 0; i < allTidyUpRobots.size(); i++) {
            if (allTidyUpRobots.get(i).returnUUID().equals(tidyUpRobotId)) {
                for(int u=0; u<allCurrentRobotCoordinates.size();u++){
                    if (allCurrentRobotCoordinates.get(u).getX() ==0 && allCurrentRobotCoordinates.get(u).getY()==0) {
                        return false;
                    }
                }
                allTidyUpRobots.get(i).setRoomUUID(newRoomUuid);
                CurrentCoordinate nc = new CurrentCoordinate(0,0,tidyUpRobotId);
                allCurrentRobotCoordinates.add(nc);
            }
        }
        return true;
    }

    public Room getRoom(UUID roomUUID){
        for (int i=0; i<allRooms.size();i++){
            if (allRooms.get(i).returnUUID().equals(roomUUID)){
                return allRooms.get(i);
            }
        }
        return null;
    }

    public CurrentCoordinate getCurrentRobotCoordinate(UUID tidyUpRobotId){
        for (int i=0;i<allCurrentRobotCoordinates.size();i++){
            if (tidyUpRobotId.equals(allCurrentRobotCoordinates.get(i).returnUUID())){
                return allCurrentRobotCoordinates.get(i);
            }
        }
        return null;
    }

    public Boolean checkIfNextCoordinateIsBlocked(Integer x, Integer y , UUID roomID, UUID moveRobot){
        for (int i=0; i<allCurrentRobotCoordinates.size(); i++){
            if (allCurrentRobotCoordinates.get(i).getX().equals(x)&& allCurrentRobotCoordinates.get(i).getY().equals(y)){
                if (getTidyUpRobotRoomId(allCurrentRobotCoordinates.get(i).returnUUID()).equals(roomID) && allCurrentRobotCoordinates.get(i).returnUUID() != moveRobot){
                    return false;
                    //throw  new IllegalArgumentException("Not possible");
                }
            }
        }
        return true;

    }


}




