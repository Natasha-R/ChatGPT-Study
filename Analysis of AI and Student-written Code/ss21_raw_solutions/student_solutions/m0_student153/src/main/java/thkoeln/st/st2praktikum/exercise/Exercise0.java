package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {

    int  starty=7;
    int startx=11;


    @Override
    public String walk(String walkCommandString) {





        String direction = walkCommandString.substring(1, 3);
        String substr2 = walkCommandString.substring(4, 5);
        int steps = Integer.parseInt(substr2);


        //Array erstellen

        int [][] ship = new int [8][12];

        //Array befüllen

        for(int y=0;y<8;y++){
            for (int x=0;x<12;x++){
                ship[y][x]=0;
            }
        }

        //Array spezifisch befüllen, der Rand des Shiffes

        for (int x=1;x<11;x++){
            ship[0][x]=2;
        }
        for (int x=1;x<11;x++){
            ship[7][x]=4;
        }
        for (int y=1;y<7;y++){
            ship[y][0]=1;
        }
        for (int y=1;y<7;y++){
            ship[y][11]=3;
        }
        ship[0][0]=5;
        ship[0][11]=6;
        ship[7][0]=8;
        ship[7][11]=7;

        // Wand ins Array füllen

        ship[2][5]=3;
        ship[3][5]=3;
        ship[4][5]=7;
        ship[5][4]=3;
        ship[5][5]=12;
        ship[2][6]=1;
        ship[3][6]=1;
        ship[4][6]=1;
        ship[5][11]=7;
        ship[6][11]=6;

        for(int x=6;x<11;x++){
            ship[5][x]=4;
        }
        for(int x=5;x<11;x++){
            ship[6][x]=2;
        }

        //


       //Move step by Step

    switch (direction){
        case "we":

        for ( int s=0;s<steps;s++){
           if (ship[starty][startx]== 1 || ship[starty][startx]==5 ||ship[starty][startx]==8||ship[starty][startx]==9||ship[starty][startx]==11||ship[starty][startx]==12){
               break;
           }
            startx=startx-1;

        }
        break;
        case "ea":

            for ( int s=0;s<steps;s++){
                if(ship[starty][startx]==3||ship[starty][startx]==6||ship[starty][startx]==7||ship[starty][startx]==9||ship[starty][startx]==10||ship[starty][startx]==11 ){
                    break;
                }
                startx=startx+1;

            }
            break;
        case "no":

            for ( int s=0;s<steps;s++){
                if(ship[starty][startx]==4||ship[starty][startx]==7||ship[starty][startx]==8||ship[starty][startx]==10||ship[starty][startx]==11||ship[starty][startx]==12 ){

                    break;
                }
                starty=starty+1;

            }
            break;
        case "so":

            for ( int s=0;s<steps;s++){
                if(ship[starty][startx]==2||ship[starty][startx]==5||ship[starty][startx]==6||ship[starty][startx]==9||ship[starty][startx]==10||ship[starty][startx]==12){
                    break;
                }
                starty=starty-1;

            }
            break;







    }












        return ("("+startx+","+starty+")");
    }
}
