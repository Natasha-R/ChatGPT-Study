package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {

    int x = 1;
    int y = 7;

    @Override
    public String walkTo(String walkCommandString) {
        String s = walkCommandString;
        s = s.replaceAll("\\D+","");
        int n = Integer.parseInt(s);
        for (int i=0; i<n; i++) {
            if (walkCommandString.charAt(1)=='n') {
                if (x==3 || x==4 && y==2) {
                    i=n;
                } else if (y<8) {
                    y++;
                }
            } else if (walkCommandString.charAt(1)=='s') {
                if (x==3 || x==4 && y==3) {
                    i=n;
                } else if (y>0) {
                    y--;
                }
            } else if (walkCommandString.charAt(1)=='e') {
                if ((x==2 && y>=3 && y<=8) || (x==4 && y==0 || y==1) || (x==5 && y>=0 && y<=3)) {
                    i=n;
                } else if (x<11) {
                    x++;
                }
            } else if (walkCommandString.charAt(1)=='w') {
                if ((x==3 && y>=3 && y<=8) || (x==5 && y==0 || y==1) || (x==6 && y>=0 && y<=3)) {
                    i=n;
                } else if (x>0) {
                    x--;
                }
            }
        }
        return "(" + x + "," + y + ")";

        /*int x = 1;
        int y = 7;
        int steps = walkCommandString.charAt(4);
        if(walkCommandString.charAt(1)=='n') {
            y+=steps;
            if (y>8) {
                y=8;
            }
        } else if(walkCommandString.charAt(1)=='s') {
            y-=steps;
            if (y<0) {
                y=0;
            }
        } else if(walkCommandString.charAt(1)=='e') {
            x+=steps;
            if (x>11) {
                x=11;
            }
        } else if(walkCommandString.charAt(1)=='w') {
            x-=steps;
            if (x<0) {
                x=0;
            }
        }
        return "(" + x + "," + y + ")";*/
    }
}
