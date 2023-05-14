package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Field implements Appendable{

    @Id
    private UUID id = UUID.randomUUID();
    private int height, width;
    @ElementCollection(targetClass = Wall.class, fetch = FetchType.EAGER)
    private List<Wall> walls = new ArrayList<Wall>();

    public Field(){}

    public Field(int height, int width){
        this.height = height;
        this.width = width;
    }
    public Field getField(){
        return this;
    }

    public void addWall(Wall wall){
        walls.add(wall);
    }
}

