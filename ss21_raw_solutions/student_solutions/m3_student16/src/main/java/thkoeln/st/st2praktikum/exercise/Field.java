package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
public class Field {
    @Id
    private final UUID fieldId = UUID.randomUUID();
    private Integer height;
    private Integer width;
    @ElementCollection( targetClass = Wall.class )
    private List<Wall> walls = new ArrayList<>();
    @OneToMany
    private List<Connection> connections = new ArrayList<>();

    public Field(Integer height, Integer width){
        this.height = height;
        this.width = width;
    }

    public Field() {
    }

    public void addConnection(Connection connection){
        connections.add(connection);
    }

    public boolean coordinateIsNotOutOfBounds(Coordinate coordinate){
        Integer coordinateX = coordinate.getX();
        Integer coordinateY = coordinate.getY();
        return coordinateX <= width && coordinateY <= height && coordinateX >= 0 && coordinateY >= 0;
    }

    public void addWall(Wall wall){ walls.add(wall); }
}
