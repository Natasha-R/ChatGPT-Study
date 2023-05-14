package thkoeln.st.st2praktikum.exercise.space;


import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Obstacle;
import thkoeln.st.st2praktikum.exercise.connection.Connection;

import javax.persistence.*;
import java.util.*;

@Getter
@Entity
public class Space {

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    final Map<UUID, Connection> connections = new HashMap<>();
    @Id
    private final UUID spaceId = UUID.randomUUID();
    @OneToMany(cascade = {CascadeType.ALL})
    private final List<Obstacle> obstacles = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    private final List<Coordinate> coordinates = new ArrayList<>();
    private Integer width;
    private Integer height;


    public Space(Integer height, Integer width) {
        this.width = width;
        this.height = height;
        this.fillCoordinates();
    }

    protected Space() {
    }

    public void addBarrier(Obstacle obstacle) {
        this.obstacles.add(obstacle);
    }

    public void addConnection(Connection connection) throws RuntimeException {
        this.connections.put(connection.getConnectionId(), connection);
    }

    private void fillCoordinates() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                coordinates.add(new Coordinate(x, y));
            }
        }
    }
}
