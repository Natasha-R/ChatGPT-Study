package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Entity
public class TidyUpRobot{

    @Id
    private UUID tidyUpRobotID;
    @Embedded
    private List<Command> commands;
    private UUID roomID;
    private String name;
    @Embedded
    private Coordinate coordinate;


    public TidyUpRobot() {this.tidyUpRobotID = UUID.randomUUID();}

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

    public UUID getTidyUpRobotID(){return tidyUpRobotID;}

    public void setCoordinate (Coordinate coordinate){
        this.coordinate = coordinate;
    }

    public void setCommands(Command command) {
        this.commands.add(command);
    }

    public List<Command> getCommands(){
        if (commands == null)
            commands = new ArrayList<>();
        return commands;
    }

    public Coordinate getCoordinate (){

        return coordinate;
    }



}