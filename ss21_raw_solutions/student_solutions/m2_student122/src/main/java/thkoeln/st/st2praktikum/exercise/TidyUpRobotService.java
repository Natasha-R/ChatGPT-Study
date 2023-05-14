package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
        List<Obstacle> obstacleList = new ArrayList<>();
        List<Obstacle> roomObstacleList = room.getObstacleList();

        obstacleList.add(new Obstacle(String.format("(0,0)-(0,%d)",room.getHeight())));
        obstacleList.add(new Obstacle(String.format("(0,0)-(%d,0)",room.getWidth())));
        obstacleList.add(new Obstacle(String.format("(0,%d)-(%d,%d)",room.getWidth(),room.getHeight(),room.getWidth())));
        obstacleList.add(new Obstacle(String.format("(%d,0)-(%d,%d)",room.getHeight(),room.getHeight(),room.getWidth())));


        roomObstacleList.addAll(obstacleList);

        room.setObstacleList(roomObstacleList);


        return room;

    }

    /**
     * This method adds a obstacle to a given room.
     * @param roomId the ID of the room the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID roomId, Obstacle obstacle) {
        Room room = findRoomWithUuidAsId(roomId);
        List<Obstacle> roomObstacleList = room.getObstacleList();
        roomObstacleList.add(obstacle);
        room.setObstacleList(roomObstacleList);
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
     * @param sourcePoint the points of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationPoint the points of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceRoomId, Point sourcePoint, UUID destinationRoomId, Point destinationPoint) {
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

        if(     sourcePoint.getX() > room1.getWidth() ||
                sourcePoint.getY() > room1.getHeight() ||
                destinationPoint.getX() > room2.getWidth() ||
                destinationPoint.getY() > room2.getHeight() ||
                destinationPoint.getX() < 0 ||
                destinationPoint.getY() < 0 ||
                sourcePoint.getX() < 0 ||
                sourcePoint.getY() < 0
        ){
            throw new RuntimeException();
        }




        Connection connection = new Connection(sourceRoomId,sourcePoint,destinationRoomId,destinationPoint);
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



    public Boolean checkSameCoordinate(Point firstCoordinate,Point secondCoordinate){
        return firstCoordinate.equals(secondCoordinate);
    }

    public String[] getCommandAsArray(String commandString){
        return commandString.replace("[","").replace("]","").split(",");
    }

    public Boolean enterTheRoom(TidyUpRobot tidyUpRobot,String roomId){
        Room foundRoom = findRoomWithStringAsId(roomId);
        if(foundRoom==null) return false;

        for(TidyUpRobot someRobot: tidyUpRobots) if(someRobot.getLocY() != null && someRobot.getLocX() != null) if(someRobot.getLocX() == 0 && someRobot.getLocY() == 0) return false;
        tidyUpRobot.spawn(foundRoom);
        return true;
    }

    public List<Point> getPointsFromObstacle(Obstacle obstacle){
        List<Point> pointList = new ArrayList<>();
        if(obstacle.getStart().getX().equals(obstacle.getEnd().getX())){
            for(int i = obstacle.getStart().getY(); i<=obstacle.getEnd().getY(); i++){
                pointList.add(new Point(obstacle.getStart().getX(),i));
            }
        }
        else{
            for(int i = obstacle.getStart().getX(); i<=obstacle.getEnd().getX(); i++){
                pointList.add(new Point(i,obstacle.getStart().getY()));
            }
        }
        return pointList;
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a obstacle or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        TidyUpRobot tidyUpRobot = findTidyUpRobot(tidyUpRobotId);

        switch (task.getTaskType()){
            case ENTER:
                System.out.println("ENTERED");
                return enterTheRoom(tidyUpRobot,task.getGridId().toString());
            case TRANSPORT:
                System.out.println(tidyUpRobot.getRoom().getId());
                System.out.println(task.getGridId());
                System.out.println(tidyUpRobot.getCoordinates());
                boolean sameCoordinate = false;
                for (Connection connector : connections) {
                    if (checkSameCoordinate(getPoint(tidyUpRobotId), connector.getSourceCoordinate())) {
                        System.out.println("IF1");
                        if (connector.getDestinationRoomId().equals(task.getGridId())) {
                            System.out.println("CONNECTOR SAME");
                            for (Room room : rooms) {
                                System.out.println("ROOM");
                                if (room.getId() == connector.getDestinationRoomId()) {
                                    System.out.println("IF2");
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
            case EAST:
                for (int i = 0; i < task.getNumberOfSteps(); i++) {
                    List<Point> edge = tidyUpRobot.getEastEdge();
                    searchObstacle:
                    {
                        for (Obstacle obstacle : tidyUpRobot.getRoom().getObstacleList()) {
                            List<Point> obstacles = getPointsFromObstacle(obstacle);
                            if(obstacles.contains(edge.get(0))&&obstacles.contains(edge.get(1))){
                                break searchObstacle;
                            }
                        }
                        tidyUpRobot.moveX(1);
                    }
                }
                break;
            case WEST:
                for (int i = 0; i < task.getNumberOfSteps(); i++) {
                    List<Point> edge = tidyUpRobot.getWestEdge();
                    searchObstacle:
                    {
                        for (Obstacle obstacle : tidyUpRobot.getRoom().getObstacleList()) {
                            List<Point> obstacles = getPointsFromObstacle(obstacle);
                            if(obstacles.contains(edge.get(0))&&obstacles.contains(edge.get(1))){
                                break searchObstacle;
                            }
                        }
                        tidyUpRobot.moveX(-1);
                    }
                }
                System.out.println(tidyUpRobot.getCoordinates());
                break;
            case SOUTH:
                for (int i = 0; i < task.getNumberOfSteps(); i++) {
                    List<Point> edge = tidyUpRobot.getSouthEdge();
                    searchObstacle:
                    {
                        for (Obstacle obstacle : tidyUpRobot.getRoom().getObstacleList()) {
                            List<Point> obstacles = getPointsFromObstacle(obstacle);
                            if(obstacles.contains(edge.get(0))&&obstacles.contains(edge.get(1))){
                                break searchObstacle;
                            }
                        }
                        tidyUpRobot.moveY(-1);
                    }
                }
                break;
            case NORTH:
                System.out.println("NORD STEPS "+task.getNumberOfSteps());
                for (int i = 0; i < task.getNumberOfSteps(); i++) {
                    List<Point> edge = tidyUpRobot.getNordEdge();
                    searchObstacle:
                    {

                        for (Obstacle obstacle : tidyUpRobot.getRoom().getObstacleList()) {
                            //System.out.println("OBSTACLES "+obstacle.getStart().toString()+","+obstacle.getEnd().toString());
                            List<Point> obstacles = getPointsFromObstacle(obstacle);
                            //System.out.println("EDGES "+ edge.get(0)+","+edge.get(1));

                            if(getPointsFromObstacle(obstacle).contains(edge.get(0))&&obstacles.contains(edge.get(1))){
                                break searchObstacle;
                            }
                        }
                        tidyUpRobot.moveY(1);
                    }
                }
                break;

            default:
                return false;
        }
        return false;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        TidyUpRobot tidyUpRobot = findTidyUpRobot(tidyUpRobotId);

        return tidyUpRobot.getRoom().getId();
    }

    /**
     * This method returns the points a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the points of the tidy-up robot
     */
    public Point getPoint(UUID tidyUpRobotId){
        TidyUpRobot tidyUpRobot = findTidyUpRobot(tidyUpRobotId);
        return new Point(tidyUpRobot.getCoordinates());
    }
}
