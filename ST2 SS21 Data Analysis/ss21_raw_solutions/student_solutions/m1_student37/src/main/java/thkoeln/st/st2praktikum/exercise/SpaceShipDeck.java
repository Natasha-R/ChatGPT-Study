package thkoeln.st.st2praktikum.exercise;

import java.util.LinkedList;
import java.util.UUID;

public class SpaceShipDeck {
    private UUID id;
    private int width;
    private int height;
    private LinkedList<Obstacle> obstacles;
    private LinkedList<Connector> connectors;

    public SpaceShipDeck(int top, int span){
        id=UUID.randomUUID();
        height = top;
        width = span;
        obstacles = new LinkedList<>();
        connectors = new LinkedList<>();
    }

    public UUID getId() {
        return id;
    }

    public void addConnector(Connector connector){
        connectors.add(connector);
    }

    public LinkedList<Obstacle> getObstacles() {
        return obstacles;
    }

    public void addObstacle(String command){
        String[] position = command.split("-");
        for(int i=0;i<position.length;i++){
            position[i] = position[i].substring(1,position[i].length()-1);
        }
        if(position.length==2){
            obstacles.add(new Obstacle(new Point(position[0]),new Point(position[1])));
        }else if(position.length<2){
            throw new IllegalStateException();
        }else{
            throw new UnsupportedOperationException();
        }
    }

    public LinkedList<Connector> getConnectors() {
        return connectors;
    }

    public int getwidth() {
        return width;
    }

    public int getheight() {
        return height;
    }
}

