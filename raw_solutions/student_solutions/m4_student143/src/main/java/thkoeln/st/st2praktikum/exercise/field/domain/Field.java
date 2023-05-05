package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Dimension;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity
public class Field {

    @Id
    @Getter
    private final UUID id = UUID.randomUUID();

    @Embedded
    private Dimension dimension;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Wall.class)
    private List<Collidable> walls;

    public void addWall(Wall wall){
        walls.add(wall);
    }
    public List<Collidable> getCollidables(){ return new ArrayList<>(walls); }


    public Field(Dimension dimension){
        this.dimension = dimension;
        walls = new ArrayList<>();

        walls.add(new Wall(new Coordinate(0,0), new Coordinate(dimension.getWidth(),0)));
        walls.add(new Wall(new Coordinate(0,0), new Coordinate(0,dimension.getHeight())));
        walls.add(
                new Wall(
                        new Coordinate(dimension.getWidth(),0),
                        new Coordinate(dimension.getWidth(),dimension.getHeight())
                )
        );
        walls.add(
                new Wall(
                        new Coordinate(0,dimension.getHeight()),
                        new Coordinate(dimension.getWidth(),dimension.getHeight())
                )
        );
    }

}