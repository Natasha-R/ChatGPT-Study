package thkoeln.st.st2praktikum.exercise;



import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class TidyUpRobot implements Moveable, Traversable, Encompassable {

    private String name;
    private UUID now_in_room=UUID.randomUUID();
    int posx=0;
    int posy=0;


    TidyUpRobot(String name){
        this.name=name;
    }




    public UUID getNow_in_room(){
        return now_in_room;
    }

    public String getCoordinates(){
        return "("+posx+","+posy+")";
    }

    @Override
    public boolean traverse(UUID roomid, Room room) {
        for (Map.Entry<UUID, Connection> entry : room.getConnectionHashMap().entrySet()) {
            UUID key = entry.getKey();
            Connection value = entry.getValue();
            if (value.getEntranceRoomID().equals(now_in_room)&&value.getEntranceCoordinates().equals(getCoordinates())){
                String[] newCoordinates = value.getExitCoordinates().split((Pattern.quote(",")));
                posx=Integer.parseInt(newCoordinates[0].replaceAll("\\D", ""));
                posy=Integer.parseInt(newCoordinates[newCoordinates.length-1].replaceAll("\\D", ""));
                now_in_room=roomid;
                return true;
            }
        }
            return false;
        }


    @Override
    public void en(UUID roomid){

                    now_in_room=roomid;
                    posx=0;
                    posy=0;

        }


    @Override
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
                //System.out.println( "(" + posx + "," + posy + ")");

            }


            if (room.getGrid()[posx][posy + takenSteps] == "Wan2" && room.getGrid()[posx][posy + takenSteps + 1] == "Wan1") {
                posy = posy + takenSteps;
                //System.out.println( "(" + posx + "," + posy + ")");
                return;
            } else {
                takenSteps += 1;
            }
        }

        posy = posy + takenSteps;

    }


    @Override
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


    @Override
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


    @Override
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
}
