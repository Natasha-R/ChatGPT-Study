package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    int x = 1;
    int y = 7;

    int[][] mauernx = {{4,3}};
    int[][] mauerny = {{3, 3}, {3, 4}, {3, 5}, {3, 6}, {3, 7}, {3, 8}, {3, 9},{5,0},{5,1},{6,3},{6,2},{6,1},{6,0}};

    Mauer[] wall;
    public Exercise0 () {
        wall= new Mauer[14];
        wall[0]=new Mauer(3,3,true);
        wall[1]=new Mauer(3,4,true);
        wall[2]=new Mauer(3,5,true);
        wall[3]=new Mauer(3,6,true);
        wall[4]=new Mauer(3,7,true);
        wall[5]=new Mauer(3,8,true);
        wall[6]=new Mauer(3,3,false);
        wall[7]=new Mauer(4,3,false);
        wall[8]=new Mauer(5,0,true);
        wall[9]=new Mauer(5,1,true);
        wall[10]=new Mauer(6,0,true);
        wall[11]=new Mauer(6,1,true);
        wall[12]=new Mauer(6,2,true);
        wall[13]=new Mauer(6,3,true);
    }
// 3,3 3,9
    @Override
    public String walk(String walkCommandString) {
        walkCommandString= walkCommandString.substring(1,walkCommandString.length()-1);
        String[] com = walkCommandString.split(",");
        boolean pablo = false;
        for (int i=0; Integer.parseInt(com[1])>i; i++) {
            switch (com[0]){
                case "no":
                    for (Mauer ww: wall) {
                        if (x==ww.x && y+1== ww.y && !ww.isVertical) {
                            pablo=true;
                        }
                    }
                    if (!pablo) {
                        y++;
                    }

                    break;
                case "ea":
                    for (Mauer ww: wall) {
                        if (y==ww.y && x+1== ww.x && ww.isVertical) {
                            pablo=true;
                        }
                    }
                    if (!pablo) {
                        x++;
                    }

                    break;
                    case "su":
                case "so":
                    for (Mauer ww: wall) {
                        if (y==0 || x==ww.x && y== ww.y && !ww.isVertical) {
                        pablo=true;
                    }
                }
                    if (!pablo) {
                        y--;
                    }

                    break;


                case "we":
                    for (Mauer ww: wall) {
                        if (x==ww.x && y== ww.y && ww.isVertical) {
                            pablo=true;
                        }
                    }
                    if (!pablo) {
                        x--;
                    }

                    break;
            }
        }
        System.out.println(com[0]+"("+x+","+y+")");

        return "("+x+","+y+")";

    }
}
