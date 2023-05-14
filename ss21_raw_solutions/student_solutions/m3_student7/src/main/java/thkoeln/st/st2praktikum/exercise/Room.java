package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    private UUID id = UUID.randomUUID();

    @Embedded
    private Vector2D size;


    @Transient
    private List<Blocking> blockers = new ArrayList<Blocking>();


    public Room(Integer height, Integer width) throws Vector2DException {
        size = new Vector2D(width, height);
    }

    public boolean isCoordinateInBounds(Vector2D vector2D){
        if(vector2D.getX() > getWidth()-1 || vector2D.getY() > getHeight()-1)
            return false;
        return true;
    }

    public boolean isCoordinateEmpty(Vector2D coordinates){
        if(blockers.stream().anyMatch(findBlocker -> findBlocker.isBlockingCoordinate(coordinates)))
            return false;
        return true;
    }


    public void addBlocker(Blocking blocker){
        this.blockers.add(blocker);
    }
    public void removeBlocker(Blocking blocker){
        this.blockers.remove(blocker);
    }

    public int getHeight(){
        return size.getY();
    }
    public int getWidth(){
        return size.getX();
    }
}
