package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;


public class Barrier {
    private final UUID fieldID;
    private final Axis axis;
    private final Point startPoint;
    private final Point endPoint;
    //Mit BarrierPoint arbeiten

    public Barrier(UUID fieldID, Point startPoint, Point endPoint) {
        this.fieldID = fieldID;
        this.startPoint = startPoint;
        this.endPoint = endPoint;

        if (startPoint.getX() != endPoint.getX())
            this.axis = Axis.HORIZONTAL; //Bilden Barriere auf x-Achse -
        else
            this.axis = Axis.VERTICAL; //Bilden Barriere auf y-Achse |

    }

    public UUID getFiedID() {
        return fieldID;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public Axis getAxis() {
        return axis;
    } //mit Enum arbeiten

}