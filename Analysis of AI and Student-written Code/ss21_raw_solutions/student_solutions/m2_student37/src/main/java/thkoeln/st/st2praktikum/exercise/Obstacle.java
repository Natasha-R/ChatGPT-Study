package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.expetctions.InvalidObstacleExeption;

public class Obstacle {

    private Vector2D start;
    private Vector2D end;
    private boolean vertical;


    public Obstacle(Vector2D pos1, Vector2D pos2) {
        if (pos1.getX()==pos2.getX()&&pos1.getY()== pos2.getY()) {
            throw new InvalidObstacleExeption("Darf nicht Diagonal sein");
        }
        else if (pos1.getX()-pos1.getY()== pos2.getY()- pos2.getX()) {
            throw new InvalidObstacleExeption("Darf nicht Diagonal sein");
        }
        else if (pos1.getX()>= pos2.getX()){
            if (pos1.getY()>= pos2.getY()){
                this.start=pos2;
                this.end=pos1;
            }else {
                this.start = pos1;
                this.end = pos2;
            }
        }else {
            this.start=pos1;
            this.end=pos2;
        }
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
        String[] oString = obstacleString.split("-");
        Vector2D start = new Vector2D(oString[0]);
        Vector2D end = new Vector2D(oString[1]);
        if (start.getX()== end.getX()) {
            vertical=true;
            if (start.getY()>end.getY()){
                this.start=end;
                this.end=start;
            }else {
                this.start=start;
                this.end=end;
            }
        }else if (start.getY()==end.getY()){
            vertical=false;
            if (start.getX()> end.getX()) {
                this.start=end;
                this.end=start;
            }else {
                this.start=start;
                this.end=end;
            }
        }else {
            throw new InvalidObstacleExeption("Falscher Obstacle");
        }
    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }

    public boolean isVertical() {
        return vertical;
    }

    public boolean sameX(int x) {
        boolean result = false;
        if (vertical){
            if (x==start.getX()){
                result=true;
            }else {
                result=false;
            }
        }else {
            if (x>start.getX()&&x<end.getX()){
                result=true;
            }else {
                result=false;
            }
        }
        return result;
    }

    public boolean sameY(int y) {
        boolean result =false;
        if (!vertical){
            if (y==start.getY()){
                result=true;
            }else {
                result=false;
            }
        }else {
            if (y>start.getY()&&y<end.getY()){
                result=true;
            }else {
                result=false;
            }
        }
        return result;
    }

}
