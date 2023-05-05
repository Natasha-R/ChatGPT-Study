package thkoeln.st.st2praktikum.exercise;
public class Exercise0 implements Walkable {
    int x = 7;
    int y = 7;
    String initialPosition="(7,7)";

    String obstaclesAndWalls="(0,0)-(0,8),(11,0)-(11,8),(2,1)-(2,6),(10,1)-(10,8),(0,8)-(11,8),(0,0)-(11,0),(2,6)-(7,6),(2,1)-(10,1)";

    @Override
    public String walkTo(String walkCommandString) {
        // [no, 6]

        String movement=walkCommandString.substring(1,walkCommandString.length()-1);
        String[] walking=movement.split(",");
        String directionMove=walking[0];
        int stepToMove= Integer.parseInt(walking[1]);

        switch (directionMove){
            case "no":
                if (x==0||x==1 || x==10){
                    if ( (y+stepToMove)>7)
                        y=7;
                    else
                        y+=stepToMove;
                }
                if (2<=x && x<=9 && y==0){
                    y=0;
                }

                //top line
                if (2<=x && x<=6 && 1<=y && y<=5){
                    if ((y+stepToMove)>5)
                        y=5;
                    else
                        y+=stepToMove;
                }
                if (7<=x && x<=9 && 1<=y){
                    if ((y+stepToMove)>7)
                        y=7;
                    else
                        y+=stepToMove;
                }

                if (2<=x && x<=6 && (y==6 || y==7)){
                    if ((y+stepToMove)>7)
                        y=7;
                    else
                        y+=stepToMove;
                }break;

            case "so":
                if(x==0 || x==1 || x==10){
                    if ((y-stepToMove)<0)
                        y=0;
                    else
                        y-=stepToMove;
                }
                //Bottom line
                if(2<=x && x<=9 && y==0){
                        y=0;
                }

                if(2<=x && x<=6 && (1<=y && y<=5)){
                    if ((y-stepToMove)<1)
                        y=1;
                    else
                        y-= stepToMove;
                 }

                //top
                if(7<=x && x<=9 && 1<=y){
                    if ((y-stepToMove)<1)
                        y=1;
                    else
                        y-= stepToMove;
                }
                if(2<=x && x<=6 && 6<=y){
                    if ((y-stepToMove)<6)
                        y=6;
                    else
                        y-= stepToMove;
                }break;

            case "ea":
                if ((x==0 || x==1) && 1<=y && y<=5){
                    if ((x+stepToMove)>1)
                        x=1;
                    else
                        x+=stepToMove;
                }

                //secondline
                if (2<=x && x<=9 && 1<=y && y<=5){
                    if ((x+stepToMove)>9)
                        x=9;
                    else
                        x+=stepToMove;
                }
                if (0<=x && x<=9 && 6<=y){
                    if ((x+stepToMove)>9)
                        x=9;
                    else
                        x+=stepToMove;
                }
                if (x==10 ){
                    x=10;
                }

                if (y==0){
                    if ((x+stepToMove)>10)
                        x=10;
                    else
                        x+=stepToMove;
                }break;

            case "we":
                //2ndWvertical
                if (9<x && 1<=y){
                    x=10;
                }
                if (2<=x && x<= 9 && 1<=y && y<=5){
                    if((x-stepToMove)<2)
                        x=2;
                    else
                        x-=stepToMove;
                }
                if (x<= 9 && 6<=y ){
                    if((x-stepToMove)<0)
                        x=0;
                    else
                        x-=stepToMove;
                }

                if ((x== 0 || x==1 )&& 1<=y && y<=5){
                    if((x-stepToMove)<0)
                        x=0;
                    else
                        x-=stepToMove;
                }

                if (y==0 ){
                    if((x-stepToMove)<0)
                        x=0;
                    else
                        x-=stepToMove;
                }break;
        }

        return "(" + this.x+","+ this.y+ ")" ;
    }
}
