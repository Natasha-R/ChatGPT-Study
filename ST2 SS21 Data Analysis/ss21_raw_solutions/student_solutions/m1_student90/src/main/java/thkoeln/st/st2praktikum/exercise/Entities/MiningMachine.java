package thkoeln.st.st2praktikum.exercise.Entities;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Entities.Field;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
public class MiningMachine{

    @Id
    private UUID id;

    private int current_xPosition;
    private int current_yPosition;

    @ManyToOne
    private Field field;

    public MiningMachine(){this.id = UUID.randomUUID();}
}
