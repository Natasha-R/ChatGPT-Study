package thkoeln.st.st2praktikum.exercise.entitys;

import thkoeln.st.st2praktikum.exercise.interfaces.Fieldable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Field implements Fieldable {

   //ID generiert automatisch.
    @Id
    protected UUID fieldId;
    private Integer height;
    private Integer width;

    public Field() { this.fieldId = UUID.randomUUID();}

    //Getter-------------------------------------------------------------------------------
    public UUID getFieldId() {
        return fieldId;
    }
    @Override
    public Integer getHeight() {
        return height;
    }
    @Override
    public Integer getWidth() {return width;}
    //Setter---------------------------------------------------------------------------------
    @Override
    public void setHeight(Integer height) {
        this.height = height;
    }
    @Override
    public void setWidth(Integer width) {
        this.width = width;
    }


}
