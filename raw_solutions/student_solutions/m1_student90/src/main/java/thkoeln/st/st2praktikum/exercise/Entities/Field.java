package thkoeln.st.st2praktikum.exercise.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Field {
    @Id
    private UUID id;

    private int maxWidth;
    private int maxHeight;

    public Field(){this.id=UUID.randomUUID();}
}
