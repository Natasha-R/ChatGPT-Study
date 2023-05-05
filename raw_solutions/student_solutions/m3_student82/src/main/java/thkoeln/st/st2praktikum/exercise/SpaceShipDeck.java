package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceShipDeck extends AbstractEntity {

    @Id
    private UUID spaceShipDeckId = UUID.randomUUID();

    private Integer height;

    private Integer width;

    @OneToMany
    @Setter
    private List<Wall> walls;


    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Coordinate> coordinates;


    public SpaceShipDeck(Integer height, Integer width) {
        this.spaceShipDeckId = UUID.randomUUID();
        this.height = height;
        this.width = width;
        this.walls = new ArrayList<>();
        this.coordinates =  new ArrayList<>();
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
