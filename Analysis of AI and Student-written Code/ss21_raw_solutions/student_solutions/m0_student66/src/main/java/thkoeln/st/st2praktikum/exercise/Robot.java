package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

@Getter
public class Robot {
    private Coordinate position;

    public Robot(Coordinate position){
        this.position = position;
    }

    public void move(String direction){
        switch (direction){
            case "no":
                position.setY(position.getY()+1);
                break;
            case "so":
                position.setY(position.getY()-1);
                break;
            case "we":
                position.setX(position.getX()-1);
                break;
            case "ea":
                position.setX(position.getX()+1);
                break;
        }
    }
}
