package thkoeln.st.st2praktikum.exercise.spaceship;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Point;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
public class SpaceShip {
    @Id
    private UUID spaceChipId=UUID.randomUUID();
    Integer height,width;

    public SpaceShip() {}
    public SpaceShip(Integer height, Integer width) {
        this.height = height;
        this.width = width;
    }


}
