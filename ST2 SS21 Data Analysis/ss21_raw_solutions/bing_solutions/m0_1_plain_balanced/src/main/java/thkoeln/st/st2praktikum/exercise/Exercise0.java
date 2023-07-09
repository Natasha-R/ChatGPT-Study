package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int[][] walls = {{3,0},{3,1},{3,2},{3,3},{4,3},{5,3},{6,3},{7,3},{7,2},{7,1},{7,0},{1,4},{2,4},{3,4},{4,4},{5,4},{6,4},{7,4}};
    private int[] currentPos = {3,0};
    private int[] roomSize = {12,8};

    public String walkTo(String walkCommandString) {
        String[] walkCommand = walkCommandString.substring(1,walkCommandString.length()-1).split(",");
        int steps = Integer.parseInt(walkCommand[1]);
        switch(walkCommand[0]) {
            case "no":
                for(int i=0; i<steps; i++) {
                    if(currentPos[1]+1>=roomSize[1] || isWall(currentPos[0],currentPos[1]+1)) break;
                    currentPos[1]++;
                }
                break;
            case "ea":
                for(int i=0; i<steps; i++) {
                    if(currentPos[0]+1>=roomSize[0] || isWall(currentPos[0]+1,currentPos[1])) break;
                    currentPos[0]++;
                }
                break;
            case "so":
                for(int i=0; i<steps; i++) {
                    if(currentPos[1]-1<0 || isWall(currentPos[0],currentPos[1]-1)) break;
                    currentPos[1]--;
                }
                break;
            case "we":
                for(int i=0; i<steps; i++) {
                    if(currentPos[0]-1<0 || isWall(currentPos[0]-1,currentPos[1])) break;
                    currentPos[0]--;
                }
                break;
        }
        return "("+currentPos[0]+","+currentPos[1]+")";
    }

    private boolean isWall(int x,int y) {
        for(int[] wall : walls) {
            if(wall[0]==x && wall[1]==y) return true;
        }
        return false;
    }
}