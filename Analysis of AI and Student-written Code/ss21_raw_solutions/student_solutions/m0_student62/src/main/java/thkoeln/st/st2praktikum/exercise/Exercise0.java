package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
int x= 8;
int y=3;
Wall[] borders;
    public Exercise0(){
        borders = new Wall[15];
        borders[0] = new Wall(4,1,true);
        borders[1] = new Wall(4,2 ,true);
        borders[2] = new Wall(4,3 ,true);
        borders[3] = new Wall(4,4 ,true);
        borders[4] = new Wall(4,5 ,true);
        borders[5] = new Wall(4,6 ,true);
        borders[6] = new Wall(6,2 ,true);
        borders[7] = new Wall(6,3,true);
        borders[8] = new Wall(6,4,true);
        borders[9] = new Wall(6,2,false);
        borders[10] = new Wall(6,5,false);
        borders[11] = new Wall(7,2,false);
        borders[12] = new Wall(7,5,false);
        borders[13] = new Wall(8,2,false);
        borders[14] = new Wall(8,5,false);
    }
    @Override
    public String walk(String walkCommandString) {
        walkCommandString = walkCommandString.substring(1, walkCommandString.length() - 1);
        String[] commands = walkCommandString.split(",");
        boolean stop=false;
        for(int i=0;i<Integer.parseInt(commands[1]);i++){
            switch (commands[0]){
                case "no":
                    for (Wall w:borders) {
                        if(w.x==x&&w.y==y+1&&!w.vertical){
                            stop=true;
                        }
                    }
                    if(!stop) y+=1;
                    break;
                case "ea":
                    for (Wall w:borders) {
                        if(w.x==x+1&&w.y==y&&w.vertical){
                            stop=true;
                        }
                    }
                    if(!stop) x+=1;
                    break;
                case "so":
                    for (Wall w:borders) {
                        if(w.x==x&&w.y==y&&!w.vertical){
                            stop=true;
                        }
                    }
                    if(!stop) y-=1;
                    break;
                case "we":
                    for (Wall w:borders) {
                        if(w.x==x&&w.y==y&&w.vertical){
                            stop=true;
                        }
                    }
                    if(!stop) x-=1;
                    break;
            }
        }
        return "("+x+","+y+")";
    }
}
