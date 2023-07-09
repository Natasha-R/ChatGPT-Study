package thkoeln.st.st2praktikum.exercise;


public class Exercise0 implements Walkable {
    int[] currentPosition = new int[] {1,7};
    int[][] obstacles = new int[][] {
        // vertical     0,x,x,y,y
        // horizontal   1,y,y,x,x
        {0,2,3,3,9},
        {0,5,6,0,3},
        {0,4,5,0,1},
        {0,-1,0,0,9},
        {0,12,13,0,9},
        {1,2,3,3,4},
        {1,-1,0,0,12},
        {1,9,10,0,12}
    };


    @Override
    public String walkTo(String walkCommandString) {

        String direction = walkCommandString.split(",")[0].replace("[", "");
        int steps = Integer.parseInt(walkCommandString.split(",")[1].replace("]", ""));


        moveloop:
        for(int i = 0; i < steps; i++) {


            if(direction.equals("we")) {
                for(int a = 0; a < obstacles.length; a++) {
                    if(
                        // if obstacle is relevant for move
                        (obstacles[a][0] == 0)
                        // if current position is in range of obstacle
                        && (currentPosition[1] >= obstacles[a][3] && currentPosition[1] <= obstacles[a][4])
                        // if collision is ahead
                        && (currentPosition[0] == obstacles[a][2])
                    ) break moveloop;
                }

                currentPosition[0]--;
            }


            else if(direction.equals("ea")) {
                for(int a = 0; a < obstacles.length; a++) {
                    if(
                        (obstacles[a][0] == 0)
                        && (currentPosition[1] >= obstacles[a][3] && currentPosition[1] <= obstacles[a][4])
                        && (currentPosition[0] == obstacles[a][1])
                    ) break moveloop;
                }

                currentPosition[0]++;
            }
            

            else if(direction.equals("no")) {
                for(int a = 0; a < obstacles.length; a++) {
                    if(
                        (obstacles[a][0] == 1)
                        && (currentPosition[0] >= obstacles[a][3] && currentPosition[0] <= obstacles[a][4])
                        && (currentPosition[1] == obstacles[a][1])
                    ) break moveloop;
                }

                currentPosition[1]++;
            }


            else if(direction.equals("so")) {
                for(int a = 0; a < obstacles.length; a++) {
                    if(
                        (obstacles[a][0] == 1)
                        && (currentPosition[0] >= obstacles[a][3] && currentPosition[0] <= obstacles[a][4])
                        && (currentPosition[1] == obstacles[a][2])
                    ) break moveloop;
                }

                currentPosition[1]--;
            }
        }
        
        System.out.println(walkCommandString);
        System.out.println("current Position: (" + currentPosition[0] + "," + currentPosition[1] + ")");
        return "(" + currentPosition[0] + "," + currentPosition[1] + ")";
    }
}