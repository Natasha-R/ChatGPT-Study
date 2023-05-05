package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class connection {
    UUID ID = UUID.randomUUID();
    UUID source;
    UUID dest;
    Coordinate sourceCords;
    Coordinate destCords;
    connection(UUID source, Coordinate sourceCords, UUID dest, Coordinate destCords){
        this.source = source;
        this.dest = dest;
        this.sourceCords = sourceCords;
        this.destCords = destCords;
    }
}
