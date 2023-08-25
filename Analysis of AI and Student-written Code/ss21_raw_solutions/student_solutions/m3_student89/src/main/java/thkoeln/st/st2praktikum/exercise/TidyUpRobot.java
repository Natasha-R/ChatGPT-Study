package thkoeln.st.st2praktikum.exercise;



import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;

import javax.persistence.Entity;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
@Entity
@NoArgsConstructor
public class TidyUpRobot extends AbstractEntity  {


    private String name;
    private UUID now_in_room=UUID.randomUUID();
   private int posx=0;
  private   int posy=0;


    public TidyUpRobot(String name){
        this.name=name;
    }




    public UUID getNow_in_room(){
        return now_in_room;
    }

    public String getCoordinates(){
        return "("+posx+","+posy+")";
    }

    public Vector2D getVector2D(){
        return new Vector2D(posx,posy);
    }

    public boolean traverse(UUID roomid, Room room) {
        for (Map.Entry<UUID, Connection> entry : room.getConnectionHashMap().entrySet()) {
            Connection value = entry.getValue();
            if (value.getEntranceRoomID().equals(now_in_room)&&value.getEntranceCoordinates().equals(getVector2D())){
                String[] newCoordinates = value.getExitCoordinates().toString().split((Pattern.quote(",")));
                posx=Integer.parseInt(newCoordinates[0].replaceAll("\\D", ""));
                posy=Integer.parseInt(newCoordinates[newCoordinates.length-1].replaceAll("\\D", ""));
                now_in_room=roomid;
                return true;
            }
        }
        return false;
    }



    public void en(UUID roomid){

        now_in_room=roomid;
        posx=0;
        posy=0;

    }



    public void moveNorth(int moves, Room room){
        int takenSteps=0;
        if (moves>room.getGrid()[0].length-1-posy)
        {
            moves=room.getGrid()[0].length-1-posy;
        }

        while (takenSteps < moves) {
            if (room.getGrid()[posx][posy + takenSteps] == "Wan1" && room.getGrid()[posx][posy + takenSteps + 1] == "Wan2") {
                posy = posy + takenSteps;

                return;

            }


            if (room.getGrid()[posx][posy + takenSteps] == "Wan2" && room.getGrid()[posx][posy + takenSteps + 1] == "Wan1") {
                posy = posy + takenSteps;
                return;
            } else {
                takenSteps += 1;
            }
        }

        posy = posy + takenSteps;

    }



    public void moveEast(int moves, Room room){
        int takenSteps=0;


        if (moves>room.getGrid().length-1-posx)
        {
            moves=room.getGrid().length-1-posx;
        }
        while (takenSteps < moves ) {
            if (room.getGrid()[posx + takenSteps ][posy] == "Wan1"&&room.getGrid()[posx + takenSteps + 1][posy] == "Wan2")
            {
                posx=posx+takenSteps;
                return;

            }
            if (room.getGrid()[posx + takenSteps ][posy] == "Wan2"&&room.getGrid()[posx + takenSteps + 1][posy] == "Wan1")
            {
                posx=posx+takenSteps;
                return;

            }
            else{
                takenSteps += 1;
            }
        }
        posx = posx + takenSteps;

    }



    public void moveSouth(int moves, Room room){
        int takenSteps=0;


        if (posy-moves<0)
        {
            moves=posy;
        }
        while (takenSteps < moves ) {
            if (room.getGrid()[posx][posy - takenSteps ] == "Wan1"&&room.getGrid()[posx][posy - takenSteps - 1] == "Wan2")
            {
                posy = posy - takenSteps;
                return;

            }
            if (room.getGrid()[posx][posy - takenSteps ] == "Wan2"&&room.getGrid()[posx][posy - takenSteps - 1] == "Wan1")
            {
                posy = posy - takenSteps;
                return;

            }

            else {
                takenSteps += 1;
            }
        }
        posy = posy - takenSteps;



    }



    public void moveWest(int moves, Room room){
        int takenSteps=0;
        if (posx-moves<0)
        {
            moves=posx;
        }

        while (takenSteps < moves ) {
            if (room.getGrid()[posx - takenSteps ][posy] == "Wan1"&&room.getGrid()[posx - takenSteps - 1][posy] == "Wan2")
            {
                posx=posx-takenSteps;
                return;
            }
            if (room.getGrid()[posx - takenSteps ][posy] == "Wan2"&&room.getGrid()[posx - takenSteps - 1][posy] == "Wan1")
            {
                posx=posx-takenSteps;
                return;
            }
            else
            {
                takenSteps += 1;
            }
        }
        posx = posx - takenSteps;

    }


    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public UUID getRoomId(){return now_in_room;}

}
