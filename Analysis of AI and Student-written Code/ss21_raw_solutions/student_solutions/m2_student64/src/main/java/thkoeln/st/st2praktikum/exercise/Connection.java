package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Connection implements IHasUUID {
    private final UUID uuid;

    private Space startSpace;
    private Point startPoint;

    private Space endSpace;
    private Point endpoint;

    public Connection(Space startSpace, Point startPoint, Space endSpace, Point endpoint) {
        this.uuid = UUID.randomUUID();
        this.startSpace = startSpace;
        this.startPoint = startPoint;
        this.endSpace = endSpace;
        this.endpoint = endpoint;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }


}
