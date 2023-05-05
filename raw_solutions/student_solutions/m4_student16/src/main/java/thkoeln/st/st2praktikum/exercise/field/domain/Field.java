package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;

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


    public Field(Integer height, Integer width){
        this.height = height;
        this.width = width;
    }

    public Field() {
    }

    public boolean coordinateIsNotOutOfBounds(Coordinate coordinate){
        Integer coordinateX = coordinate.getX();
        Integer coordinateY = coordinate.getY();
        return coordinateX <= width && coordinateY <= height && coordinateX >= 0 && coordinateY >= 0;
    }

    public void addWall(Wall wall){ walls.add(wall); }
}
