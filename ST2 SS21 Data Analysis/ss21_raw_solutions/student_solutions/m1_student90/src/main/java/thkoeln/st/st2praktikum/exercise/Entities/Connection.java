package thkoeln.st.st2praktikum.exercise.Entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Connection {

    @Id
    public UUID id;

    @ManyToOne
    private final Field sourceField = new Field();

    @ManyToOne
    private final Field destinationField = new Field();



    public Connection(){this.id=UUID.randomUUID();}

}
