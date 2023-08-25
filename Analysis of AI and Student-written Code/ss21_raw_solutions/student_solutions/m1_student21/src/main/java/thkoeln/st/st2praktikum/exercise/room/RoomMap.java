package thkoeln.st.st2praktikum.exercise.room;

import java.util.*;
import java.util.stream.Collectors;

public class RoomMap {

    HashMap<UUID, Room> roomMap;


    public boolean canIGoThere(UUID roomId,String direction,int x,int y){
        return roomMap.get(roomId).canIGoThere(direction,x,y);
    }

    public UUID addRoom(Integer height,Integer width){
        Room room = new Room(height,width);
        roomMap.put(room.getId(),room);
        return room.getId();
    }

    public void addObstacle(UUID roomId,String obstacleString){
        List<Integer> numberList = parser(obstacleString);
        roomMap.get(roomId).addObstacle(numberList.get(0),numberList.get(1),numberList.get(2),numberList.get(3));
    }

    public List<Integer> parser(String inputString){
        List<Integer> numberList = new ArrayList<>();
        String[] numberString = inputString.replaceAll("[()]","").split("[,-]");
        try {
            numberList = Arrays.stream(numberString).map(string -> Integer.parseInt(string)).collect(Collectors.toList());
            return numberList;
        } catch(Exception e){
            System.out.println("ERROR: Could not parse through InputString");
            System.out.println("Example of allowed inputs: (10,2) or (0,12)-(21-12)");
            System.out.println("Your inputString: "+inputString);
            return numberList;
        }
    }

    public Room getRoomById(UUID roomId){
        return roomMap.get(roomId);
    }

    public RoomMap(){
        roomMap = new HashMap<>();
    }
}
