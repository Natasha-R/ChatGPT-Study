package thkoeln.st.st2praktikum.exercise;
import java.util.*;
public class Exercise0 implements thkoeln.st.st2praktikum.Walkable{
    int width = 12;
    int height = 9;
    HashMap<Integer[], Integer[]>[] walls = {{{{0, 3}, {3, 6}}, {5, 8}}}; // Wall Locations
    Scanner scanner = new Scanner(System.in);
    public boolean validCommand(String[] cmd) {
        if (cmd.length != 2) return false;
        for (int i=0; i<cmd[0].length(); ++i) {
            char c = cmd[0].charAt(i);
            switch (c) {
                case 'n':
                    return true;
                case 'e':
                    return true;
                case 's':
                    return true;
                case 'w':
                    return true;
            }
        }
        return false;
    };

    public int[] move(String cmd){

        int xOffSet = xFromCmd(cmd.substring(0, cmd.indexOf('[')));
        int yOffSet = yFromCmd(cmd.substring(cmd.indexOf('[')+1, cmd.indexOf(']')));

        int tempX = currentLocation().getX() + xOffSet;
        int tempY = currentLocation().getY() + yOffSet;

        for (HashMap<Integer[],Integer[]> wall : walls) {
            for(int i=0; i<wall.size();++i) {
                int tempTempX = tempX;
                int tempTempY = tempY;
                while(wall.get(tempTempX,tempTempY)[0] > 0 && tempTempX >= 0 || tempTempX <width )
                {
                    tempTempX += xOffSet;
                }
                while(wall.get(tempTempX,tempTempY)[1] > 0 && tempTempY>=0 || tempTempY<height)
                {
                    tempTempY+=yOffSet;
                }
                if((tempX == (xTempTempX - (xOffSet*(xTempTempX/Math.abs(Math.abs(wall.get(xTempTempX,tempTempY)[0])))))&& wall.get(tempTempX,tempTempY)[0]*-1==Math.signum(xOffSet))||
                        (tempY == (yTempTempY - (yOffSet*((yTempTempY)/Math.abs(Math.abs(wall.get(xTempTempX,tempTempY)[1])))))){
                    System.out.println("Collision Detected");
                }
                else{
                    tempX=xTempTempTempX;
                    tempY=yTempTempY;
                }
            }
        }
    }
       
      return new int[]{currentLocation().getX(),currentLocation().getY()};

};

private IntHolder location(){
        return new IntHolder();
        }

private class IntHolder {
    int x = 0;
    int y = 0;
    public void getLoc(int[] temp_loc){
        temp_loc[0]=x;
        temp_loc[1]=y;
    }
}
    
}