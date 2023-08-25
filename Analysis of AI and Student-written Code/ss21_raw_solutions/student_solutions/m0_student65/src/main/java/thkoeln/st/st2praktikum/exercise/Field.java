package thkoeln.st.st2praktikum.exercise;

import java.util.Vector;

public class Field {
    public Vector<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(Vector<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    private Vector<Obstacle> obstacles;
    Field(){
        obstacles = new Vector<Obstacle>();
        obstacles.add(new Obstacle(new Vector2d(0,0),new Vector2d(0,9)));
        obstacles.add(new Obstacle(new Vector2d(0,9),new Vector2d(12,9)));
        obstacles.add(new Obstacle(new Vector2d(12,0),new Vector2d(12,9)));
        obstacles.add(new Obstacle(new Vector2d(0,0),new Vector2d(12,0)));

        obstacles.add(new Obstacle(new Vector2d(3,0),new Vector2d(3,3)));
        obstacles.add(new Obstacle(new Vector2d(5,0),new Vector2d(5,4)));
        obstacles.add(new Obstacle(new Vector2d(4,5),new Vector2d(7,5)));
        obstacles.add(new Obstacle(new Vector2d(7,5),new Vector2d(7,9)));
    }
}
