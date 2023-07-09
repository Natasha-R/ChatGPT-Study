package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
//@NoArgsConstructor
@AllArgsConstructor
public class SpaceShipDeck {

    @Id
    @Getter
    private UUID id;

    @Getter
    @Setter
    private Integer width;

    @Getter
    @Setter
    private Integer height;

    @Getter
    @Embedded
    private List<Wall> walls;

    @Getter
    @Embedded
    private List<Vector2D> blocked;

    @Getter
    @OneToMany
    private List<Connection> connections;

    public void addConnections(Connection connection) {
        if (connections == null) {
            connections = new ArrayList<>();
        }
        connections.add(connection);
    }


    public void addBlocked(Vector2D vector2D) {
        if (blocked == null) {
            blocked = new ArrayList<>();
        }
        blocked.add(vector2D);
    }

    public void removeBlocked(Vector2D vector2D) {
        if (blocked != null)
            blocked.remove(vector2D);
    }

    public void setWalls(Wall wall) {
        if (walls == null) {
            walls = new ArrayList<>();
        }
        walls.add(wall);
    }

    public List<Wall> getxWalls() {
        List<Wall> xWalls = new ArrayList<>();

        if (walls == null) {
            return xWalls;
        }

        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).getStart().getY().equals(walls.get(i).getEnd().getY())) {
                xWalls.add(walls.get(i));
            }
        }
        return xWalls;
    }

    public List<Wall> getyWalls() {
        List<Wall> yWalls = new ArrayList<>();

        if (walls == null) {
            return yWalls;
        }

        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).getStart().getX().equals(walls.get(i).getEnd().getX())) {
                yWalls.add(walls.get(i));
            }
        }
        return yWalls;
    }

    public Boolean isFieldFree(Integer x, Integer y, MaintenanceDroidRepository maintenanceDroidRepository) {
        //Überprüft Grenzen des Decks
        if (x < 0 || x > width)
            return false;
        if (y < 0 || y > height)
            return false;

        //Überprüft das sich kein anderer Droid auf dem Feld befindet
        if (blocked != null) {
            for (int i = 0; i < blocked.size(); i++) {
                if (blocked.get(i).getX().equals(x) && blocked.get(i).getY().equals(y)) {
                    return false;
                }
            }
        }

        return true;
    }

    public SpaceShipDeck() {
        this.id = UUID.randomUUID();
    }
}
