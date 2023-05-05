package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class Room implements RoomInterface{
    private UUID roomId;
    private Integer height;
    private Integer width;
    private Cell[][] cells;

    private ArrayList<String> obstacleStrings;
    private ArrayList<Pair<Integer,Integer>> obstacleStartCoordinates;
    private ArrayList<Pair<Integer,Integer>> obstacleEndCoordinates;
    private ArrayList<Connection> connections;

    public Room(Integer height, Integer width){
        this.height = height;
        this.width = width;
        this.roomId = UUID.randomUUID();
        this.cells = new Cell[width][height];
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                cells[i][j] = new Cell();
            }
        }

        obstacleStrings = new ArrayList<String>();
        obstacleStartCoordinates = new ArrayList<Pair<Integer, Integer>>();
        obstacleEndCoordinates = new ArrayList<Pair<Integer, Integer>>();
        connections = new ArrayList<Connection>();
    }

    @Override
    public void addObstacle(String obstacleString){
        obstacleStrings.add(obstacleString);

        String[] splitString = obstacleString.split("-");
        String coordinateWithoutBrackets = splitString[0].replace("(","").replace(")","");

        Integer x = Integer.parseInt(coordinateWithoutBrackets.replaceAll(",.*",""));
        Integer y = Integer.parseInt(coordinateWithoutBrackets.replaceAll(".*,",""));
        obstacleStartCoordinates.add(new Pair<>(x,y));

        coordinateWithoutBrackets = splitString[1].replace("(","").replace(")","");

        Integer x1 = Integer.parseInt(coordinateWithoutBrackets.replaceAll(",.*",""));
        Integer y1 = Integer.parseInt(coordinateWithoutBrackets.replaceAll(".*,",""));
        obstacleEndCoordinates.add(new Pair(x1,y1));
    }

    @Override
    public void addConnections(Connection connection) {
        connections.add(connection);
    }

    @Override
    public Integer getHeight() {
        return height;
    }

    @Override
    public Integer getWidth() {
        return width;
    }

    @Override
    public UUID getRoomId() {
        return roomId;
    }

    public ArrayList<String> getObstacleStrings(){
        return obstacleStrings;
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> getObstacleStartCoordinate() {
        return obstacleStartCoordinates;
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> getObstacleEndCoordinate() {
        return obstacleEndCoordinates;
    }

    @Override
    public Connection getConnection(UUID destinationRoomId) {
        for(int i=0; i<connections.size(); i++){
            if(connections.get(i).getDestinationRoomId().equals(destinationRoomId)){
                return connections.get(i);
            }
        }
        return null;
    }

    @Override
    public Cell[][] getCell() {
        return cells;
    }
}
