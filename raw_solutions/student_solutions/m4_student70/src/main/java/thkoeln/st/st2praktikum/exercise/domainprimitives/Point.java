package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;


@EqualsAndHashCode
@Setter
@Getter
@Embeddable
@NoArgsConstructor
public class Point {
    private Integer x;
    private Integer y;


    public Point(Integer x, Integer y) {
        if (x < 0 || y < 0) throw new UnsupportedOperationException();

        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public static Point fromString(String pointString) {
        if (!pointString.startsWith("(")
                || !pointString.endsWith(")")) throw new UnsupportedOperationException();
        String[] pointdata = pointString.replace("(", "")
                .replace(")", "").split(",");
        int x1 = -1, y1 = -1;
        if (pointdata.length != 2) throw new UnsupportedOperationException();
        try {
            x1 = Integer.parseInt(pointdata[0]);
            y1 = Integer.parseInt(pointdata[1]);
        } catch (NumberFormatException e) {
            throw new UnsupportedOperationException();
        }


        return new Point(x1, y1);

    }
    public void movNorth(){y+=1;}
    public void movSouth(){y-=1;}
    public void movEast(){x+=1;}
    public void movWest(){x-=1;}

}
