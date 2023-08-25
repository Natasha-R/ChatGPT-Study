package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
public class TidyUpRobot extends AbstractEntity {

    @Id
    private UUID tidyUpRobotID;
    private UUID roomID;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Coordinate coordinate;

    public TidyUpRobot(){}
  //  public TidyUpRobot(UUID id){super(id);}

    public void setTidyUpRobotID ( UUID tidyUpRobotID){
        this.tidyUpRobotID = tidyUpRobotID;
    }

    public UUID getTidyUpRobotID() {
        return tidyUpRobotID;
    }

    public void setRoomID ( UUID roomID){
        this.roomID = roomID;
    }

    public UUID getRoomId() {
        return roomID;
    }

    public void setName ( String name){
        this.name = name;
    }

    public String getName (){
        return name;
    }

    public void setCoordinate (Coordinate coordinate){
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate (){
        return coordinate;
    }



}
