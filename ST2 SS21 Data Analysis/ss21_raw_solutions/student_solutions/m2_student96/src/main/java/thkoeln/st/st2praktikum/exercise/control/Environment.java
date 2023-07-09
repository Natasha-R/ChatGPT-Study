package thkoeln.st.st2praktikum.exercise.control;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.droids.Droid;
import thkoeln.st.st2praktikum.exercise.map.Connection;
import thkoeln.st.st2praktikum.exercise.map.Grid;
import thkoeln.st.st2praktikum.exercise.Point;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class Environment {
    private HashMap<UUID, Grid> grids = new HashMap<>();
    private HashMap<UUID, Droid> droids = new HashMap<>();
    private HashMap<UUID, Connection> connections = new HashMap<>();

    //Singleton Pattern Quelle: https://www.youtube.com/watch?v=3cJbDs-zzpw
    //private static Environment environment = new Environment();

    public UUID addDroid(String name){
        Droid newDroid = new Droid(name);
        droids.put(newDroid.getDroidID(), newDroid);
        return newDroid.getDroidID();
    }

    public UUID addGrid(int height, int width) {
        Grid newDeck = new Grid(height, width);
        addGrid(newDeck);
        return newDeck.getGridID();
    }

    public UUID addGrid(Grid grid){
        grids.put(grid.getGridID(), grid);
        return grid.getGridID();
    }

    public UUID addConnection(Connection connection){
        connections.put(connection.getConnectionID(), connection);
        return connection.getConnectionID();
    }

    public Point getPointFromGrid(UUID gridID, String coordinateString){
        return getGrids().get(gridID).getPointFromGrid(coordinateString);
    }

    public HashMap<String, Point> getPoints(UUID deckID){
        return getGrids().get(deckID).getPoints();
    }
}