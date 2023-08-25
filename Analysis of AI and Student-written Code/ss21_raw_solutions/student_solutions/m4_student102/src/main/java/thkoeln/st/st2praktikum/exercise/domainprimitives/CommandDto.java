package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Getter

public class CommandDto {

    private final Command command;
    private final UUID maintenanceDroid;

    public CommandDto( UUID maintenanceDroid , Command command) {
        this.command = command;
        this.maintenanceDroid = maintenanceDroid;
    }
}
