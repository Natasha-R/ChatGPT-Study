package thkoeln.st.st2praktikum.exercise.entitys;

import thkoeln.st.st2praktikum.exercise.interfaces.NoMoveable;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

public class Wall implements NoMoveable {

    @Id
    protected UUID wallId;
    private UUID fieldId;
    private Integer sourceX;
    private Integer sourceY;
    private Integer destinationX;
    private Integer destinationY;

    public Wall(Field field, Integer sourceX, Integer sourceY, Integer destinationX, Integer destinationY){
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }
    public Wall(){wallId = UUID.randomUUID();}

    //Getter-----------------------------------------------------------------------------------------------------------
    @Override
    public UUID getWallId() {
        return wallId;
    }
    @Override
    public UUID getField() {
        return fieldId;
    }
    @Override
    public Integer getSourceX() {
        return sourceX;
    }
    @Override
    public Integer getSourceY() {
        return sourceY;
    }
    @Override
    public Integer getDestinationX() {
        return destinationX;
    }
    @Override
    public Integer getDestinationY() {
        return destinationY;
    }

    //Setter-----------------------------------------------------------------------------------------------------------
    @Override
    public void setField(UUID fieldId) {this.fieldId =fieldId;}
    @Override
    public void setSourceX(Integer sourceX) {
        this.sourceX = sourceX;
    }
    @Override
    public void setSourceY(Integer sourceY) {
        this.sourceY = sourceY;
    }
    @Override
    public void setDestinationX(Integer destinationX) {
        this.destinationX = destinationX;
    }
    @Override
    public void setDestinationY(Integer destinationY) {
        this.destinationY = destinationY;
    }

}
