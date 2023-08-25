package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Connection extends AbstractEntity {
    private Field sourceField;
    private Field destinationField;
    private Coordinate sourceCoordinate;
    private Coordinate destinationCoordinate;

    public Connection(
            Field sourceField,
            Field destinationField,
            Coordinate sourceCoordinate,
            Coordinate destinationCoordinate) {


        if(sourceCoordinate.getX() > sourceField.getDimension().getWidth() ||
                sourceCoordinate.getY() > sourceField.getDimension().getHeight()
        ){ throw new IllegalArgumentException(
                "You tried to place the source coordinate " +
                        "of a connection outside the field");

        }

        if(destinationCoordinate.getX() > destinationField.getDimension().getWidth() ||
                destinationCoordinate.getY() > destinationField.getDimension().getHeight()
        ){ throw new IllegalArgumentException(
                "You tried to place the destination coordinate " +
                "of a connection outside the field");
        }

        this.sourceField = sourceField;
        this.destinationField = destinationField;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
    }

    boolean teleport(Placeable itemToTeleport){

        for(FieldItem item : destinationField.getItems()){
            if(item.getCoordinate().equals(destinationCoordinate)
                    && item instanceof MiningMachine){
                return false;
            }
        }

        for(FieldItem item : sourceField.getItems()){
            if(item == itemToTeleport && item.getCoordinate().equals(sourceCoordinate)){
                sourceField.remove(item);
                itemToTeleport.setCoordinate(destinationCoordinate);
                destinationField.add(item);
                return true;
            }
        }
        return false;
    }

}
