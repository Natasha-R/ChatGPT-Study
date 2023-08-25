package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@AllArgsConstructor
public class Room {

    @Id
    private UUID roomID;
    private Integer height;
    private Integer width;
    @Embedded
    private List<Barrier> horizontalBarriers;
    @Embedded
    private List<Barrier> vertikalBarriers;




    public Room() {
        this.roomID = UUID.randomUUID();
    }


    public UUID getRoomID(){return roomID;}
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
        if (horizontalBarriers == null)
            horizontalBarriers = new ArrayList<>();

        return horizontalBarriers;
    }

    public void setVertikalBarriers(List<Barrier> vertikalBarriers){

        this.vertikalBarriers = vertikalBarriers;
    }

    public List<Barrier> getVertikalBarriers(){
        if (vertikalBarriers == null)
            vertikalBarriers = new ArrayList<>();


        return vertikalBarriers;
    }


}
