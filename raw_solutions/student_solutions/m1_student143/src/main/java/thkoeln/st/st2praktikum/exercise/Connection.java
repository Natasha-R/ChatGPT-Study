package thkoeln.st.st2praktikum.exercise;

public class Connection extends AbstractEntity {
    private Field sourceField;
    private Field destinationField;
    private Coordinate sourceCoordinate;
    private Coordinate destinationCoordinate;

    public Connection(Field sourceField,
                      Field destinationField,
                      Coordinate sourceCoordinate,
                      Coordinate destinationCoordinate) {
        this.sourceField = sourceField;
        this.destinationField = destinationField;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
    }

    boolean teleport(Placable itemToTeleport){
        for(Placable placable : sourceField.getItems()){
            Coordinate placableCoordinate = placable.getCoordinate();

            if(placable == itemToTeleport && placableCoordinate.equals(sourceCoordinate)){
                sourceField.remove(placable);
                placable.setCoordinate(destinationCoordinate);
                destinationField.add(placable);
                return true;
            }
        }
        return false;
    }

    public Field getSourceField() {
        return sourceField;
    }

    public void setSourceField(Field sourceField) {
        this.sourceField = sourceField;
    }

    public Field getDestinationField() {
        return destinationField;
    }

    public void setDestinationField(Field destinationField) {
        this.destinationField = destinationField;
    }

    public Coordinate getSourceCoordinate() {
        return sourceCoordinate;
    }

    public void setSourceCoordinate(Coordinate sourceCoordinate) {
        this.sourceCoordinate = sourceCoordinate;
    }

    public Coordinate getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public void setDestinationCoordinate(Coordinate destinationCoordinate) {
        this.destinationCoordinate = destinationCoordinate;
    }


}
