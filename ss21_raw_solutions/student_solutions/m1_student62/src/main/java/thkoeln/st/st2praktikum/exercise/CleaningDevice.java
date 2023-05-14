package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class CleaningDevice {
    private UUID id,space;
    private Point Position;
    private String Devicename;

    public CleaningDevice(String name) {
        id= UUID.randomUUID();
        Devicename = name;
    }

    public UUID getId() {
        return id;
    }
    public UUID getspaceId(){
        return space;
    }

    public void enable(UUID spaceid){
        Position = new Point(0,0);
        space = spaceid;
    }

    public boolean move(String commandString, Space sp) {
        boolean stop = false;
        String[] command = commandString.split(",");
        System.out.println();
        for(int i=0;i<Integer.parseInt(command[1].substring(0,command[1].length()-1));i++){
            switch (command[0].substring(1)){
                case "no":
                    for (Wall wall:sp.getWalls()) {
                        if(Position.getY()==sp.getheight()||wall.sameX(Position.getX())&&wall.sameY(Position.getY()+1)&&!wall.isvertical()){
                            stop=true;
                        }
                    }
                    if(sp.getWalls().size()==0&&Position.getX()==sp.getheight()){
                        stop=true;
                    }
                    if(!stop) Position.updateY(1);
                    break;
                case "ea":
                    for (Wall wall:sp.getWalls()) {
                        if(Position.getX()==sp.getwidth()||wall.sameX(Position.getX()+1)&&wall.sameY(Position.getY())&&wall.isvertical()){
                            stop=true;
                        }
                    }
                    if(sp.getWalls().size()==0&&Position.getX()==sp.getwidth()){
                        stop=true;
                    }
                    if(!stop) Position.updateX(1);
                    break;
                case "so":
                    for (Wall wall:sp.getWalls()) {
                        if(Position.getY()==0||(wall.sameX(Position.getX())&&wall.sameY(Position.getY())&&!wall.isvertical())){
                            stop=true;
                        }
                    }
                    if(sp.getWalls()==null&&Position.getY()==0){
                        stop=true;
                    }
                    if(!stop) Position.updateY(-1);
                    break;
                case "we":
                    for (Wall wall:sp.getWalls()) {
                        if(Position.getX()==0||(wall.sameX(Position.getX())&&wall.sameY(Position.getY())&&wall.isvertical())){
                            stop=true;
                        }
                    }
                    if(sp.getWalls().size()==0&&Position.getX()==0){
                        stop=true;
                    }
                    if(!stop) Position.updateX(-1);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        return true;
    }

    public String getposition() {
        return "("+Position.getX()+","+Position.getY()+")";
    }

    public boolean transport(Space sp, String spaceid) {
        boolean result=false;
        for(Connector con: sp.getConnectors()){
            if(con.getLocation1().getX()==Position.getX()&&con.getLocation1().getY()==Position.getY()){
                if(con.getSpacedestination().equals(UUID.fromString(spaceid))){
                    space=UUID.fromString(spaceid);
                    Position=con.getLocation2();
                    result = true;
                }
            }
        }
        return result;
    }
}
