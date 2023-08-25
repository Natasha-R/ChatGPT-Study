package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room extends AbstractEntity {




    private int height;
    private int width;
    private UUID roomUUID;
    @Id
    public UUID getId() {
        return roomUUID;
    }

    public void setId(UUID id) {
        this.roomUUID = id;
    }


    private List<Barrier> barrierList = new ArrayList<Barrier>();


    public Room(int height, int width, UUID roomUUID) {
        this.height = height;
        this.width = width;
        this.roomUUID = roomUUID;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public UUID getRoomUUID() {
        return roomUUID;
    }

    @OneToMany
    public List<Barrier> getBarrierList() {
        return barrierList;
    }




}
