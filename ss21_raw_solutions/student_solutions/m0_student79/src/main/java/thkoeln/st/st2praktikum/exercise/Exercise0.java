package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {
    int x= 3;
    int y= 0;

    @Override
    public String moveTo(String moveCommandString) {
        // moveCommandString= [we,3]
        String move= moveCommandString.substring(1,moveCommandString.length()-1);
        String[] moving= move.split(",");
        String richtung= moving[0];
        int steps= Integer.parseInt(moving[1]);



        if ("no".equals(richtung)) {
            if ((x == 0 || x==8 ||x==9 ||x==10 || x == 11)){
                if ((y + steps) > 7)
                    y = 7;
                else
                    y += steps;
            }
            if (( x== 1 || x==2 || x == 3|| x == 7) && 0 <= y && y <= 3){
                if ((y + steps) > 3)
                    y = 3;
                else
                    y += steps;
            }
            if ((x==4 || x==5 ||x == 6) && 0<=y && y<=2){
                if ((y + steps) > 2)
                    y = 2;
                else
                    y += steps;
            }

            if ((x==4 || x==5 ||x == 6) && y == 3){
                y = 3;
            }

            if (1 <= x && x <= 7 && y >= 4){
                if ((y + steps) > 7)
                    y = 7;
                else
                    y += steps;}

                System.out.println("x ="+x + "y="+y);
        } else if ("so".equals(richtung)) {
            if ((x == 0 || x==8 ||x==9 ||x==10 || x == 11) && y >= 0){
                if ((y - steps) < 0)
                    y = 0;
                else
                    y -= steps;}

            if (( x== 1 || x==2 || x == 3|| x == 7) && 0 <= y && y <= 3){
                if ((y - steps) < 0)
                    y = 0;
                else
                    y -= steps;}
            if (4 <= x && x <= 6 && 0 <= y && y <= 2){
                if ((y - steps) < 0)
                    y = 0;
                else
                    y -= steps;}

            if ((x==4 || x==5 ||x == 6) && y == 3){
                y = 3;}
            if (1 <= x && x <= 7 && y >= 4){
                if ((y - steps) < 4)
                    y = 4;
                else
                    y -= steps;}
            System.out.println("x ="+x + "y="+y);
        } else if ("ea".equals(richtung)) {
            if (0 <= x && 3 <= y){
                if ((x + steps) > 11)
                    x = 11;
                else
                    x += steps;}
            if (0 <= x && x <= 2 && 0 <= y && y <= 2){
                if ((x + steps) > 2)
                    x = 2;
                else
                    x += steps;}
            if (3 <= x && x <= 6 && (y == 0 || y == 1)){
                if ((x + steps) > 6)
                    x = 6;
                else
                    x += steps;}
            if (x >= 7 && (y == 0 || y == 1)){
                if ((x + steps) > 11)
                    x = 11;
                else
                    x += steps;}
            if (x >= 3 && y == 2){
                if ((x + steps) > 11)
                    x = 11;
                else
                    x += steps;}
            System.out.println("x ="+x + "y="+y);
        } else if ("we".equals(richtung)) {
            if (0 <= x && 3 <= y){
                if ((x - steps) < 0)
                    x = 0;
                else
                    x -= steps;}
            if (0 <= x && x <= 2 && 0 <= y && y <= 2){
                if ((x - steps) < 0)
                    x = 0;
                else
                    x -= steps;}

            if (3 <= x && x <= 6 && (y == 0 || y == 1)){
                if ((x - steps) > 3)
                    x = 3;
                else
                    x -= steps;}
            if (x >= 7 && (y == 0 || y == 1)){
                if ((x - steps) < 7)
                    x = 7;
                else
                    x -= steps;}

            if (x >= 3 && y == 2){
                if ((x - steps) < 3)
                    x = 3;
                else
                    x -= steps;}
            System.out.println("x ="+x + "y="+y);
        }

    return "("+ this.x +","+ this.y+")";

    }
}
