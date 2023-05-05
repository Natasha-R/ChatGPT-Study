package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

public class Connection extends UUIDBase {

    @Getter
    private final Field sourceField;
    @Getter
    private final Coordinate sourceCoordinates;
    @Getter
    private final Field destinationField;
    @Getter
    private final Coordinate destinationCoordinates;


    public Connection(Field sourceField, Field destinationField, Coordinate sourceCoordinates, Coordinate destinationCoordinates) {
        super();
        this.destinationField = destinationField;
        this.destinationCoordinates = destinationCoordinates;
        this.sourceField = sourceField;
        this.sourceCoordinates = sourceCoordinates;
    }




}
