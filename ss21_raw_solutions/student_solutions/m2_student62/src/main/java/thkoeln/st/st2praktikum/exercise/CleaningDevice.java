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

    public boolean move(Command command, Space sp) {
        boolean stop = false;
        for(int i=0;i<command.getNumberOfSteps();i++){
            switch (command.getCommandType()){
                case NORTH:
                    for (Wall wall:sp.getWalls()) {
                        if(Position.getY()==sp.getheight()||wall.sameX(Position.getX())&&wall.sameY(Position.getY()+1)&&!wall.isVertical()){
                            stop=true;
                        }
                    }
                    if(sp.getWalls().size()==0&&Position.getX()==sp.getheight()){
                        stop=true;
                    }
                    if(!stop) Position.updateY(1);
                    break;
                case EAST:
                    for (Wall wall:sp.getWalls()) {
                        if(Position.getX()==sp.getwidth()||wall.sameX(Position.getX()+1)&&wall.sameY(Position.getY())&&wall.isVertical()){
                            stop=true;
                        }
                    }
                    if(sp.getWalls().size()==0&&Position.getX()==sp.getwidth()){
                        stop=true;
                    }
                    if(!stop) Position.updateX(1);
                    break;
                case SOUTH:
                    for (Wall wall:sp.getWalls()) {
                        if(Position.getY()==0||(wall.sameX(Position.getX())&&wall.sameY(Position.getY())&&!wall.isVertical())){
                            stop=true;
                        }
                    }
                    if(sp.getWalls()==null&&Position.getY()==0){
                        stop=true;
                    }
                    if(!stop) Position.updateY(-1);
                    break;
                case WEST:
                    for (Wall wall:sp.getWalls()) {
                        if(Position.getX()==0||(wall.sameX(Position.getX())&&wall.sameY(Position.getY())&&wall.isVertical())){
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

    public Point getposition() {
        return Position;
    }

    public boolean transport(Space sp, UUID spaceid) {
        boolean result=false;
        for(Connector con: sp.getConnectors()){
            if(con.getLocation1().getX()==Position.getX()&&con.getLocation1().getY()==Position.getY()){
                if(con.getSpacedestination().equals(spaceid)){
                    space=spaceid;
                    Position=con.getLocation2();
                    result = true;
                }
            }
        }
        return result;
    }
}
