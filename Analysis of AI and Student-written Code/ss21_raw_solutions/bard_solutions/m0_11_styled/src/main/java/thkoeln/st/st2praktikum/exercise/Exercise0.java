package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable{
    private static final int WIDTH = 11;
    private static final int HEIGHT = 8;
    private static final int START_X = 7;
    private static final int START_Y = 7;
    private static final int[] BARRIERS_X = {2, 10};
    private static final int[] BARRIERS_Y = {1, 6, 7};

    private int x;
    private int y;

    public Exercise0(){
        this.x=START_X;
        this.y=START_Y;
    }

    @Override
    public String move(String moveCommandString){
        String[] parts = moveCommandString.substring(1, moveCommandString.length()-1).split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        for(int i=0; i<steps; i++){
            switch(direction){
                case "no":
                    if(y-1<0 || isBarrier(x, y-1)){
                        break;
                    }
                    y--;
                    break;
                case "ea":
                    if(x+1>=WIDTH || isBarrier(x+1, y)){
                        break;
                    }
                    x++;
                    break;
                case "so":
                    if(y+1>=HEIGHT || isBarrier(x, y+1)){
                        break;
                    }
                    y++;
                    break;
                case "we":
                    if(x-1<0 || isBarrier(x-1, y)){
                        break;
                    }
                    x--;
                    break;
            }
        }

        return "("+x+","+y+")";
    }

    private boolean isBarrier(int x, int y){
        for(int i=0; i<BARRIERS_X.length; i++){
            if(x==BARRIERS_X[i]){
                if(y<=BARRIERS_Y[i] || y>=BARRIERS_Y[i+1]){
                    return true;
                }
            }
        }
        return false;
    }
}