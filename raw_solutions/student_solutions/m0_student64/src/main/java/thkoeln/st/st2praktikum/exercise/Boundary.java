package thkoeln.st.st2praktikum.exercise;

public class Boundary {

    private Point startPoint;
    private Point endPoint;

    public Boundary(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public boolean isVertical()
    {
        return this.startPoint.getX() == this.endPoint.getX();
    }

    public boolean isPointColliding(Point somePoint)
    {
        if(!this.isVertical())
        {
            if(this.endPoint.getY() != somePoint.getY())
            {
                return false;
            }

            System.out.println(String.format("PUNKT %d %d", somePoint.getX(), somePoint.getY()));
            return somePoint.getX() >= this.startPoint.getX() &&
                    somePoint.getX() < this.endPoint.getX();
        }

        if(this.isVertical())
        {
            if(this.endPoint.getX() != somePoint.getX())
            {
                return false;
            }

            return somePoint.getY() >= this.startPoint.getY() &&
                    somePoint.getY() < this.endPoint.getY();
        }

        return false;
//
//        int dxc = somePoint.getX() - startPoint.getX();
//        int dyc = somePoint.getY() - startPoint.getY();
//
//        int dxl = endPoint.getX() - startPoint.getX();
//        int dyl = endPoint.getY() - startPoint.getY();
//
//        int crosses = dxc * dyl - dyc * dxl;
//
//        return 0 == crosses;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }
}

