package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TidyUpRobot implements Blocking{
    @Id
    private UUID id = UUID.randomUUID();

    @Embedded
    private Vector2D vector2D;
    @OneToOne
    private Room room;

    private String name;


    public TidyUpRobot(String name){
        this.name = name;
    }

    public void move(Vector2D coordinates){
        this.vector2D = coordinates;
    }

    @Override
    public Boolean isBlockingCoordinate(Vector2D coordinates){
        if(this.vector2D.equals(coordinates))
            return true;
        return false;
    }

    public UUID getRoomId(){
        return room.getId();
    }
    public Vector2D getVector2D(){
        return vector2D;
    }

}
