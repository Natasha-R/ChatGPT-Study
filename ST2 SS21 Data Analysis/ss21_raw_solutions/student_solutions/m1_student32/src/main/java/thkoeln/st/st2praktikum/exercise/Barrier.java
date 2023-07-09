package thkoeln.st.st2praktikum.exercise;

public class Barrier {
    public enum BarrierType
    {
        HORIZONTAL, VERTICAL
    }

    private BarrierType type;
    private Integer position;
    private Integer start;
    private Integer end;

    public Barrier(BarrierType type, Integer position, Integer start, Integer end) {
        this.type = type;
        this.position = position;
        this.start = start;
        this.end = end;
    }

    public BarrierType getType() {
        return type;
    }

    public Integer getPosition() {
        return position;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }
}
