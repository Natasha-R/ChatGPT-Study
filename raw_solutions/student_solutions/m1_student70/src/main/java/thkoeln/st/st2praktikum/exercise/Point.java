package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class Point {


    private int x, y;

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        if (!pointString.startsWith("(") || !pointString.endsWith(")")) throw new UnsupportedOperationException();
        String[] pointdata = pointString.replace("(", "").replace(")", "").split(",");

        if (pointdata.length != 2) throw new UnsupportedOperationException();
        int x1 = -1, y1 = -1;
        try {
            x1 = Integer.parseInt(pointdata[0]);
            y1 = Integer.parseInt(pointdata[1]);
        } catch (NumberFormatException e) {
            throw new UnsupportedOperationException();
        }

        if (x1 < 0 || y1 < 0) throw new UnsupportedOperationException();
           // data pass all tests so creat the point
            this.x = x1;
            this.y = y1;


    }
    public void movNorth(){y+=1;}
    public void movSouth(){y-=1;}
    public void movEast(){x+=1;}
    public void movWest(){x-=1;}


    @Override
    public String toString() {
        return "(" + x + "," + y + ')';
    }
}
