package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    // 9x12 da noch die äußeren Ränder selber Grenzen sind
    public int[][] room = {
            {0,0,0,0,0,0,0,0,0,0,0,3},
            {0,0,0,0,0,0,0,0,0,1,0,3},
            {0,0,0,0,0,0,0,0,0,1,0,3},
            {0,0,0,0,0,0,0,0,0,1,0,3},
            {0,0,0,0,0,0,0,0,0,1,0,3},
            {0,1,1,1,1,1,1,1,1,1,0,3},
            {2,2,2,3,0,0,0,0,0,0,0,3},
            {0,0,0,3,0,0,0,0,0,0,0,3},
            {3,3,3,3,3,3,3,3,3,3,3,3}
    };
    // initial Value serves as starting Position
    public int[] currPos = {6,1};

    @Override
    public String move(String moveCommandString) {
        // no +,0 | ea 0,+ | so -,0 | we 0,-
        // java arrays[y-axis][x-axis]

        String numb = moveCommandString.substring(moveCommandString.indexOf(",")+1,moveCommandString.indexOf("]"));
        int steps = Integer.parseInt(numb);
        while(steps!=0){
            if(moveCommandString.contains("no")){
                if(currPos[0]+1 <= 8 && currPos[1]+1 <= 11){
                    if((room[currPos[0]+1][currPos[1]]
                       != room[currPos[0]+1][currPos[1]+1]
                       || room[currPos[0]+1][currPos[1]]==0
                       || room[currPos[0]+1][currPos[1]+1]==0)
                       && currPos[0]+1 < 8){
                        this.currPos[0] += 1;
                    }
                }
            }
            if(moveCommandString.contains("so")){
                if(currPos[1]+1 <= 11){
                    if((room[currPos[0]][currPos[1]]
                       != room[currPos[0]][currPos[1]+1]
                       || room[currPos[0]][currPos[1]]==0
                       || room[currPos[0]][currPos[1]+1]==0)
                       && currPos[0]-1 >= 0){
                        this.currPos[0] -= 1;
                    }
                }
            }
            if(moveCommandString.contains("ea")){
                if(currPos[0]+1 <= 8 && currPos[1]+1 <= 11){
                    if((room[currPos[0]][currPos[1]+1]
                       != room[currPos[0]+1][currPos[1]+1]
                       || room[currPos[0]][currPos[1]+1]==0
                       || room[currPos[0]+1][currPos[1]+1]==0)
                       && currPos[1]+1 < 11){
                        this.currPos[1] += 1;
                    }
                }
            }
            if(moveCommandString.contains("we")){
                if(currPos[0]+1 <= 8){
                    if((room[currPos[0]][currPos[1]]
                       != room[currPos[0]+1][currPos[1]]
                       || room[currPos[0]][currPos[1]]==0
                       || room[currPos[0]+1][currPos[1]]==0)
                       && currPos[1]-1 >= 0){
                        this.currPos[1] -= 1;
                    }
                }
            }
            steps--;
        }
        String retPos = "("+currPos[1]+","+currPos[0]+")";
        return retPos;
    }
}
