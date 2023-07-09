package thkoeln.st.st2praktikum.exercise;


public class Exercise0 implements GoAble {
    // 경계선 까지는 허용이나, 경계선이나 map 자체를 넘어 가는 게 불가능.
    // 대각선으로 움직이지 않는다. 왼 오 위 아래 로만 움직인다.

    int x = 7;
    int y = 7;

    String position;

    @Override
    public String go(String goCommandString) {

        if(goCommandString.contains("no")){
            String intStr =  goCommandString.replaceAll("[^0-9]","");
            int zahl = Integer.parseInt(intStr);

            if( y == 0) {
                y = y + zahl;
                if( y > 8) {
                    y = 7;
                }

                if( x >= 2 && x <= 9){
                    y = 0;
                }
            }
            else if( y >= 1 && y <= 5){
                y = y + zahl;
                if( y > 8) {
                    y = 7;
                }

                if( x >= 2 && x <= 6){
                    if( y > 6){
                        y = 6;
                    }
                }
            }

          /*  if( y > 8) {
                y = 7;
            }
*/

        }
        else if(goCommandString.contains("so")){
            String intStr =  goCommandString.replaceAll("[^0-9]","");
            int zahl = Integer.parseInt(intStr);

            if( y >= 6 ){
                y = y - zahl;
                if( x >= 2 && x <= 6){
                    if( y < 6){
                        y = 6;
                    }
                }
                if( x >= 7 && x <= 9){
                    if( y < 0) {
                        y = 1;
                    }
                }
                if( y < 0) {
                    y = 0;
                }
            }

            else if( y >= 1 && y <= 5){
                y = y - zahl;
                if( y < 0) {
                    y = 0;
                }
                if( x >= 2 && x <= 9){
                    if( y <= 0){
                        y = 1;
                    }
                }
            }
            //y = y - zahl;
            /*if( y < 0) {
                y = 0;
            }
*/
        }
        else if(goCommandString.contains("we")){
            String intStr =  goCommandString.replaceAll("[^0-9]","");
            int zahl = Integer.parseInt(intStr);


            if( x <= 1){
                x = x - zahl;
                if( x < 0){
                    x = 0;
                }
            }
            else if( x >= 2 && x <= 9){
                x = x - zahl;
                if( y >= 1 && y <= 5){
                    if(x < 2) {
                        x = 2;
                    }
                }
            }
            else{
                x = 10;
            }

          /*  x = x - zahl;

            if( x < 0 ){
                x = 0;
            }
*/


        }
        else if(goCommandString.contains("ea")){
            String intStr =  goCommandString.replaceAll("[^0-9]","");
            int zahl = Integer.parseInt(intStr);

            if( x < 2) {
                x = x + zahl;
                if( y >= 1 && y <= 5) {
                    if(x > 2){
                        x = 1;
                    }
                }
            }
            else if( x >= 2 && x <= 9){
                x = x + zahl;
                if( y >= 1 && y <= 7){
                    if( x > 10){
                        x = 9;
                    }
                }

            }
            else{
                x = 10;
            }

            //x = x + zahl;

         /*  if( y >= 1 && y <= 5) {
                if(x > 2){
                    x = 1;
                }
            }

            if( x > 10){
                x = 10;
            }
*/
        }

        // wall
    /*    if( 2 <= x && x <= 6) {
            if (y > 6) {
                y = 5;
            }
        }*/

        position = "(" +x+","+y+")";

        return position;
    }

}
