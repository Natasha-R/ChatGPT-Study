package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class connection {
    UUID ID = UUID.randomUUID();
    UUID source;
    UUID dest;
    String sourceCords;
    String destCords;
    connection(UUID source, String sourceCords, UUID dest, String destCords){
        this.source = source;
        this.dest = dest;
        this.sourceCords = sourceCords;
        this.destCords = destCords;
    }
}
