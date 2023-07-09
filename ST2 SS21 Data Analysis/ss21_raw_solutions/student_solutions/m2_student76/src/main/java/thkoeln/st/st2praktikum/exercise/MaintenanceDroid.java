package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;
import java.util.UUID;
@Getter
@Setter

public class MaintenanceDroid extends AbstractEntity implements Moveable {

    private String name;
    protected int x,y;
    protected Point point;
    protected UUID currentSpaceDeckUUID;
    protected  SpaceDeck currentSpaceDeck;
    protected  String coordinate = "(" +x+ "," +y+ ")";

    protected void setXY(int x, int y){
        this.point = new Point(x,y);
    }

    @Override
    public void moveNorth(int command) {
        int y1= point.getY()+command ;
        Integer y0 = null; //let y0 be the y-coordinate of another droid that may stand in the way of the droid at y1

        for ( int i=0; i < getCurrentSpaceDeck().maintenanceDroids.size(); i++ ){

            Point point = getCurrentSpaceDeck().maintenanceDroids.get(i).getPoint();
            if(point.getX().equals(getPoint().getX()) &&  point.getY()> getPoint().getY()){
                y0 = getCurrentSpaceDeck().maintenanceDroids.get(i).getPoint().getY();
            }
        }
        //don't fall off the border
        if(y1 > currentSpaceDeck.getHeight()) y1=currentSpaceDeck.getHeight();//no droid or barrier in the way

        //barriers and/or droids standing in the way
        if(currentSpaceDeck.barriers.isEmpty()){
            if(y0 !=null && y1 >= y0){ y1 = y0-1;} //no barriers but a droid stands in the way
        }else{
            for(int i =0; i< currentSpaceDeck.barriers.size(); i++){
                Barrier barrier = currentSpaceDeck.barriers.get(i);
                //existence of a barrier
                if(barrier.getStart().getY().equals(barrier.getEnd().getY())){
                    if(y0!= null && y1 >=y0){
                        y1= y0-1;//droid in the way
                    }else if(getPoint().getX()>= barrier.getStart().getX() && getPoint().getX()<barrier.getEnd().getX()){
                        if(y1 >= barrier.getStart().getY()) y1 = barrier.getStart().getY()-1;//barrier in the way
                    }
                }
            }
        }
        setXY(getPoint().getX(),y1);
    }

    @Override
    public void moveSouth(int command) {
        int y1 = point.getY()-command;
        Integer y0= null;

        for ( int i=0; i < getCurrentSpaceDeck().maintenanceDroids.size(); i++ ){
            Point point = getCurrentSpaceDeck().maintenanceDroids.get(i).getPoint();
            if(point.getX().equals(getPoint().getX()) &&  point.getY() < getPoint().getY()){
                y0 = getCurrentSpaceDeck().maintenanceDroids.get(i).getPoint().getY();
                break;
            }
        }

        if(y1<0) y1=0;

        if(currentSpaceDeck.barriers.isEmpty()){
            if(y0!= null && y1 <= y0) y1 =y0+1;
        }

        for(int i = 0; i < currentSpaceDeck.barriers.size(); i++){
            Barrier barrier = currentSpaceDeck.barriers.get(i);

            if(barrier.getStart().getY().equals(barrier.getEnd().getY())){
                if(y0!= null && y1 <= y0 && y0> barrier.getStart().getY()) y1=y0+1;

                else if(getPoint().getX() <=barrier.getStart().getX() && getPoint().getX()> barrier.getEnd().getX()){
                    if(y1<= barrier.getStart().getY()){
                        y1 = barrier.getStart().getY()+1;
                    }
                }
            }

        }
        setXY(getPoint().getX(), y1);
    }

    @Override
    public void moveEast(int command) {
        int x1 = point.getX()+command;
        Integer x0 = null;

        for ( int i = 0; i<getCurrentSpaceDeck().maintenanceDroids.size(); i++){
            Point point = getCurrentSpaceDeck().maintenanceDroids.get(i).getPoint();
            if(point.getY().equals(getPoint().getY()) && getPoint().getX() < point.getX()){
                x0 = getCurrentSpaceDeck().maintenanceDroids.get(i).getPoint().getX();
                break;
            }
        }

        if( x1> currentSpaceDeck.getWidth()) x1= currentSpaceDeck.getWidth();

        if(currentSpaceDeck.barriers.isEmpty()){
            if(x0 != null && x1 >= x0) x1 = x0-1;
        }else{
            for(int i =0; i< currentSpaceDeck.barriers.size(); i++){
                Barrier barrier = currentSpaceDeck.barriers.get(i);

                if(barrier.getStart().getX().equals(barrier.getEnd().getX())){
                    if(x0!= null && x1 >= x0 && x0<barrier.getStart().getX()) x1 =x0-1;
                    else if(getPoint().getY() <= barrier.getStart().getY() && getPoint().getY() > barrier.getEnd().getY()){
                        if(x1>= barrier.getStart().getX()) x1= barrier.getStart().getX()-1;
                    }
                }
            }
        }
        setXY(x1,getPoint().getY());
    }

    @Override
    public void moveWest(int command) {
        int x1 = point.getX()-command;
        Integer x0 = null;

        for ( int i = 0; i<getCurrentSpaceDeck().maintenanceDroids.size(); i++){
            Point point = getCurrentSpaceDeck().maintenanceDroids.get(i).getPoint();
            if(point.getY().equals(getPoint().getY()) && getPoint().getX() > point.getX()){
                x0 = getCurrentSpaceDeck().maintenanceDroids.get(i).getPoint().getX();
                break;
            }
        }

        if(x1 <0) x1 =0;

        if (currentSpaceDeck.barriers.isEmpty()){
            if(x0 !=null && x1<= x0) x1 =x0+1;
        }

        for(int i =0; i<currentSpaceDeck.barriers.size(); i ++){
            Barrier barrier = currentSpaceDeck.barriers.get(i);

            if(barrier.getStart().getX().equals(barrier.getEnd().getX())){
                if(x0 != null && x1 <=x0 && x0 >barrier.getStart().getX() ) {x1 =x0+1;}
                else if(getPoint().getY()  >= barrier.getStart().getY() && getPoint().getY() <barrier.getEnd().getY()){
                    if( x1<= barrier.getStart().getX()) { x1 = barrier.getStart().getX()+1;}
                }

            }
        }
        setXY(x1, getPoint().getY());
    }
}
