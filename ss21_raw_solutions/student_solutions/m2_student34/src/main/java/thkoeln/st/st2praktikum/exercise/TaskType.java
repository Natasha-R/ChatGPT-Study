package thkoeln.st.st2praktikum.exercise;

public enum TaskType {
    NORTH,
    WEST,
    SOUTH,
    EAST,
    TRANSPORT,
    ENTER;

    public static TaskType of(String type) {
        switch (type) {
            case "no": return NORTH;
            case "we": return WEST;
            case "so": return SOUTH;
            case "ea": return EAST;
            case "tr": return TRANSPORT;
            case "en": return ENTER;
            default: throw new UnsupportedOperationException();
        }
    }
}
