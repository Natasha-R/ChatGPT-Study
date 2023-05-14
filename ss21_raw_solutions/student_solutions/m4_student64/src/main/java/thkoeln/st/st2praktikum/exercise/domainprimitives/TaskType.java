package thkoeln.st.st2praktikum.exercise.domainprimitives;

public enum TaskType {
    NORTH,
    WEST,
    SOUTH,
    EAST,
    TRANSPORT,
    ENTER;

    public static TaskType fromString(String enumString)
    {
        switch (enumString)
        {
            case "no":
                return TaskType.NORTH;
            case "we":
                return TaskType.WEST;
            case "so":
                return TaskType.SOUTH;
            case "ea":
                return TaskType.EAST;
            case "tr":
                return TaskType.TRANSPORT;
            case "en":
                return TaskType.ENTER;
            default:
                throw new RuntimeException("Invalid TaskType String");
        }
    }
}
