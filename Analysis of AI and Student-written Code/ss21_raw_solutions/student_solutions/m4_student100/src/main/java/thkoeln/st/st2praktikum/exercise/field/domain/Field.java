package thkoeln.st.st2praktikum.exercise.field.domain;

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
public class Field extends AbstractEntity {

    private UUID fielduuid;
    private int height;
    private int width;




    @Id
    public UUID getId() {
        return fielduuid;
    }

    public void setId(UUID id) {
        this.fielduuid = id;
    }


    private List<Wall> walllist = new ArrayList<Wall>();

    public Field(int height, int width, UUID fielduuid) {
        this.height = height;
        this.width = width;
        this.fielduuid = fielduuid;
    }



    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public UUID getFielduuid() {
        return fielduuid;
    }




    @OneToMany
    public List<Wall> getWalllist() {
        return walllist;
    }




}
