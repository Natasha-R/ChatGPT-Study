package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {
    int x = 4;
    int y = 0;
    String initialPosition = "(4,0)";



    @Override
    public String moveTo(String moveCommandString) {
        String movement = moveCommandString.substring(1,moveCommandString.length()-1);
        String[] moving = movement.split(",");
        String directionMove = moving[0];
        int stepToMove= Integer.parseInt(moving[1]);

        switch (directionMove){
            case "no":
                if(x==0 || x==4 || x>= 7) {
                    if((y+stepToMove)>7){
                        y = 7;
                    }

                    else y+=stepToMove;
                }

                if(x >=1 && x <= 3 || x>= 5 && x<=6 || y>=0 && y<=5){
                    if((y+stepToMove)>5){
                        y=5;
                    }

                    else y+=stepToMove;
                }

                if((y==6|| y==7) && x>=1 && x<=3 && (x==5|| x==6)){
                    if((y+stepToMove) >7){
                        y = 7;
                    }

                    else y+=stepToMove;
                }
                System.out.println("x=" +x+ "y=" +y);
                break;

            case "so":
                if(x==0 || x==4 || x >= 7 && x<=10) {
                    if((y-stepToMove)<0){
                        y = 0;
                    }

                    else y-=stepToMove;
                }

                if(x >=1 && x <= 3 || x>= 5 && x<=6 || y>=0 && y<=5){
                    if((y-stepToMove)<0){
                        y= 0;
                    }

                    else y-=stepToMove;
                }

                if((y==6|| y==7) && x>=1 && x<=3 || (x==5|| x==6)){
                    if((y-stepToMove) <6){
                        y = 6;
                    }

                    else y-=stepToMove;
                }
                System.out.println("x=" +x+ "y=" +y);
                break;
            case "ea":
                if(y>=0 && y<=5 && x == 0) {
                    x = 0;
                }

                if((y==6 || y==7) && x>=0 && x<= 10){
                    if((x+stepToMove) > 10){
                        x= 10;
                    }

                    else x+= stepToMove;
                }

                if(x>=7 && x<=10 && y>= 1 && y<= 5){
                    if((x+stepToMove) > 10){
                        x = 10;
                    }

                    else x+= stepToMove;
                }

                if(y== 0 && x >= 1 && x<=10){
                    if((x+stepToMove) > 10){
                        x = 10;
                    }

                    else x+= stepToMove;
                }

                if(y>=1 && y <= 5 && x >= 1 && x <= 6){
                    if((x+stepToMove) > 6){
                        x = 6;
                    }

                    else x+= stepToMove;
                }
                System.out.println("x=" +x+ "y=" +y);
                break;
            case "we":
                if(y>=0 && y<=5 && x== 0) {
                    x = 0;
                }

                if((y==6 || y==7) && x>=0 && x<= 10){
                    if((x-stepToMove) < 0){
                        x= 0;
                    }

                    else x-= stepToMove;
                }

                if(x>=7 && x<=10 && y>= 1 && y<= 5){
                    if((x-stepToMove) < 7){
                        x = 7;
                    }

                    else x-= stepToMove;
                }

                if(y== 0 && x >= 1 && x<=10){
                if((x-stepToMove) < 1){
                    x = 1;
                }

                else x-= stepToMove;
            }

            if(y>=1 && y <= 5 && x >= 1 && x <= 6){
                if((x-stepToMove) < 1){
                    x = 1;
                }

                else x-= stepToMove;
            }
            System.out.println("x=" +x+ "y=" +y);
            break;

        }
        return "(" + this.x +","+ this.y +")";
    }
}
