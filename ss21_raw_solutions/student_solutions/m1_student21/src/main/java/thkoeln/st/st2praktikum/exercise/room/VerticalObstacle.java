package thkoeln.st.st2praktikum.exercise.room;

public class VerticalObstacle extends AbstractObstacle {

    @Override
    public boolean collision(int x, int y) {
        if(x==x1 && x==x2){
            if(y1<=y && y<=y2 && y1<=y+1 && y+1<=y2){
                return true;
            }
        }
        return false;
    }

    public VerticalObstacle(int x1,int y1,int x2,int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}
