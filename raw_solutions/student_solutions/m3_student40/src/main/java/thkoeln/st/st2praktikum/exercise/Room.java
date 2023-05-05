package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.TidyUpRobot.TidyUpRobot;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity
public class Room {

    private int width, height;

    private Tile[][] tiles;

    @Getter
    @Id
    private UUID id = UUID.randomUUID();

    @OneToMany
    private List<TidyUpRobot> robots = new ArrayList<>();

    public Room(int width, int height){
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        for(int w = 0; w < width; w++){
            for(int h = 0; h < height; h++){
                tiles[w][h] = new Tile();
            }
        }
    }

    public Tile getTile(Coordinate coordinate) {
        if (coordinate.getX() >= this.width || coordinate.getY() >= this.height)
            throw new UnsupportedOperationException("Tile not in room");

        return this.tiles[coordinate.getX()][coordinate.getY()];
    }

    public boolean isInBoundaries(Coordinate checkPoint){
        return checkPoint.getX() < width && checkPoint.getY() < height && checkPoint.getX() >= 0 && checkPoint.getY() >= 0;
    }

    public void addWall(Wall wall) {
        if(!isInBoundaries(wall.getStart()) || !isInBoundaries(wall.getEnd())){
            throw new RuntimeException("Wall must be placed within the rooms boundaries");
        }
        if (wall.getStart().getX() == wall.getEnd().getX()) { // check if start and end x are the same. if so we have a vertical
            for (int start = wall.getStart().getY(); start < wall.getEnd().getY(); start++) {
                tiles[wall.getStart().getX()][start] = wall;
            }
        } else {
            for (int start = wall.getStart().getX(); start < wall.getEnd().getX(); start++) {
                tiles[start][wall.getStart().getY()] = wall;
            }
        }
    }
    public void addConnection(Coordinate position, Connection connection){
        if(!isInBoundaries(position)){
            throw new RuntimeException("Connection must be placed within the rooms boundaries");
        }
        this.tiles[position.getX()][position.getY()] = connection;
    }
    public void addRobot(TidyUpRobot robot){
        robots.add(robot);
    }
}
