package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

class CurrentCoordinate extends TidyUpRobotService implements IdReturnableInterface {

    private UUID uuidRobot;
    private Integer x;
    private Integer y;

    public CurrentCoordinate(Integer x, Integer y, UUID uuidRobot){
        this.x = x;
        this.y = y;
        this.uuidRobot = uuidRobot;
    }


    @Override
    public UUID returnUUID() {
        return uuidRobot;
    }



    public Integer getX(){
        return x;
    }
    public void setX(Integer newX){
        x=newX;
    }
    public Integer getY(){
        return y;
    }
    public void setY(Integer newY){
        y=newY;
    }
}
