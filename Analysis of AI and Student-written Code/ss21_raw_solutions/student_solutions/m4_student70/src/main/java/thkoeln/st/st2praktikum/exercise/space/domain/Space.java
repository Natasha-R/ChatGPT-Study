package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;

import javax.persistence.*;
import java.util.*;


@Setter
@Getter
@NoArgsConstructor
@Entity
public class Space {
    @Id
    private UUID spaceId;
    private int height,width;
    @ElementCollection(targetClass = Wall.class, fetch = FetchType.EAGER)
    private List<Wall> walls =new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<CleaningDevice> cleaningDevices =new ArrayList<>();


    public Space(int height, int width) {
        if(height<1||width<1)throw new UnsupportedOperationException();
        this.spaceId= UUID.randomUUID();
        this.height = height;
        this.width = width;

    }

    public boolean isCoordinateExistAndEmpty(Point position2, TaskType taskType){
        Point position=new Point(position2.getX(), position2.getY());
        switch (taskType) {
            case NORTH:  position.setY(position.getY()+1);break;
            case SOUTH:   position.setY(position.getY()-1);break;
            case EAST:  position.setX(position.getX()+1);break;
            case WEST:  position.setX(position.getX()-1);
        }
       if(!isCoordinateExist(position))return false;

        for (CleaningDevice cleaningDevice : cleaningDevices)

            if (cleaningDevice.getPoint().getX() == position.getX()
                    && cleaningDevice.getPoint().getY() == position.getY())
                return false;

        return true;

    }

    public boolean isCoordinateExist(Point position){
        if(position.getY()>height-1||position.getX()>width-1)return false;
        if(position.getY()<0||position.getX()<0)return false;
        return true;

    }





}
