package thkoeln.st.st2praktikum.exercise;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TidyUpRobotService {


    public List<TidyUpRobot>allTidyUpRobots = new ArrayList<>();
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
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID roomId, String barrierString) {
        allRoomBarriers.addAll(dissolveAllBarrierStrings(barrierString,roomId));
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
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,room-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on cells with a connection to another room
     * "[en,<room-id>]" for setting the initial room where a tidy-up robot is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, String commandString) {
        Boolean returnBool=true;
        CurrentCoordinate CurrentCord = null;

        for (int c=0;c<allCurrentRobotCoordinates.size();c++){
            if (tidyUpRobotId==allCurrentRobotCoordinates.get(c).returnUUID()){
                CurrentCord = allCurrentRobotCoordinates.get(c);
            }
        }
        if (commandString!=null){
            if (commandString.contains("no")||commandString.contains("so")||commandString.contains("ea")||commandString.contains("we")){
                for (int i = 0; i<allCurrentRobotCoordinates.size();i++){

                    if (allCurrentRobotCoordinates.get(i).returnUUID() == tidyUpRobotId){
                        for (int i1 =0; i1<allTidyUpRobots.size(); i1++){
                            if (allTidyUpRobots.get(i1).returnUUID() == tidyUpRobotId){
                                checkIfBarrierContact(allCurrentRobotCoordinates.get(i),commandString,allTidyUpRobots.get(i1).roomUUID);
                            }
                        }
                    }
                }
            }
            else if (commandString.contains("tr")){
               returnBool= transportRobot(commandString,tidyUpRobotId,CurrentCord);
            }
            else if (commandString.contains("en")){
                returnBool=setInitialRoom(UUID.fromString(commandString.substring(4, commandString.length()-1)),tidyUpRobotId);
            }
            else throw new UnsupportedOperationException();
        }

        return returnBool;
    }

    public Boolean transportRobot(String transportString, UUID tidyUpRobotId, CurrentCoordinate currentCoordinate) {
        for (int o=0;o<allConnections.size(); o++){

            if (Integer.parseInt(allConnections.get(o).sourceCoordinate.substring(1,2)) == currentCoordinate.x && Integer.parseInt(allConnections.get(o).sourceCoordinate.substring(3,4))==currentCoordinate.y){

                for (int i = 0; i < allTidyUpRobots.size(); i++) {
                    if (allTidyUpRobots.get(i).returnUUID() == tidyUpRobotId) {
                        allTidyUpRobots.get(i).roomUUID = allConnections.get(o).destinationRoomId;
                        for (int a=0;a<allCurrentRobotCoordinates.size();a++){
                            if (allCurrentRobotCoordinates.get(a).returnUUID() == tidyUpRobotId){
                                allCurrentRobotCoordinates.get(a).x=Integer.parseInt(allConnections.get(o).destinationCoordinate.substring(1,2));
                                allCurrentRobotCoordinates.get(a).y=Integer.parseInt(allConnections.get(o).destinationCoordinate.substring(3,4));
                            }
                        }
                    }
                }

                return true;
            }
        }
        return false;
    }

    public Boolean setInitialRoom(UUID newRoomUuid, UUID tidyUpRobotId){
        for (int i = 0; i < allTidyUpRobots.size(); i++) {
            if (allTidyUpRobots.get(i).returnUUID() == tidyUpRobotId) {
                for(int u=0; u<allCurrentRobotCoordinates.size();u++){
                    if (allCurrentRobotCoordinates.get(u).x ==0 && allCurrentRobotCoordinates.get(u).y==0) {
                        return false;
                    }
                }
                allTidyUpRobots.get(i).roomUUID = newRoomUuid;
                CurrentCoordinate nc = new CurrentCoordinate(0,0,tidyUpRobotId);
                allCurrentRobotCoordinates.add(nc);
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
        for (int i =0; i<allTidyUpRobots.size();i++){
            if (allTidyUpRobots.get(i).returnUUID() == tidyUpRobotId){
                return allTidyUpRobots.get(i).roomUUID;
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
        for (int i=0; i<allCurrentRobotCoordinates.size(); i++){
            if (allCurrentRobotCoordinates.get(i).returnUUID() == tidyUpRobotId){
                return "("+allCurrentRobotCoordinates.get(i).x+","+allCurrentRobotCoordinates.get(i).y+")";
            }
        }
        return null;
    }

    public CurrentCoordinate checkIfBarrierContact(CurrentCoordinate currentCoordinate, String commandString, UUID roomId){
         Integer moveAmmount = Integer.parseInt(commandString.substring(4,5));
         //Norden
        if ( commandString.contains("no")){
         for (int i = 0; i<allRoomBarriers.size();i++){

             if (Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(3,4))>currentCoordinate.y && Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(3,4))<= (currentCoordinate.y+moveAmmount) && Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(1,2)) == currentCoordinate.x && allRoomBarriers.get(i).returnUUID().equals(roomId) ){
                 currentCoordinate.y = Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(3,4))-1;
                 return currentCoordinate;
             }
         }

             currentCoordinate.y = currentCoordinate.y + moveAmmount;
            return currentCoordinate;

          }
        //süden
        else if (commandString.contains("so")) {
            for (int i = 0; i <allRoomBarriers.size(); i++) {

                if (Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(3,4)) < currentCoordinate.y && Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(3,4)) >= (currentCoordinate.y - moveAmmount) && Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(1,2)) == currentCoordinate.x && allRoomBarriers.get(i).returnUUID().equals(roomId)) {
                    currentCoordinate.y = Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(3,4)) + 1;
                    return currentCoordinate;
                }
            }
                currentCoordinate.y = currentCoordinate.y - moveAmmount;
             if (currentCoordinate.y<0)currentCoordinate.y=0;
                return currentCoordinate;
        }
        //Osten
        else if (commandString.contains("ea")){
            for (int i = 0; i<allRoomBarriers.size(); i++){

                if (Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(1,2))> currentCoordinate.x && Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(1,2))<= (currentCoordinate.x+moveAmmount) && Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(3,4)) == currentCoordinate.y && allRoomBarriers.get(i).returnUUID().equals(roomId) ) {
                    currentCoordinate.x =Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(1,2))-1;
                    return currentCoordinate;
                }
            }
            currentCoordinate.x = currentCoordinate.x + moveAmmount;
            return currentCoordinate;
        }
        //Westen
        else if (commandString.contains("we")){
            for (int i = 0; i<allRoomBarriers.size(); i++){

                if (Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(1,2))< currentCoordinate.x && Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(1,2))>= (currentCoordinate.x-moveAmmount) && Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(3,4)) == currentCoordinate.y && allRoomBarriers.get(i).returnUUID().equals(roomId) ) {
                    currentCoordinate.x =Integer.parseInt(allRoomBarriers.get(i).solvevBarrierString.substring(1,2));
                    return currentCoordinate;
                }
            }

            currentCoordinate.x = currentCoordinate.x - moveAmmount;
            if (currentCoordinate.x<0)currentCoordinate.x=0;
            return currentCoordinate;
        }
        else return null;
    }
    public Boolean reachRoomBorder(){


        return null;
    }



    public List<BarrierInRoom> dissolveAllBarrierStrings(String barrierString, UUID roomId){
        List<BarrierInRoom> solvedCoordinatesInRoom = new ArrayList<>();
        Integer firstX = Integer.parseInt(barrierString.substring(1,2));
        Integer firstY = Integer.parseInt(barrierString.substring(3,4));
        Integer secondX = Integer.parseInt(barrierString.substring(7,8));
        Integer secondY = Integer.parseInt(barrierString.substring(9,10));
        Integer smallerX;
        if (firstX<secondX){
            smallerX=firstX;
        }
        else{
            smallerX=secondX;
        }

        Integer higherX;
        if (firstX>secondX){
            higherX=firstX;
        }
        else {
            higherX=secondX;
        }
        Integer smallerY;
        if (firstY<secondY){
            smallerY=firstY;
        }
        else{
            smallerY=secondY;
        }

        Integer higherY;
        if (firstY>secondY){
            higherY=firstY;
        }
        else {
            higherY=secondY;
        }
        if (firstY==secondY){
            for (int i = smallerX; i<higherX && firstY==secondY; i++){
                String newCoordinate=("("+i+","+firstY+")");
                BarrierInRoom bir = new BarrierInRoom(newCoordinate,roomId);
                solvedCoordinatesInRoom.add(bir);
            }
        }
        else {
            for (int i = smallerY; i<higherY && firstX==secondX; i++){
                String newCoordinate1=("("+firstX+","+i+")");
                BarrierInRoom bir = new BarrierInRoom(newCoordinate1,roomId);
                solvedCoordinatesInRoom.add(bir);
            }
        }

        return solvedCoordinatesInRoom;
    }


}
