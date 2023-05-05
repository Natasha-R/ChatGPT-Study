package thkoeln.st.st2praktikum.exercise;

public interface Connectable extends Identifiable {

    XYPositionable getFromPosition();

    XYPositionable getToPosition();

    String debugConnectionToString();
}
