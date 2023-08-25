package thkoeln.st.st2praktikum.exercise;

public class Obstacles {
    private boolean[][] obstaclesX;
    private boolean[][] obstaclesY;
    public Obstacles(int pHeight,int pWidth){
        obstaclesX=new boolean[pHeight+1][pWidth+1];
        obstaclesY=new boolean[pHeight+1][pWidth+1];
    }

    public void addObstacleLineX(int[] koordinatenFuerObstacle){
        for(int i=koordinatenFuerObstacle[1];i<=koordinatenFuerObstacle[3];i++){
            obstaclesY[koordinatenFuerObstacle[0]][i]=true;
        }
    }

    public void addObstacleLineY(int[] koordinatenFuerObstacle){
        for(int i=koordinatenFuerObstacle[0];i<=koordinatenFuerObstacle[2];i++){
            obstaclesX[i][koordinatenFuerObstacle[1]]=true;
        }
    }

    public boolean isBlockedNorth(int x,int y){
        if(obstaclesX[x][y+1] && obstaclesX[x+1][y+1])return true;
        return false;
    }

    public boolean isBlockedSouth(int x,int y){
        if(obstaclesX[x][y] && obstaclesX[x+1][y])return true;
        return false;
    }

    public boolean isBlockedEast(int x,int y){
        if(obstaclesY[x+1][y] && obstaclesY[x+1][y+1])return true;
        return false;
    }

    public boolean isBlockedWest(int x,int y){
        if(obstaclesY[x][y] && obstaclesY[x][y+1])return true;
        return false;
    }
}
