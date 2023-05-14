package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Spaceship {
    @Id
    private UUID spaceChipId=UUID.randomUUID();
    Integer height,width;

    public Spaceship() {}
    public Spaceship(Integer height, Integer width) {
        this.height = height;
        this.width = width;
    }


}

