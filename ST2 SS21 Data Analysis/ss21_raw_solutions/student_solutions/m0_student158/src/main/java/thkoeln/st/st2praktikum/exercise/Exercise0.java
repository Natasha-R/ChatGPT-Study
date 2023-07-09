package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;

public class Exercise0 implements Moveable {

    private ArrayList<Wall> walls = new ArrayList<Wall>();

    private Integer posX = 0;

    private Integer posY = 2;

/*
no = x +
so = x -
ea = y +
we = y -
*/

    @Override
    public String moveTo(String input) {
        if(walls.isEmpty()){
            setWalls();
        }
        String direction = input.substring(1,3);
        int steps = Integer.parseInt(input.substring(4,6).replace("]", ""));

        switch (direction){
            case "ea":
                for (int i = 0;i<steps;i++){
                    boolean marker=true;
                    for(int j=0;j<walls.size();j++){
                        if(posX==11){
                            marker = false;
                            continue;
                        }
                        if(marker){
                            marker = !walls.get(j).checkBlock(posX, posY, 1,0);
                        }
                    }
                    if (marker){
                        posX++;
                    } else {
                        continue;
                    }
                }
                break;
            case "we":
                for (int i = 0;i<steps;i++){
                    boolean marker=true;
                    for(int j=0;j<walls.size();j++){
                        if(posX==0){
                            marker = false;
                            continue;
                        }
                        if(marker){
                            marker = !walls.get(j).checkBlock(posX, posY, -1,0);
                        }
                    }
                    if (marker){
                        posX--;
                    } else {
                        continue;
                    }
                }
                break;
            case "no":
                for (int i = 0;i<steps;i++){
                    boolean marker=true;
                    for(int j=0;j<walls.size();j++){
                        if (posY == 8){
                            marker = false;
                            continue;
                        }
                        if(marker){
                            marker = !walls.get(j).checkBlock(posX, posY, 0,1);
                        }
                    }
                    if (marker){
                        posY++;
                    } else {
                        continue;
                    }
                }
                break;
            case "so":
                for (int i = 0;i<steps;i++){
                    boolean marker=true;
                    for(int j=0;j<walls.size();j++){
                        if(posY==0){
                            marker = false;
                            continue;
                        }
                        if(marker){
                            marker = !walls.get(j).checkBlock(posX, posY, 0,-1);
                        }
                    }
                    if (marker){
                        posY--;
                    } else {
                        continue;
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("???");
        }
        String erg = "(" + posX+ "," + posY+")";
        return erg;
    }


    private void setWalls(){
        Wall one = new Wall(3,0,3,3);
        Wall two = new Wall(5,0,5,4);
        Wall three = new Wall(7,5,7,9);
        Wall four = new Wall(4,5,7,5);

        walls.add(one);
        walls.add(two);
        walls.add(three);
        walls.add(four);

        /*
        3,0-3,3
        5,0-5,4
        7,5-7-9
        4,5-7,5
        */
    }

}
