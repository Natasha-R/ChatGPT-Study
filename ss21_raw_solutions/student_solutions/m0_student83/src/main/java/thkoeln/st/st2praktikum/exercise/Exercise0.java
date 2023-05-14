package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;

public class Exercise0 implements Walkable {
    int x=11;
    int y=7;
    @Override
    public String walkTo(String walkCommandString) {

        String movement=walkCommandString.substring(1,walkCommandString.length()-1);
        String[] walking=movement.split(",");
        String direction=walking[0];
        int steps= Integer.parseInt(walking[1]);

        switch (direction){
            case "no":
                //1)
                if (0<=x && x<=4 && 0<=y && y<=7){
                    if ( (y+steps)>7)
                        y=7;
                    else
                        y+=steps;
                }
                //2)
                if (x==5 && 0<=y && y<=4){
                    if ((y+steps)>4)
                        y=4;
                    else
                        y+=steps;
                }
                //3)
                if (x==5 && y==5){
                    y=5;
                }
                //4)
                if (6<=x && x<=11 && 0<=y && y<=5){
                    if ((y+steps)>5)
                        y=5;
                    else
                        y+=steps;
                }
                //5)
                if (x>=5 &&x<=11 && y>=6 &&y<=7){
                    if ((y+steps)>7)
                        y=7;
                    else
                        y+=steps;
                }break;

            case "so":

                if (0<=x && x<=4 && 0<=y && y<=7){
                    if ( (y-steps)<0)
                        y=0;
                    else
                        y-=steps;
                }

                if (x==5 && 0<=y && y<=4){
                    if ((y-steps)<0)
                        y=0;
                    else
                        y-=steps;
                }

                if (x==5 && y==5){
                    y=5;
                }

                if (6<=x && x<=11 && 0<=y && y<=5){
                    if ((y-steps)<0)
                        y=0;
                    else
                        y-=steps;
                }
                //5)
                if (x>=5 &&x<=11 && y>=6 &&y<=7){
                    if ((y-steps)<6)
                        y=6;
                    else
                        y-=steps;
                }break;
            case "ea":

                if (0<=x && x<=11 && (y==6 || y==7 ||y==0 || y==1)){
                    if ( (x+steps)>11)
                        x=11;
                    else
                        x+=steps;
                }

                if (y==5 && 0<=x && x<=4){
                    if ((x+steps)>4)
                        x=4;
                    else
                        x+=steps;
                }
                if ((y==4||y==3||y==2) && 0<=x && x<=5){
                    if ((x+steps)>5)
                        x=5;
                    else
                        x+=steps;
                }
                if (5<=x&&x>=11 && y==5){
                    if ((x+steps)>11)
                        x=11;
                    else
                        x+=steps;
                }
                if (6<=x&&x>=11 && 0<=y&&y<=4){
                    if ((x+steps)>11)
                        x=11;
                    else
                        x+=steps;
                }
                break;
            case "we":

                if (0<=x && x<=11 && (y==6 || y==7 ||y==0 || y==1)){
                    if ( (x-steps)<0)
                        x=0;
                    else
                        x-=steps;
                }
                //2)
                if (y==5 && 0<=x && x<=4){
                    if ((x-steps)<0)
                        x=0;
                    else
                        x-=steps;
                }
                if ((y==4||y==3||y==2) && 0<=x && x<=5){
                    if ((x-steps)<0)
                        x=0;
                    else
                        x-=steps;
                }
                if (5<=x&&x>=11 && y==5){
                    if ((x-steps)<5)
                        x=5;
                    else
                        x-=steps;
                }
                if (6<=x&&x>=11 && 0<=y&&y<=4){
                    if ((x-steps)<6)
                        x=6;
                    else
                        x-=steps;
                }
                break;
        }
        return "(" + this.x+","+ this.y+ ")" ;
    }
}