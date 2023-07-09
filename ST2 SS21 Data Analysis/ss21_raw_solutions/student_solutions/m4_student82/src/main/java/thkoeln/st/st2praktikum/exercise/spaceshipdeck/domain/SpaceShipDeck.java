package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class SpaceShipDeck extends AbstractEntity {

    @Id
    private final UUID spaceShipDeckId = UUID.randomUUID();

    private Integer height;

    private Integer width;

    @Transient
    @Setter
    private final List<Wall> walls = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    private final List<Coordinate> coordinates = new ArrayList<>();


    public SpaceShipDeck(Integer height, Integer width) {
        this.height = height;
        this.width = width;
        this.createField();
    }

    private void createField(){
        for(int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Coordinate temp = new Coordinate(x,y);
                coordinates.add(temp);
            }
        }
    }

    public void addWall(Wall wall){
        walls.add(wall);
    }

    private SpaceShipDeck getThis() {
        return this;
    }
}
