package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Droid {
  private UUID droidId, spaceShipId;
  private Point Position;
  private String Devicename;

  public Droid(String name) {
    droidId = UUID.randomUUID();
    Devicename = name;
  }

  public UUID getDroidId() {
    return droidId;
  }
  public UUID getspaceId(){
    return spaceShipId;
  }

  public void enable(UUID spaceid){
    Position = new Point(0,0);
    spaceShipId = spaceid;
  }

  public boolean move(String commandString, SpaceShipDeck sp) {
    boolean stop = false;
    String[] command = commandString.split(",");
    System.out.println();
    for(int i=0;i<Integer.parseInt(command[1].substring(0,command[1].length()-1));i++){
      switch (command[0].substring(1)){
        case "no":
          for (Obstacle obstacle :sp.getObstacles()) {
            if(Position.getY()==sp.getheight()|| obstacle.sameX(Position.getX())&& obstacle.sameY(Position.getY()+1)&&!obstacle.isvertical()){
              stop=true;
            }
          }
          if(sp.getObstacles().size()==0&&Position.getX()==sp.getheight()){
            stop=true;
          }
          if(!stop) Position.updateY(1);
          break;
        case "ea":
          for (Obstacle obstacle :sp.getObstacles()) {
            if(Position.getX()==sp.getwidth()|| obstacle.sameX(Position.getX()+1)&& obstacle.sameY(Position.getY())&& obstacle.isvertical()){
              stop=true;
            }
          }
          if(sp.getObstacles().size()==0&&Position.getX()==sp.getwidth()){
            stop=true;
          }
          if(!stop) Position.updateX(1);
          break;
        case "so":
          for (Obstacle obstacle :sp.getObstacles()) {
            if(Position.getY()==0||(obstacle.sameX(Position.getX())&& obstacle.sameY(Position.getY())&&!obstacle.isvertical())){
              stop=true;
            }
          }
          if(sp.getObstacles()==null&&Position.getY()==0){
            stop=true;
          }
          if(!stop) Position.updateY(-1);
          break;
        case "we":
          for (Obstacle obstacle :sp.getObstacles()) {
            if(Position.getX()==0||(obstacle.sameX(Position.getX())&& obstacle.sameY(Position.getY())&& obstacle.isvertical())){
              stop=true;
            }
          }
          if(sp.getObstacles().size()==0&&Position.getX()==0){
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

  public boolean transport(SpaceShipDeck sp, String spaceid) {
    boolean result=false;
    for(Connector con: sp.getConnectors()){
      if(con.getLocation1().getX()==Position.getX()&&con.getLocation1().getY()==Position.getY()){
        if(con.getSpacedestination().equals(UUID.fromString(spaceid))){
          spaceShipId =UUID.fromString(spaceid);
          Position=con.getLocation2();
          result = true;
        }
      }
    }
    return result;
  }
}




