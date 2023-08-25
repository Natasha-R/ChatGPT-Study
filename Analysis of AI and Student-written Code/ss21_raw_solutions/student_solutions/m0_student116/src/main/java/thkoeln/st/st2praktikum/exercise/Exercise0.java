package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int x;
    private int y;
    char[] c=new char[2];
    @Override
    public String walk(String walkCommandString){
        c[0]=walkCommandString.charAt(1);
        c[1]=walkCommandString.charAt(2);
        String []h=walkCommandString.split(",");
        String j=h[1];
        j=j.replace("]","");
        int b=Integer.parseInt(j);
        if(c[0]=='n'&&c[1]=='o'){
            goNorth(b);
        }

        else if(c[0]=='s'&&c[1]=='o'){
            goSouth(b);
        }

        else if(c[0]=='e'&&c[1]=='a'){
            goEast(b);
        }

        else if(c[0]=='w'&&c[1]=='e'){
            goWest(b);
        }
        else{
            return "-1";
        }
        return "("+x+","+y+")";
    }
    boolean [] [] obstaclesX = new boolean[12][9];
    boolean [] [] obstaclesY = new boolean[12][9];
    Exercise0() {
        x = 1;
        y = 6;
        obstaclesY[0][6] = true;
        obstaclesY[1][6] = true;
        obstaclesY[2][6] = true;

        obstaclesY[1][5] = true;
        obstaclesY[2][5] = true;
        obstaclesY[3][5] = true;
        obstaclesY[4][5] = true;
        obstaclesY[5][5] = true;
        obstaclesY[6][5] = true;
        obstaclesY[7][5] = true;
        obstaclesY[8][5] = true;
        obstaclesY[9][5] = true;

        obstaclesX[9][1] = true;
        obstaclesX[9][2] = true;
        obstaclesX[9][3] = true;
        obstaclesX[9][4] = true;
        obstaclesX[9][5] = true;

        obstaclesX[3][6] = true;
        obstaclesX[3][7] = true;
        obstaclesX[3][8] = true;
    }
    private void goNorth(int steps){
        if(y<7 && steps>0){
            if(!obstaclesY[x][y+1] || !obstaclesY[x+1][y+1]) {
                y++;
                goNorth(steps - 1);
            }
        }
    }

    private void goSouth(int steps){
        if(y>0 && steps>0){
            if(!obstaclesY[x][y] || !obstaclesY[x+1][y]) {
                y--;
                goSouth(steps - 1);
            }
        }
    }

    private void goEast(int steps){
        if(x<10 && steps>0){
            if(!obstaclesX[x+1][y] || !obstaclesX[x+1][y+1]) {
                x++;
                goEast(steps - 1);
            }
        }
    }

    private void goWest(int steps){
        if(x>0 && steps>0){
            if(!obstaclesX[x][y] || !obstaclesX[x][y+1]) {
                x--;
                goWest(steps - 1);
            }
        }
    }

}
