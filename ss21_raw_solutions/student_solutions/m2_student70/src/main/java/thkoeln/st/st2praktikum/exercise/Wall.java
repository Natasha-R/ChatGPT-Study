package thkoeln.st.st2praktikum.exercise;

public class Wall {

    private Point start;
    private Point end;


    public Wall(Point pos1, Point pos2) {
        if (checkPoints(pos1, pos2)) {
            Point[] points=controlPoints(pos1,pos2);
            this.start = points[0];
            this.end = points[1];
        } else throw new UnsupportedOperationException();
    }

    private Point[] controlPoints(Point pos1, Point pos2) {
        Point[] points={pos1,pos2};
        if(pos1.getY()==pos2.getY()){
            if(pos1.getX()>pos2.getX()){
                points[0]=pos2;
                points[1]=pos1;
            }
        }
        if(pos1.getX()==pos2.getX()){
            if(pos1.getY()>pos2.getY()){
                points[0]=pos2;
                points[1]=pos1;

            }
        }

      return points;
    }


    private boolean checkPoints(Point pointstart, Point pointend) {
        return (pointstart.getX() == pointend.getX() || pointstart.getY() == pointend.getY());
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        String[] walldata = wallString.split("-");
        if (walldata.length != 2) throw new UnsupportedOperationException();
        Point pointstart = new Point(walldata[0]);
        Point pointend = new Point(walldata[1]);
        if (checkPoints(pointstart,pointend)) {
            this.start = pointstart;
            this.end = pointend;
        } else throw new UnsupportedOperationException();


    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
