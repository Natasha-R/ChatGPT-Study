package thkoeln.st.st2praktikum.exercise;

public interface Occupied extends Identifiable {

    Boolean isOccupied(XYPositionable position);

    String debugOccupationStatusToString();
}
