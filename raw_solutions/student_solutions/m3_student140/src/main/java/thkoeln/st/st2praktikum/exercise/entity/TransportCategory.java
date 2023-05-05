package thkoeln.st.st2praktikum.exercise.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Obstacle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransportCategory {
    @Id
    private UUID transportCategoryId;
    private String name;

    @OneToMany(
            mappedBy = "transportCategory",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Connection> connections = new ArrayList<>();

    public TransportCategory(UUID transportCategoryId, String name) {
        this.transportCategoryId = transportCategoryId;
        this.name = name;
    }
}
