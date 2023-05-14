package thkoeln.st.st2praktikum.exercise.connection;

import java.util.*;
import java.util.stream.Collectors;

public class ConnectionMap {
    private HashMap<UUID,Connection> connectionMap;

    // important to prevent Exceptions from occuring
    public boolean connectionExists(UUID sourceRoomId,UUID destinationRoomId){
        for(Connection connection : connectionMap.values()){
            if(connection.getSourceRoom().equals(sourceRoomId) && connection.getDestinationRoom().equals(destinationRoomId)){
                return true;
            }
        }
        return false;
    }

    public UUID getConnectionByRoomIds(UUID sourceRoomId,UUID destinationRoomId){
        if(connectionExists(sourceRoomId,destinationRoomId)){
            for(Connection connection : connectionMap.values()){
                if(connection.getSourceRoom().equals(sourceRoomId) && connection.getDestinationRoom().equals(destinationRoomId)){
                    return connection.getId();
                }
            }
        }else{
            throw new RuntimeException("This Connection does not exist! Please use the \"connectionExists\" method to prevent this!");
        }
        return null;
    }

    public UUID addConnection(UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate) {
        List<Integer> sourceCoordinateIntList = parser(sourceCoordinate);
        List<Integer> destinationCoordinateIntList = parser(destinationCoordinate);
        Connection connection = new Connection(sourceRoomId, sourceCoordinateIntList.get(0),sourceCoordinateIntList.get(1),
                destinationRoomId, destinationCoordinateIntList.get(0),destinationCoordinateIntList.get(1));
        connectionMap.put(connection.getId(),connection);
        return connection.getId();
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

    public Integer getSourceX(UUID connectionId){
        return connectionMap.get(connectionId).getSourceX();
    }

    public Integer getSourceY(UUID connectionId){
        return connectionMap.get(connectionId).getSourceY();
    }

    public Integer getDestinationX(UUID connectionId){
        return connectionMap.get(connectionId).getDestinationX();
    }

    public Integer getDestinationY(UUID connectionId){
        return connectionMap.get(connectionId).getDestinationY();
    }

    public ConnectionMap(){
        connectionMap = new HashMap<UUID, Connection>();
    }
}
