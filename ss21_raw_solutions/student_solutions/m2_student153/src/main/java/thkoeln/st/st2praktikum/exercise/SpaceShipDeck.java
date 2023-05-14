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

    public void createWall(Wall wall){

        int a = wall.getStart().getX();
        int b = wall.getStart().getY();
        int c = wall.getEnd().getX();
        int d = wall.getEnd().getY();

        try {


            if (b == d) {     // (0,3 / 4,3) = the Wall goes from left to right)
                for (int i = 0; i < c; i++) {
                    field[d][i].wallUp = true;
                    field[d - 1][i].wallDown = true;
                }
            } else if (a == c) {
                for (int i = 0; i < d; i++) {    //  (2,4 / 2,2) = the wall goes from down to up
                    field[i][c].wallLeft = true;
                    field[i][c - 1].wallRight = true;
                }
            }
        }
      catch (Exception e){
            System.out.println("The wall goes through the bounds of the field ");
            }
    }
}
