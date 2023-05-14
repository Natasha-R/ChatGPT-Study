package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;

import javax.persistence.*;
import java.util.*;

@Entity
public class Field extends AbstractEntity {
    @Getter @ElementCollection(targetClass = Barrier.class,  fetch = FetchType.EAGER)
    private final List<Barrier> barriers = new ArrayList<>();

    // connection from this field to the field with the hashmap key
    @Getter @OneToMany(cascade = CascadeType.REMOVE)
    private final Map<UUID, Connection> connections = new HashMap<>();

    protected Field(){}
    public Field(int height, int width){
        var leftB = new Barrier(0,0,0,height);
        var rightB = new Barrier(width,0,width,height);
        var downB = new Barrier(0,0,width,0);
        var upB = new Barrier(0,height,width,height);
        getBarriers().add(leftB); getBarriers().add(rightB);
        getBarriers().add(downB); getBarriers().add(upB);
    }

    public void addConnection(TransportSystem transportSystemId, UUID destFieldID, Point src, Point dest){
        // maybe todo, check if coords are actually valid
        // this would however break the one-sided connection from mm to this.
    }
}
