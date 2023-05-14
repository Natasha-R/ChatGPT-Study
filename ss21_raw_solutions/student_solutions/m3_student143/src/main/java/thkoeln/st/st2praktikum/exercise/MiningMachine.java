package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class MiningMachine implements Collidable {

    @Id
    @Getter
    private final UUID id = UUID.randomUUID();

    @Embedded
    private Rectangle bounds;

    @ManyToOne
    @Setter
    @Getter
    private Field field;


    public UUID getFieldId(){
        return field.getId();
    }


    public MiningMachine(String name, Coordinate leftCorner) {
        this.bounds = new Rectangle(
                leftCorner,
                new Dimension(1,1)
        );
    }


    public Coordinate nextCoordinate(Direction direction) {
        Coordinate coordinate = bounds.getCoordinate();
        switch (direction){
            case NORTH: return new Coordinate(coordinate.getX(), coordinate.getY()+1);
            case SOUTH: return new Coordinate(coordinate.getX(), coordinate.getY()-1);
            case EAST:  return new Coordinate(coordinate.getX() + 1 , coordinate.getY());
            case WEST:  return new Coordinate(coordinate.getX() - 1, coordinate.getY());
        }
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.bounds = new Rectangle(
                coordinate,
                this.bounds.getDimension()
        );
    }

    public Coordinate getCoordinate() {
        return this.bounds.getCoordinate();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public String toString() {
        return this.bounds.getCoordinate().toString();
    }
};