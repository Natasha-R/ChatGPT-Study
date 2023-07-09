package thkoeln.st.st2praktikum.exercise.room;

public class HorizontalObstacle extends AbstractObstacle {

    @Override
    public boolean collision(int x, int y) {
        if(y==y1 && y==y2){
            if(x1<=x && x<=x2 && x1<=(x+1) && (x+1)<=x2){
                return true;
            }
        }
        return false;
    }

    public HorizontalObstacle(int x1,int y1,int x2,int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}
