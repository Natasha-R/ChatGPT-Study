package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {

    int x=1,y=7;
    @Override
    public String walkTo(String walkCommandString) {

        int steps = Integer.parseInt(String.valueOf(walkCommandString.charAt(4)));

        if(walkCommandString.charAt(1)=='n'){
            if( (x < 3 && y <9) || ( x >= 5 && y < 9)){
                if( y+steps > 8) y=8;
                else y += steps;
            }
            else if((x < 5 && x >= 3) && y < 3){
                if(y+steps > 2) y=2;
                else y += steps;
            }
            else {
                if(y+steps > 8) y=8;
                else y += steps;
            }
        }

        if(walkCommandString.charAt(1)=='s'){
            if( (x < 3 && y >= 0) || (x >= 5 && y >= 0)){
                if( y-steps < 0) y=0;
                else y -= steps;
            }
            else if((x < 5 && x >= 3) ){
                if( y >= 3) {
                    if (y - steps < 3) y = 3;
                    else y -= steps;
                }
                else{
                    if (y - steps < 0) y = 0;
                    else y -= steps;

                }

            }
        }

        if(walkCommandString.charAt(1)=='e'){
            if( (x>=6 && x<=11) && (y>=0 && y<=3)){
                if( x+steps > 11) x=11;
                else x+=steps;
            }

            else if( (x>=3 && x<=11) && (y>=4 && y<=8)){
                if( x+steps > 11) x=11;
                else x+=steps;
            }

            else if(x==5 && (y>=0 && y<=1)){
               x+=steps;
               x=5;
            }

            else if((x>=3 && x<=5) && y==3 ){
                if( x+steps > 5) x=5;
                else x+=steps;
            }

            else if((x>=0 && x<=4) && (y>=0 && y<=1)){
                if( x+steps > 4) x=4;
                else x+=steps;
            }
            else if((x>=0 && x<=5) && y==2){
                if( x+steps > 5) x=5;
                else x+=steps;
            }

            else if((x>=0 && x<=2) && (y>=3 && y<=8)){
                if( x+steps > 2) x=2;
                else x+=steps;
            }
        }

        if(walkCommandString.charAt(1)=='w'){

            if( (x>=6 && x<=11) && (y>=0 && y<=3)){
                if( x-steps < 6) x=6;
                else x-=steps;
            }

            else if( (x>=3 && x<=11) && (y>=4 && y<=8)){
                if( x-steps < 3) x=3;
                else x-=steps;
            }

            else if(x==5 && (y>=0 && y<=1)){
                //x-=steps;
                x=5;
            }

            else if((x>=3 && x<=5) && y==3 ){
                if( x-steps < 3) x=3;
                else x-=steps;
            }

            else if((x>=0 && x<=4) && (y>=0 && y<=1)){
                if( x-steps < 0) x=0;
                else x-=steps;
            }
            else if((x>=0 && x<=5) && y==2){
                if( x-steps < 0) x=0;
                else x-=steps;
            }

            else if((x>=0 && x<=2) && (y>=3 && y<=8)){
                if( x-steps < 0) x=0;
                else x-=steps;
            }
        }


        return "("+x+","+y+")";
    }

}

