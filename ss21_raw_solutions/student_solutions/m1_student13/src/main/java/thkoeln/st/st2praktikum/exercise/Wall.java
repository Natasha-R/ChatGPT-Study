package thkoeln.st.st2praktikum.exercise;

public class Wall {
   private int xStartPosition;
   private int yStartPosition;
   private int xEndPosition;
   private int yEndPosition;

    public Wall(int xStartPosition, int yStartPosition, int xEndPosition, int yEndPosition){
        this.xStartPosition = xStartPosition;
        this.yStartPosition = yStartPosition;
        this.xEndPosition = xEndPosition;
        this.yEndPosition = yEndPosition;
    }

    public boolean isVertically(){
        return xStartPosition == xEndPosition;
    }

    public int getxStartPosition() {
        return xStartPosition;
    }
    public int getyStartPosition() {
        return yStartPosition;
    }

    public int getxEndPosition() {
        return xEndPosition;
    }
    public int getyEndPosition() {
        return yEndPosition;
    }
}



