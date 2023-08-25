package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Wall implements Placable{

    private Point start;
    private Point end;
    private Boolean isHorizontal;
    private UUID wallId;
    private UUID destinationRoomId;


    public Wall(Point pos1, Point pos2) {
        this.start = pos1;
        this.end = pos2;
        this.wallId = UUID.randomUUID();
        if(!isStartCloserToBottomLeft(start,end)){
            switchStartEndPoint();
        }
        if(isDiagonally(start,end)){throw new RuntimeException("a wall must be either vertical or horizontal");}
        this.isHorizontal = isHorizontal(start,end);
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString){
        if(isStringValid(wallString)){
            this.start = parseFirstPoint(wallString);
            this.end = parseSecondPoint(wallString);
            this.wallId = UUID.randomUUID();
            if(!isStartCloserToBottomLeft(start, end)){switchStartEndPoint();}
            this.isHorizontal = isHorizontal(this.start, this.end);
        }
    }

    private void switchStartEndPoint(){
        Point tempPoint;
        tempPoint=this.start;
        this.start= this.end;
        this.end=tempPoint;
    }
    private Boolean isStringValid(String wallString){
        Point firstPoint = parseFirstPoint(wallString);
        Point secondPoint = parseSecondPoint(wallString);
        if (isDiagonally(firstPoint,secondPoint)){throw new RuntimeException("a wall must be either vertical or horizontal");}
        else {return isNotOutOfBounds(firstPoint, secondPoint);}
        }

    private Point parseFirstPoint(String wallString){
        Point firstPoint = new Point(wallString.substring(0,wallString.indexOf("-")));
        return firstPoint;
    }

    private  Point parseSecondPoint(String wallString){
        Point secondPoint = new Point(wallString.substring(wallString.indexOf("-")+1,wallString.lastIndexOf(")")+1));
        return secondPoint;
    }
    private Boolean isNotOutOfBounds(Point firstPoint, Point secondPoint){
        if(firstPoint.getY()<0||firstPoint.getX()<0||secondPoint.getY()<0||secondPoint.getX()<0){
            return false;
        }
        else return true;
    }

    private Boolean isHorizontal(Point firstPoint, Point secondPoint){
      if(firstPoint.getY()==secondPoint.getY()){
          return true;
      }
      else return false;
    }

    private Boolean isDiagonally(Point firstPoint, Point secondPoint){
        if(firstPoint.getY()!=secondPoint.getY()&&firstPoint.getX()!=secondPoint.getX()){
            return true;
        }
        else return false;
    }

    private Boolean isWallOutOfBounds(Point start ,Point end, UUID destinationRoomId ){
        Integer roomMaxWidth = RoomMap.getByUUID(destinationRoomId).getWidth();
        Integer roomMaxHeight = RoomMap.getByUUID(destinationRoomId).getHeight();
        if(start.getX()>roomMaxWidth||start.getY()>roomMaxHeight||end.getX()>roomMaxWidth||end.getY()>roomMaxHeight){
            return true;
        }
        else return false;
    }

    private Boolean isStartCloserToBottomLeft(Point firstPoint, Point secondPoint){
        if(firstPoint.getX()>secondPoint.getX()||firstPoint.getY()>secondPoint.getY()){
           return false;
        }
        else return true;
    }

    public void placeWallInRoom(UUID roomId){
        this.destinationRoomId = roomId;
        if(isWallOutOfBounds(this.start,this.end, this.destinationRoomId)){throw new RuntimeException("Wall out of Bounds");}
    }

    public UUID getWallId(){
        return wallId;
    }

    public Boolean getIsHorizontal(){
        return isHorizontal;
    }

    public Point getStart() {
        return start;
    }

    public UUID getDestinationRoomId(){
        return  destinationRoomId;
    }

    public Point getEnd() {
        return end;
    }
}
