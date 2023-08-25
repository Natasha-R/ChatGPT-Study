package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class SpaceShipDeck {

    UUID mapID;
    int height;
    int width;
    Square[][] field;

    public SpaceShipDeck(UUID id, int height, int width) {

        this.mapID = id;
        this.height = height;
        this.width = width;

        Square[][] field = new Square[height][width];

        for (int countHeight = 0; countHeight < height; countHeight++) {
            for (int countWidth = 0; countWidth < width; countWidth++) {
                Square square = new Square(id,false,false,false,false);
                field[countHeight][countWidth]=square;
            }
        }
        this.field=field;
    }

    public void createBorder(){

        for(int borderLeft =0; borderLeft<height;borderLeft++){
            field[borderLeft][0].wallLeft=true;
        }

        for(int borderRight=0;borderRight<height;borderRight++){
            field[borderRight][height-1].wallRight=true;

        }

        for(int borderUp=0;borderUp<width;borderUp++){
            field[0][borderUp].wallUp=true;
        }

        for(int borderDown=0;borderDown<width;borderDown++){
            field[width-1][borderDown].wallDown=true;
        }
    }

    public void createWall(String command){

        int a = Character.getNumericValue(command.charAt(1));
        int b = Character.getNumericValue(command.charAt(3));
        int c = Character.getNumericValue(command.charAt(7));
        int d = Character.getNumericValue(command.charAt(9));


        if(b==d){     // (0,3 / 4,3) = the Wall goes from left to right)
            for(int i=0;i<c;i++){
                field[d][i].wallUp=true;
                field[d-1][i].wallDown=true;
            }
        }else if (a==c){
            for(int i=0;i<d;i++){    //  (2,4 / 2,2) = the wall goes from down to up
                field[i][c].wallLeft=true;
                field[i][c-1].wallRight=true;
            }
        }
    }
}
