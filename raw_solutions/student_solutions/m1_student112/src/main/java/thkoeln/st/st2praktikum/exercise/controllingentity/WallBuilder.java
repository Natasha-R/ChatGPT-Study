package thkoeln.st.st2praktikum.exercise.controllingentity;

import thkoeln.st.st2praktikum.exercise.entitys.Wall;
import thkoeln.st.st2praktikum.exercise.interfaces.Fieldable;
import thkoeln.st.st2praktikum.exercise.interfaces.NoMoveable;
import thkoeln.st.st2praktikum.exercise.selfdefinedsupport.CoordinatePair;

public class WallBuilder {
    public NoMoveable createNewWall (CoordinatePair[] coordinateWall){
        NoMoveable wall =new Wall();

        wall.setDestinationX(coordinateWall[1].getXCoordinate());
        wall.setDestinationY(coordinateWall[1].getYCoordinate());
        wall.setSourceX(coordinateWall[0].getXCoordinate());
        wall.setSourceY(coordinateWall[0].getYCoordinate());
        return wall;
    }

    public Boolean isEnoughSpaceForTheWall (Fieldable field, NoMoveable wall){
        return field.getHeight() >= wall.getDestinationY() && field.getWidth() >= wall.getDestinationX();
    }

    public NoMoveable placeWallAtField( NoMoveable wall, Fieldable field){
        if(isEnoughSpaceForTheWall(field, wall)) {
            wall.setField(field.getFieldId());
            return wall;
        }else throw new IndexOutOfBoundsException("Mauer hat keinen Platz auf dem Feld");
    }
}
