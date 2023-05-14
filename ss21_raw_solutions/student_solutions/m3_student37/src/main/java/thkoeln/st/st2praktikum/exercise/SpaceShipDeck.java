package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class SpaceShipDeck {
    @Id
    private UUID id;
    private int width;
    private int height;
    @OneToMany
    private List<Obstacle> obstacles;
    @OneToMany
    private List<Connector> connectors;

    public SpaceShipDeck(int top, int span){
        id=UUID.randomUUID();
        height = top;
        width = span;
        obstacles = new ArrayList<>();
        connectors = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void addConnector(Connector connector){
        connectors.add(connector);
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
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
