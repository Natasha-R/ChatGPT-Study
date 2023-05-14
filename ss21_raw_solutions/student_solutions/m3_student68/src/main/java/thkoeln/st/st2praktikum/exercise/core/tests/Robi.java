package thkoeln.st.st2praktikum.exercise.core.tests;

import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Robi implements Occu {
    @Id
    private UUID id;
    private String name;
    @Embedded
    private Coor coordinate;


    public Robi(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    @Override
    public UUID getId() { return this.id; }

    @Override
    public Coor getCoordinate() { return this.coordinate; }

}
