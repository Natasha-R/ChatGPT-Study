package thkoeln.st.st2praktikum.exercise.room.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.room.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepo roomRepo;


    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(width,height);

        room = addRoomBorder(room);

        roomRepo.save(room);

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
        return roomRepo.findRoomById(roomId);
    }

    public Room findRoomWithStringAsId(String idString){
        return roomRepo.findRoomById(UUID.fromString(idString));
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

}
