package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class TidyUpRobot  {


    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID tidyUpRobotId ;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private UUID roomId ;


    @Getter
    @Setter
    @Embedded
    private thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate coordinate ;






    public TidyUpRobot(String name){
        this.name = name;
    }

    public TidyUpRobot(String name, UUID roomId){
        this.name = name;
        this.roomId = roomId;
    }

    public UUID getTidyUpRobotId(){
       if (this.tidyUpRobotId != null){
           return this.tidyUpRobotId;
       }
       else return null;
    }





}