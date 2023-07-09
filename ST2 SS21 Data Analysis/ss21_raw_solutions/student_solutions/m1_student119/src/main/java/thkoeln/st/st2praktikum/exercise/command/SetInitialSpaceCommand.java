package thkoeln.st.st2praktikum.exercise.command;

import java.util.UUID;

/// "[en,<space-id>]" for setting the initial space where a cleaning device is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The cleaning device will always spawn at (0,0) of the given space.
public class SetInitialSpaceCommand extends Command {
    private final UUID spaceId;

    SetInitialSpaceCommand(UUID spaceId) {
        super(CommandType.setInitialSpace);
        this.spaceId = spaceId;
    }

    public UUID getSpaceId() {
        return spaceId;
    }
}
