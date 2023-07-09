package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connector;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Space {
    @Id
    private UUID uuid;
    private int width;
    private int height;

    @OneToMany
    private List<Wall> walls;

    @OneToMany
    private List<Connector> connectors;

    public Space(Integer top, Integer span) {
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
            walls.add(new Wall(Point.fromString(position[0]),Point.fromString(position[1])));
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
