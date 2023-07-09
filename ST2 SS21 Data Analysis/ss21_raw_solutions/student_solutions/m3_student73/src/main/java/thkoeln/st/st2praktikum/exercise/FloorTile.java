package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FloorTile {
    private boolean hasRobot = false;
    private boolean hasHorizontalBarrier = false;
    private boolean hasVerticalBarrier = false;
    private boolean hasConnection = false;
    private Connection connection;

    public boolean isHasRobot() {
        return hasRobot;
    }

    public void setHasRobot() { hasRobot = !hasRobot; }

    public boolean isHasHorizontalBarrier() {
        return hasHorizontalBarrier;
    }

    public void setHasHorizontalBarrier() {
        hasHorizontalBarrier = !hasHorizontalBarrier;
    }

    public boolean isHasVerticalBarrier() { return hasVerticalBarrier; }

    public void setHasVerticalBarrier() {
        hasVerticalBarrier = !hasVerticalBarrier;
    }

    public boolean isHasConnection() {
        return hasConnection;
    }

    public void setHasConnection() {
        hasConnection = !hasConnection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connectionParam) {
        connection = connectionParam;
    }

    public Vector2D getConnectionCoordinate() {
        return connection.getDestinationCoordinate();
    }



}
