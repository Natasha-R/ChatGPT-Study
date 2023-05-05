package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    Integer current_X = 0;
    Integer current_Y = 2;
    Integer MAX_X = 11;
    Integer MAX_Y = 8;
    @Override
    public String move(String moveCommandString) {
        var direction = moveCommandString.substring(1,3);
        var movement = Integer.valueOf(moveCommandString.substring(4,5));
        if(direction.equals("no")){
            if(current_Y <= 4 && current_X >= 4 && current_X <= 6){
                current_Y += movement;
                if(current_Y > 4) current_Y = 4;
            }
            else{
                current_Y += movement;
                if(current_Y > MAX_Y) current_Y = MAX_Y;
            }
        }
        else if(direction.equals("so")){
            if(current_Y >= 5 && current_X >= 4 && current_X <= 6){
                current_Y -= movement;
                if(current_Y < 5) current_Y = 5;
            }
            else{
                current_Y -= movement;
                if(current_Y < 0) current_Y = 0;
            }
        }
        else if(direction.equals("ea")){
            if(current_X <= 2 && current_Y >= 0 && current_Y <= 2){
                current_X += movement;
                if(current_X > 2) current_X = 2;
            }
            else if(current_X >= 3 && current_X <= 4 && current_Y >= 0 && current_Y <= 2){
                current_X += movement;
                if(current_X > 4) current_X = 4;
            }
            else if(current_X <= 4 && current_Y == 3){
                current_X += movement;
                if(current_X > 4) current_X = 4;
            }
            else if(current_X <= 6 && current_Y >= 5 && current_Y <= 8){
                current_X += movement;
                if(current_X > 6) current_X = 6;
            }
            else{
                current_X += movement;
                if(current_X > MAX_X) current_X = MAX_X;
            }
        }
        else if(direction.equals("we")){
            if(current_X >= 3 && current_X <= 4 && current_Y >= 0 && current_Y <= 2){
                current_X -= movement;
                if(current_X < 3) current_X = 3;
            }
            else if(current_X >= 5 && current_Y >= 0 && current_Y <= 3){
                current_X -= movement;
                if(current_X < 5) current_X = 5;
            }
            else if(current_X >= 7 && current_Y >= 5 && current_Y <= 8){
                current_X -= movement;
                if(current_X < 7) current_X = 7;
            }
            else{
                current_X -= movement;
                if(current_X < 0) current_X = 0;
            }
        }
        String returnString = String.format("(%d,%d)",current_X,current_Y);
        return returnString;
    }
}
