package thkoeln.st.st2praktikum.exercise;

public enum OrderType {
    NORTH,
    WEST,
    SOUTH,
    EAST,
    TRANSPORT,
    ENTER;

    public static OrderType fromString(String orderTypeString) {
        switch (orderTypeString) {
            case "no":
                return OrderType.NORTH;
            case "ea":
                return OrderType.EAST;
            case "so":
                return OrderType.SOUTH;
            case "we":
                return OrderType.WEST;
            case "tr":
                return OrderType.TRANSPORT;
            case "en":
                return OrderType.ENTER;
            default:
                throw new RuntimeException("Unsupported OrderType " + orderTypeString);
        }
    }
}
