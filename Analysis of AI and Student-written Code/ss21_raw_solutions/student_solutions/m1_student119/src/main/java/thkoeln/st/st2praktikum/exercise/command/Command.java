package thkoeln.st.st2praktikum.exercise.command;

import java.util.UUID;

/// "[tr,space-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on gridcells with a connection to another space
class TransportCommand extends Command {
    private final UUID spaceId;

    TransportCommand(UUID spaceId) {
        super(CommandType.transport);
        this.spaceId = spaceId;
    }

    public UUID getSpaceId() {
        return spaceId;
    }
}

public abstract class Command {
    private final CommandType commandType;

    protected Command(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return commandType;
    }
}

