package thkoeln.st.st2praktikum.exercise.domains;

import lombok.*;
import org.springframework.data.util.Pair;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Obstacle implements Serializable {

    private UUID roomId;
    private String obstacle;
    private Boolean isHorizontal;

    public Obstacle(UUID roomId, String obstacle) {
        this.roomId = roomId;
        this.obstacle = obstacle;
    }
}