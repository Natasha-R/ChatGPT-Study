package thkoeln.st.st2praktikum.exercise;

public class Obstacle {         //startP.x has to be smaller than endP.x      Same for y
    public Vector2d getStartP() {
        return startP;
    }

    public void setStartP(Vector2d startP) {
        this.startP = startP;
    }

    public Vector2d getEndP() {
        return endP;
    }

    public void setEndP(Vector2d endP) {
        this.endP = endP;
    }

    private Vector2d startP;
    private Vector2d endP;
    public Obstacle(Vector2d startP, Vector2d endP)
    {
        this.startP=startP;
        this.endP=endP;
    }
}

