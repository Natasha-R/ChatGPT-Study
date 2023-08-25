package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connector;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class SpaceShipDeck {
    @Id
    private UUID uuid;
    private int width;
    private int height;

    @OneToMany
    private List<Obstacle> obstacles;

    @OneToMany
    private List<Connector> connectors;

    public SpaceShipDeck(Integer top, Integer span) {
        uuid=UUID.randomUUID();
        height = top;
        width = span;
        obstacles = new ArrayList<>();
        connectors = new ArrayList<>();
    }

    public UUID getId() {
        return uuid;
    }

    public void addConnector(Connector connector){
        connectors.add(connector);
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void addObstacle(Obstacle obstacle){
        obstacles.add(obstacle);
    }
    public void addObstacle(String task){
        String[] position = task.split("-");
        for(int i=0;i<position.length;i++){
            position[i] = position[i].substring(1,position[i].length()-1);
        }
        if(position.length==2){
            obstacles.add(new Obstacle(Vector2D.fromString(position[0]),Vector2D.fromString(position[1])));
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
