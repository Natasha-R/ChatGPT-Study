package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class TidyUpRobotDto {

    @Getter
    @Setter
    @Id
    private UUID id;

    @Getter
    @Setter
    private String name;
}
