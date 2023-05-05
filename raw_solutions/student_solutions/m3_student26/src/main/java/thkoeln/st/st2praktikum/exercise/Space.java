package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
public class Space extends AbstractEntity{

    @Getter
    private int height;
    @Getter
    private int width;
    @Transient @Getter
    private List<Wall> walls = new ArrayList<Wall>();
    @Transient @Getter
    private List<Connection> connections = new ArrayList<Connection>();

    public Space (int height, int width){
        this.height = height;
        this.width = width;
        walls = new ArrayList<Wall>();
        connections = new ArrayList<Connection>();
    }

    public Boolean checkCoordinateWithinSpace(Coordinate coordinate){
        return coordinate.getX() <= width && coordinate.getY() <= height;

    }





}
