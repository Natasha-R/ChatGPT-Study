package thkoeln.st.st2praktikum.exercise;

import java.util.LinkedList;
import java.util.UUID;

public class Space {
    private UUID id;
    private int width;
    private int height;
    private LinkedList<Wall> walls;
    private LinkedList<Connector> connectors;

    public Space(int top, int span){
        id=UUID.randomUUID();
        height = top;
        width = span;
        walls = new LinkedList<>();
        connectors = new LinkedList<>();
    }

    public UUID getId() {
        return id;
    }

    public void addConnector(Connector connector){
        connectors.add(connector);
    }

    public LinkedList<Wall> getWalls() {
        return walls;
    }

    public void addWall(String command){
        String[] position = command.split("-");
        for(int i=0;i<position.length;i++){
            position[i] = position[i].substring(1,position[i].length()-1);
        }
        if(position.length==2){
            walls.add(new Wall(new Point(position[0]),new Point(position[1])));
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
