package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class Room implements RoomInterface {
    private UUID roomId;
    private Integer height;
    private Integer width;
    private Cell[][] cells;

    private ArrayList<Obstacle> obstacles;
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

        obstacles = new ArrayList<Obstacle>();
        connections = new ArrayList<Connection>();
    }

    @Override
    public void addObstacles(Obstacle obstacle) throws ObstacleException {
        if(obstacle.getStart().getX() > width || obstacle.getStart().getY() > height || obstacle.getEnd().getX() > width || obstacle.getEnd().getY() > height)
            throw new ObstacleException("obstacle is out of bound");

        obstacles.add(obstacle);
    }

    @Override
    public void addConnections(Connection connection) throws ConnectionException {
        if(connection.getSourceRoomId().equals(this.roomId)) {
            if (connection.getSourceCoordinate().getX() >= width || connection.getSourceCoordinate().getY() >= height)
                throw new ConnectionException("connection is out of bound");
        }
        else if(connection.getDestinationRoomId().equals(this.roomId)){
            if(connection.getDestinationCoordinate().getX() >= width || connection.getDestinationCoordinate().getY() >= height)
                throw new ConnectionException("connection is out of bound");
        }

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

    @Override
    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    @Override
    public Connection getConnection(UUID destinationRoomId){
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
