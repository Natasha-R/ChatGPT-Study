package thkoeln.st.st2praktikum.exercise.field;

import thkoeln.st.st2praktikum.exercise.Coordinate;

import java.util.UUID;

public class FieldConnection {
    private final Field sourceField;
    private final Coordinate sourceCoordinate;
    private final Field destinationField;
    private final Coordinate destinationCoordinate;

    public FieldConnection(Field sourceField, Coordinate sourceCoordinate, Field destinationField, Coordinate destinationCoordinate) {
        if(sourceField == null || sourceCoordinate == null || destinationField == null || destinationCoordinate == null)
            throw new RuntimeException("The source or destination cannot be null.");
        if(sourceCoordinate.getX() < sourceField.getStart().getX() || sourceCoordinate.getY() > sourceField.getEnd().getY())
            throw new RuntimeException("The source coordinate cannot be out of bounds.");
        if(destinationCoordinate.getY() < destinationField.getStart().getY() || destinationCoordinate.getY() > destinationField.getEnd().getY())
            throw new RuntimeException("The destination coordinate cannot be out of bounds.");
        this.sourceField = sourceField;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
        this.destinationField = destinationField;
    }

    public Coordinate getSourceCoordinate() {
        return sourceCoordinate;
    }

    public Coordinate getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public Field getSourceField() {
        return sourceField;
    }

    public Field getDestinationField() {
        return destinationField;
    }
}
