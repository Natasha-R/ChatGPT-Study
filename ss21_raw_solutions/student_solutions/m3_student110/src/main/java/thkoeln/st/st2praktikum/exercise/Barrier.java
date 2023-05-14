package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity

public class Barrier extends TidyUpRobotService  {

    @Id
    @Getter
    private UUID barrierId = UUID.randomUUID();


    @Getter
    @Setter
    @Embedded
    private Coordinate start;


    @Getter
    @Setter
    @Embedded
    private Coordinate end;

    @Getter
    @Setter
    private UUID roomUUID;

    @Getter
    @Setter
    private String barrierString;

    @Getter
    @Setter
    private Boolean isValidBarrier;

    @Getter
    @Setter
    @OneToOne
    private Room roomOfBarrier;



    public Barrier(){}



    public Barrier(Coordinate pos1, Coordinate pos2){
        this.start = pos1;
        this.end = pos2;


        if (!correctStart(this)){
            this.start=pos2;
            this.end=pos1;
        }
        this.isValidBarrier = isValidBarrier(this,pos1.toString().length()+pos2.toString().length()+1);

    }

    public Barrier(UUID roomID, String barrierString){
        this.roomUUID=roomID;
        this.barrierString=barrierString;
        this.roomOfBarrier=super.getRoom(roomID);





        // this.isValidBarrier=isValidBarrier(this,barrierString.length());

    }

    public Barrier(Room room, String barrierString){
        System.out.println(roomOfBarrier.getHeight()+"DIE ROOM ID");
        this.roomOfBarrier=super.getRoom(roomUUID);


        this.barrierString=barrierString;

        this.isValidBarrier = isValidBarrier(this,barrierString.length());
    }


    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString){
        this.barrierString = barrierString;
        this.start = new Coordinate(barrierString.substring(0,5));
        this.end = new Coordinate(barrierString.substring(6,barrierString.length()));
        this.roomUUID = null;
        this.isValidBarrier = isValidBarrier(this,barrierString.length());

    }



    public String getBarrierString() {
        if (barrierString == null){
            String  newerBarrierString =getStart().toString()+"-"+getEnd().toString();
            return newerBarrierString;
        }
        else return this.barrierString;

    }

    public Coordinate getStart() {
        if (barrierString == null){
            return start;
        }
        else return new Coordinate(barrierString.substring(0,5));
    }

    public Coordinate getEnd() {
        if (barrierString == null){
            return end;
        }
        else return new Coordinate(barrierString.substring(6,barrierString.length()));
    }



    public List<BarrierInRoom> dissolveAllBarrierStrings(){
        UUID roomId = this.roomUUID;

        List<BarrierInRoom> solvedCoordinatesInRoom = new ArrayList<>();
        Integer firstX = Integer.parseInt(barrierString.substring(1,2));
        Integer firstY = Integer.parseInt(barrierString.substring(3,4));
        Integer secondX = Integer.parseInt(barrierString.substring(7,8));
        Integer secondY = Integer.parseInt(barrierString.substring(9,10));

        Integer smallerX;
        Integer higherX;
        Integer smallerY;
        Integer higherY;

        if (firstX<secondX){
            smallerX=firstX;
            higherX=secondX;
        }
        else{
            smallerX=secondX;
            higherX=firstX;
        }
        if (firstY<secondY){
            smallerY=firstY;
            higherY=secondY;
        }
        else{
            smallerY=secondY;
            higherY=firstY;
        }
        if (firstY==secondY){
            for (int i = smallerX; i<higherX && firstY==secondY; i++){
                String newCoordinate=("("+i+","+firstY+")");
                BarrierInRoom bir = new BarrierInRoom(newCoordinate,roomId);
                solvedCoordinatesInRoom.add(bir);
            }
        }
        else {
            for (int i = smallerY; i<higherY && firstX==secondX; i++){
                String newCoordinate1=("("+firstX+","+i+")");
                BarrierInRoom bir = new BarrierInRoom(newCoordinate1,roomId);
                solvedCoordinatesInRoom.add(bir);
            }
        }
        return solvedCoordinatesInRoom;
    }
    public Boolean isValidBarrier(Barrier barrier, Integer barrierLength){
        // Integer barrierLength = barrier.barrierString.length();


        for (int i =0;i<allRooms.size();i++){
            System.out.println(allRooms.get(i).getHeight());
        }

        Coordinate start =barrier.getStart();
        Coordinate end = barrier.getEnd();


        if (this.roomUUID != null){

            if (start.getX()>getRoom(this.roomUUID).getWidth()||end.getX()>getRoom(this.roomUUID).getWidth() || start.getY()>getRoom(this.roomUUID).getHeight()||end.getY()>getRoom(this.roomUUID).getHeight()
                    || start.getX()<0 || start.getY()<0 || end.getX()<0 || end.getY()<0
            ){
                throw new IllegalArgumentException("Barrier out of room boundaries!!!");

            }
        }


        if (barrierLength==11 && barrier.getStart().isValidCoordinate(barrier.getStart()) && barrier.getEnd().isValidCoordinate(barrier.getStart()) && barrier.getBarrierString().contains("-")){


            if (start.getX()==end.getX() && start.getY()!=end.getY() || start.getY()==end.getY() && start.getX()!=end.getX()){

                return true;
            }
            else throw new IllegalArgumentException("Invalid Barrier");

        }
        else throw new IllegalArgumentException("Invalid Barrier");
    }

    public Boolean correctStart(Barrier barrier){
        Coordinate c1 = barrier.getStart();
        Coordinate c2 = barrier.getEnd();

        Integer c1Value = c1.getX() + c1.getY();
        Integer c2Value = c2.getX() + c2.getY();

        if (c1Value<c2Value){
            return true;
        }
        else return false;
    }


}
