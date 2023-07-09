package thkoeln.st.st2praktikum.exercise;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Room {

    @Id
    private UUID id = UUID.randomUUID();

    private Integer width;
    private Integer height;

    @OneToOne(cascade = CascadeType.ALL)
    private Connection connection;

    @ElementCollection
    @Column(insertable = false,updatable = false)
    private List<Obstacle> obstacleList = new ArrayList<>();


    public Room(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }
}