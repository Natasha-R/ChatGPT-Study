package thkoeln.st.st2praktikum.exercise;

public class Position {

    public static final String DEFAULT = "(0,0)";

    private int x;

    private int y;

    public Position(String pos) {
        pos=pos.replace("(", "").replace(")", "");
        String[] posArray = pos.split(",");
        this.x = Integer.parseInt(posArray[0]);
        this.y = Integer.parseInt(posArray[1]);
    }

    public void incrementX(){
        x++;
    }

    public void decrementX(){
        x--;
    }

    public void incrementY(){
        y++;
    }

    public void decrementY(){
        y--;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getPositionString(){
        return "("+x+","+y+")";
    }

}
