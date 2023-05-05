package thkoeln.st.st2praktikum.exercise.core.tests;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Coordinate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Space {
    @Id
    private UUID Id;
    private Integer width;
    private Integer height;
    @Embedded
    private Coordinate spawnCoordinate;
    @ElementCollection(targetClass = Obsi.class, fetch = FetchType.EAGER )
    private List<Block> barriers;
    @OneToMany (targetEntity = Robi.class)
    private List<Occu> robis;

    public Space (Integer height, Integer width) {
        this.Id = UUID.randomUUID();
        this.height = height;
        this.width = width;
        //this.spawnCoordinate = new Coordinate(0,0);
        this.barriers = new ArrayList<Block>();
        this.robis = new ArrayList<Occu>();
    }
}