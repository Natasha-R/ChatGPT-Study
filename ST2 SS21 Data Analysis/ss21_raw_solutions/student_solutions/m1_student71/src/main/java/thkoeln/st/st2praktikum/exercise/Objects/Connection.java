package thkoeln.st.st2praktikum.exercise.Objects;

public class Connection extends BaseClass{

    public final Field SourceField;
    public final Coordinates SourceCoordinates;

    public final Field DestinationField;
    public final Coordinates DestinationCoordinates;


    public Connection(Field sourceField, Field destinationField, Coordinates sourceCoordinates, Coordinates destinationCoordinates) {
        super();
        DestinationField = destinationField;
        DestinationCoordinates = destinationCoordinates;
        SourceField = sourceField;
        SourceCoordinates = sourceCoordinates;
    }




}
