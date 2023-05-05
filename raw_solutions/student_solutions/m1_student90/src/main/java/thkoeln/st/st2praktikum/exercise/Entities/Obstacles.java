package thkoeln.st.st2praktikum.exercise.Entities;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Entities.Field;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Obstacles {

    @Id
    private UUID id;

    @ManyToOne
    private Field field;

    public Obstacles(){this.id = UUID.randomUUID();}


    private int sourceX;

    private int sourceY;

    private int destinationX;

    private int destinationY;



}
