package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domains.Connection;
import thkoeln.st.st2praktikum.exercise.domains.Obstacle;
import thkoeln.st.st2praktikum.exercise.domains.Room;
import thkoeln.st.st2praktikum.exercise.domains.TidyUpRobot;

import java.util.*;


public class TidyUpRobotService {

    @Getter
    private final List<Room> rooms = new ArrayList<>();
    @Getter
    private final List<Connection> connections = new ArrayList<>();
    @Getter
    private final List<TidyUpRobot> tidyUpRobots = new ArrayList<>();



    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(width,height);

        room = addRoomBorder(room);

        rooms.add(room);

        return room.getId();
    }

    public Room addRoomBorder(Room room){
        List<String> borders = new ArrayList<>();

        borders.add("("+0+","+0+")-("+0+","+room.getHeight()+")"); //South Border
        borders.add("("+0+","+0+")-("+room.getWidth()+","+0+")"); //West Border
        borders.add("("+0+","+room.getWidth()+")-("+room.getHeight()+","+room.getWidth()+")"); //East Border
        borders.add("("+room.getHeight()+","+0+")-("+room.getHeight()+","+room.getWidth()+")"); //North Border

        List<Obstacle> obstacleList = room.getObstacleList();

        for(String s: borders){

            List<String> obstaclesAsListOfStrings = getObstaclesAsList(s);

            for(String obstacle : obstaclesAsListOfStrings){

                Obstacle newObstacle = new Obstacle(room.getId(),obstacle);

                newObstacle.setIsHorizontal(isHorizontal(s));

                obstacleList.add(newObstacle);

            }

            room.setObstacleList(obstacleList);

        }

        return room;

    }

    /**
     * This method adds a obstacle to a given room.
     * @param roomId the ID of the room the obstacle shall be placed on
     * @param obstacleString the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     */
    public void addObstacle(UUID roomId, String obstacleString) {

        Room room = findRoomWithUuidAsId(roomId);

        List<String> obstaclesAsListOfStrings = getObstaclesAsList(obstacleString);

        List<Obstacle> obstacleList = room.getObstacleList();

        for(String obstacle: obstaclesAsListOfStrings){

            Obstacle newObs = new Obstacle(roomId,obstacle);

            newObs.setIsHorizontal(isHorizontal(obstacleString));

            obstacleList.add(newObs);

        }

        room.setObstacleList(obstacleList);

    }
    public Room findRoomWithUuidAsId(UUID roomId){

        for(Room room : rooms) if(room.getId() == roomId) return room;

        throw new RuntimeException("Room NOT FOUND");
    }

    public Room findRoomWithStringAsId(String idString){
        for(Room room : rooms) if(room.getId().toString().equals(idString)) return room;

        return null;
    }

    private Boolean isHorizontal(String edge){// (2,3)-(4,3) will be true
         String[] numberArrayAsStringArray = edge
                 .replace("-",",")
                 .replace("(","")
                 .replace(")","")
                 .split(",");

         return !numberArrayAsStringArray[0].equals(numberArrayAsStringArray[2]);
    }

    private List<String> getObstaclesAsList(String obstacleString){
        List<String> listOfEdgesAsStrings = new ArrayList<>();

        HashMap<String, HashMap<String,String>> obstacleHashmap = getPointsFromObstacleString(obstacleString);


        String direction = (String) obstacleHashmap.keySet().toArray()[0];
        String nonChangingAxis = (String) obstacleHashmap.get(direction).keySet().toArray()[0];
        String[] edgeArrayOfHashmap = getEdges(obstacleHashmap.get(direction).get(nonChangingAxis));

        for(String edge: edgeArrayOfHashmap){
            String obsWithAxis = (nonChangingAxis+"-"+edge);
            listOfEdgesAsStrings.add(obsWithAxis);
        }

        return listOfEdgesAsStrings;
    }

    private HashMap<String, HashMap<String,String>> getPointsFromObstacleString(String obstacleString){
        String direction;
        String nonChangingAxis;
        StringBuilder stringBuilder = new StringBuilder();
        String[] points = obstacleString.split("-");
        String[] leftPoint = points[0].replace("(","").replace(")","").split(",");
        String[] rightPoint = points[1].replace("(","").replace(")","").split(",");

        if(!leftPoint[0].equals(rightPoint[0])){
            direction = "horizontal";
            nonChangingAxis = leftPoint[1];
            for(int i = Integer.parseInt(leftPoint[0]); i<=Integer.parseInt(rightPoint[0]);i++){
                stringBuilder.append(i);
                stringBuilder.append(",");
            }
        }else{
            direction = "vertical";
            nonChangingAxis = leftPoint[0];
            for(int i = Integer.parseInt(leftPoint[1]); i<=Integer.parseInt(rightPoint[1]);i++){
                stringBuilder.append(i);
                stringBuilder.append(",");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        HashMap<String,String> nonChangingAxisAndPointsOnObstacle = new HashMap<>();
        HashMap<String,HashMap<String,String>> obstacleWithAllAttributes = new HashMap<>();
        nonChangingAxisAndPointsOnObstacle.put(nonChangingAxis,stringBuilder.toString());
        obstacleWithAllAttributes.put(direction,nonChangingAxisAndPointsOnObstacle);
        System.out.println(nonChangingAxisAndPointsOnObstacle);

        return obstacleWithAllAttributes;
    }

    private String[] getEdges(String str){
        String[] edgeStringAsArray = str.split(",");
        String[] edgeArrayOfStrings = new String[edgeStringAsArray.length-1];

        for(int i = 0; i< edgeArrayOfStrings.length;i++){
            edgeArrayOfStrings[i] = ("(" + edgeStringAsArray[i] + ","+edgeStringAsArray[i+1]+")");
        }
        return edgeArrayOfStrings;
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
        Room room1=null,room2=null;
        for(Room room : rooms){
            if(room.getId()==sourceRoomId){
                room1 = room;
            }
            else if(room.getId()==destinationRoomId){
                room2=room;
            }
        }
        if(room1==null || room2==null){
            throw new RuntimeException("One or both of the rooms are not found!");
        }
        Connection connection = new Connection(sourceRoomId,sourceCoordinate,destinationRoomId,destinationCoordinate);
        room1.setConnection(connection);

        connections.add(connection);

        return connection.getId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name);
        tidyUpRobots.add(tidyUpRobot);
        return tidyUpRobot.getId();
    }

    public TidyUpRobot findTidyUpRobot(UUID tidyUpRobotId){
        for(TidyUpRobot tidyUpRobot : tidyUpRobots){
            if(tidyUpRobot.getId() == tidyUpRobotId){
                return tidyUpRobot;
            }
        }
        throw new RuntimeException("TidyUpRobot not found!");
    }

    public Boolean checkSameCoordinate(String firstCoordinate,String secondCoordinate){
        return firstCoordinate.equals(secondCoordinate);
    }
    public String[] getCommandAsArray(String commandString){
        return commandString.replace("[","").replace("]","").split(",");
    }

    public Boolean enterTheRoom(TidyUpRobot tidyUpRobot,String roomId){
        Room foundRoom = findRoomWithStringAsId(roomId);
        if(foundRoom==null){
            return false;
        }
        for(TidyUpRobot someRobot: tidyUpRobots){
            if(someRobot.getLocY() != null && someRobot.getLocX() != null){
                if(someRobot.getLocX() == 0 && someRobot.getLocY() == 0){
                    return false;
                }
            }
        }
        tidyUpRobot.spawn(foundRoom);
        return true;
    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,room-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on squares with a connection to another room
     * "[en,<room-id>]" for setting the initial room where a tidy-up robot is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a obstacle or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, String commandString) {
        TidyUpRobot tidyUpRobot = findTidyUpRobot(tidyUpRobotId);

        String[] commandArray = getCommandAsArray(commandString);

        switch (commandArray[0]) {
            case "en":
                return enterTheRoom(tidyUpRobot, commandArray[1]);
            case "tr":
                boolean sameCoordinate = false;
                for (Connection connector : connections) {
                    if (checkSameCoordinate(getCoordinates(tidyUpRobotId), connector.getSourceCoordinate())) {
                        if (connector.getDestinationRoomId().toString().equals(commandArray[1])) {
                            for (Room room : rooms) {
                                if (room.getId() == connector.getDestinationRoomId()) {
                                    tidyUpRobot.transport(connector, room);
                                }
                            }
                        }
                        sameCoordinate = true;
                    }
                }

                if (!sameCoordinate) {
                    return false;
                }
                break;
            case "so":
                for (int i = 0; i < Integer.parseInt(commandArray[1]); i++) {
                    String edgeString = tidyUpRobot.getSouthEdgeString();
                    searchObstacle:
                    {
                        for (Obstacle obstacle : tidyUpRobot.getRoom().getObstacleList()) {
                            if (obstacle.getObstacle().equals(edgeString) && obstacle.getIsHorizontal()) {
                                break searchObstacle;
                            }
                        }
                        tidyUpRobot.setLocY(tidyUpRobot.getLocY() - 1);
                    }
                }
                break;
            case "no":
                for (int i = 0; i < Integer.parseInt(commandArray[1]); i++) {

                    String edgeString = tidyUpRobot.getNordEdgeString();

                    searchObstacle:
                    {
                        for (Obstacle obstacle : tidyUpRobot.getRoom().getObstacleList()) {
                            if (obstacle.getObstacle().equals(edgeString) && obstacle.getIsHorizontal()) {
                                break searchObstacle;
                            }
                        }
                        tidyUpRobot.setLocY(tidyUpRobot.getLocY() + 1);
                    }
                }
                break;
            case "ea":
                for (int i = 0; i < Integer.parseInt(commandArray[1]); i++) {
                    String edgeString = tidyUpRobot.getEastEdgeString();
                    searchObstacle:
                    {
                        for (Obstacle obstacle : tidyUpRobot.getRoom().getObstacleList()) {
                            if (obstacle.getObstacle().equals(edgeString) && !obstacle.getIsHorizontal()) {
                                break searchObstacle;
                            }
                        }
                        tidyUpRobot.moveX(1);
                    }
                }
                break;
            case "we":
                for (int i = 0; i < Integer.parseInt(commandArray[1]); i++) {
                    String edgeString = tidyUpRobot.getWestEdgeString();
                    searchObstacle:
                    {
                        for (Obstacle obstacle : tidyUpRobot.getRoom().getObstacleList()) {
                            if (obstacle.getObstacle().equals(edgeString) && !obstacle.getIsHorizontal()) {
                                break searchObstacle;
                            }
                        }
                        tidyUpRobot.moveX(-1);
                    }
                }
                break;
        }
        return true;
    }
    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        for(TidyUpRobot tud : tidyUpRobots){
            if(tud.getId().equals(tidyUpRobotId)){
                return tud.getRoom().getId();
            }
        }
        throw new RuntimeException("RoomId not found!");
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordiantes of the tidy-up robot encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID tidyUpRobotId){
        for(TidyUpRobot tidyUpRobot : tidyUpRobots){
            if(tidyUpRobot.getId().equals(tidyUpRobotId)){
                return tidyUpRobot.getCoordinates();
            }
        }

        throw new RuntimeException("Coordinates not found!");
    }
}
