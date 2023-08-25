package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FloorTile extends AbstractEntity {
    private boolean hasRobot = false;
    private boolean hasHorizontalBarrier = false;
    private boolean hasVerticalBarrier = false;
    private boolean hasConnection = false;
    private Vector2D coordinate;
    private Connection connection;

    public FloorTile(Vector2D vector2D) {
        coordinate = vector2D;
    }

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
