package thkoeln.st.st2praktikum.exercise.tidyuprobot.dto;

import lombok.Data;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class TidyUpRobotDto {


    Vector2D vector2D=new Vector2D();


    private List<Task> taskList=new ArrayList<>();

    private String name;
    private UUID now_in_room ;
    private int posx ;
    private int posy ;


    public UUID getNow_in_room() {
        return now_in_room;
    }

    public String getCoordinates() {
        return "(" + posx + "," + posy + ")";
    }

    public Vector2D getVector2D() {
        return new Vector2D(posx, posy);
    }

}
