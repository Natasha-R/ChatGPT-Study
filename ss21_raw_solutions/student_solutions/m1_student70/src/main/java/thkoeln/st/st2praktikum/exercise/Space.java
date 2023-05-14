package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
public class Space {
    private UUID spaceId;
    private int height,width;
    private List<Wall> walls =new ArrayList<>();
    private List<Connection> connections =new ArrayList<>();
    private Map<UUID,CleaningDevice> cleaningDevices =new HashMap();


    public Space(int height, int width) {
        if(height<1||width<1)throw new UnsupportedOperationException();
        this.spaceId= UUID.randomUUID();
        this.height = height;
        this.width = width;

    }
//(2,5)
    public boolean isCoordinateExistAndEmpty(Point position2,String direction){
       Point position=new Point(position2.getX(), position2.getY());
        switch (direction) {
            case "no":  position.setY(position.getY()+1);break;
            case "so":   position.setY(position.getY()-1);break;
            case "ea":  position.setX(position.getX()+1);break;
            case "we":  position.setX(position.getX()-1);
        }
        if(position.getY()>height-1||position.getX()>width-1)return false;
        if(position.getY()<0||position.getX()<0)return false;


        for (Map.Entry entry : cleaningDevices.entrySet()) {
            CleaningDevice cleaningDevice = (CleaningDevice) entry.getValue();
            if (cleaningDevice.getPosition().getX() == position.getX()
                    && cleaningDevice.getPosition().getY() == position.getY())
                return false;
        }
        return true;



    }
}
