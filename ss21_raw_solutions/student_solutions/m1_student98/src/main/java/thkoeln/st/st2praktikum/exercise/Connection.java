package thkoeln.st.st2praktikum.exercise;


import java.util.UUID;

public class Connection implements Connectable {

    private final UUID id;
    private final XYPositionable fromPosition;
    private final XYPositionable toPosition;


    public Connection(XYPositionable fromPosition, XYPositionable toPosition) {
        this.id = UUID.randomUUID();
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public XYPositionable getFromPosition() {
        return fromPosition;
    }

    @Override
    public XYPositionable getToPosition() {
        return toPosition;
    }

    @Override
    public String debugConnectionToString() {
        return "\tConnectionID: "+id+"\n"+
                "\tfromPosition:\n"+
                "\t\t"+fromPosition.debugPositionToString()+"\n"+
                "\ttoPosition:\n"+
                "\t\t"+toPosition.debugPositionToString()+"\n";
    }
}
