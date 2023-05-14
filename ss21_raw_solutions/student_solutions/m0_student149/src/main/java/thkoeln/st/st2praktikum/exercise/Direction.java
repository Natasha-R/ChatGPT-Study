package thkoeln.st.st2praktikum.exercise;

public enum Direction {

    NORTH("no", 0, 1),
    EAST("ea", 1, 0),
    SOUTH("so", 0, -1),
    WEST("we", -1, 0);

    private String code;
    private int xFactor = 0;
    private int yFactor = 0;

    Direction(String code, int xFactor, int yFactor) {
        this.code = code;
        this.xFactor = xFactor;
        this.yFactor = yFactor;
    }

    public static Direction getValue(String code) {
        switch (code) {
            case "no": return NORTH;
            case "ea": return EAST;
            case "so": return SOUTH;
            case "we": return WEST;
            default: return null;
        }
    }

    public String getCode() {
        return code;
    }

    public int getXFactor() {
        return xFactor;
    }

    public int getYFactor() {
        return yFactor;
    }
}
