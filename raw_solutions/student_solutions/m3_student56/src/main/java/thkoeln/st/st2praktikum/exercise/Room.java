package thkoeln.st.st2praktikum.exercise;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
public class Room {

    @Id
    private UUID roomID;
    private Integer height;
    private Integer width;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Barrier> horizontalBarriers = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Barrier> vertikalBarriers = new ArrayList<>();



    public void setRoomID ( UUID roomID){
        this.roomID = roomID;
    }

    public UUID getRoomID() {
        return roomID;
    }

    public void setHeight ( Integer height){
        this.height = height;
    }
    public Integer getHeight (){
        return height;
    }

    public void setWidth ( Integer width){
        this.width = width;
    }
    public Integer getWidth (){
        return width;
    }

    public void setHorizontalBarriers(List<Barrier> horizontalBarriers){
        this.horizontalBarriers = horizontalBarriers;
    }

    public List<Barrier> getHorizontalBarriers(){
        return horizontalBarriers;
    }

    public void setVertikalBarriers(List<Barrier> vertiktalBarriers){
        this.vertikalBarriers = vertiktalBarriers;
    }

    public List<Barrier> getVertikalBarriers(){
        return vertikalBarriers;
    }


}
