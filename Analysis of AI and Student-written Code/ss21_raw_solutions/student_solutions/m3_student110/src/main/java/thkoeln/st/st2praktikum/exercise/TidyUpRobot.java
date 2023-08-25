package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class TidyUpRobot  extends TidyUpRobotService {


    @Id
    @Getter
    private UUID tidyUpRobotId = UUID.randomUUID();

    @Getter
    @Setter
    private String robotName;

    @Getter
    @Setter
    private UUID roomId ;


    @Getter
    @Setter
    @Embedded
    private Coordinate Coordinate ;






    public TidyUpRobot(String robotName){
        this.robotName = robotName;
    }

    public TidyUpRobot(String robotName, UUID roomId){
        this.robotName = robotName;
        this.roomId = roomId;
    }


}

