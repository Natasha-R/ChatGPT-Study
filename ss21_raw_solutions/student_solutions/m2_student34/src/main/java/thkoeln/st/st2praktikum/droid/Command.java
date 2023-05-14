package thkoeln.st.st2praktikum.droid;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import thkoeln.st.st2praktikum.map.Connection;
import thkoeln.st.st2praktikum.map.Map;

public abstract class Command {

    public static MapCommand createMapCommand(Direction direction,
                                              int distance) {
        return new MapCommand(direction, distance);
    }

    public static EnterCommand createEnterCommand(Map targetMap) {
        return new EnterCommand(targetMap);
    }

    public static ConnectionCommand createConnectionCommand(Connection connection) {
        return new ConnectionCommand(connection);
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @EqualsAndHashCode
    public static class MapCommand extends Command {
        private final Direction direction;
        private final int distance;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @EqualsAndHashCode
    public static class EnterCommand extends Command {
        private final Map targetMap;

        public double[] getTargetPosition() {
            return new double[]{0, 0};
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @EqualsAndHashCode
    public static class ConnectionCommand extends Command {
        private final Connection connection;
    }
}
