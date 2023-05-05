package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Space {
    @Id
    private UUID uuid;
    private int width;
    private int height;

    @OneToMany
    private List<Wall> walls;

    @OneToMany
    private List<Connector> connectors;

    public Space(int top, int span){
        uuid=UUID.randomUUID();
        height = top;
        width = span;
        walls = new ArrayList<>();
        connectors = new ArrayList<>();
    }

    public UUID getId() {
        return uuid;
    }

    public void addConnector(Connector connector){
        connectors.add(connector);
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public void addWall(Wall wall){
        walls.add(wall);
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

    public List<Connector> getConnectors() {
        return connectors;
    }

    public int getwidth() {
        return width;
    }

    public int getheight() {
        return height;
    }
}
