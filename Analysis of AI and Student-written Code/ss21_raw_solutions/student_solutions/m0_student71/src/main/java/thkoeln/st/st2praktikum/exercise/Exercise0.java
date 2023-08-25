package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.util.Pair;

import java.util.*;

public class Exercise0 implements GoAble {

    private enum rotation{
        North,
        west,
        south,
        east
    }

    private class obstacle{
        public int x1,x2;
        public int y1,y2;

        public obstacle(int x1,int y1,int x2,int y2){
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }
    }

    private class Map{
        public ArrayList<obstacle> obstacles = new ArrayList();
        
        public int sizeX = 11;
        public int sizeY = 8;
        
        public Map(){
            obstacles.add(new obstacle(4,1,4,6));
            obstacles.add(new obstacle(6,2,8,2));
            obstacles.add(new obstacle(6,5,8,5));
            obstacles.add(new obstacle(6,2,6,4));
        }
    }

    private int positionX = 8;
    private int positionY = 3;

    @Override
    public String go(String goCommandString) {

        if(goCommandString.toCharArray()[0] != '[' || goCommandString.toCharArray()[goCommandString.length()-1] != ']')
            return null;

        var rawString = goCommandString.substring(1,goCommandString.length()-1);

        var rawRotation = rawString.split(",")[0];
        var rawLength = rawString.split(",")[1];

        rotation tempRotation;
        Integer tempLength = Integer.parseInt(rawLength);
        Map map = new Map();

        
        switch(rawRotation) {
            case "no":
                tempRotation = rotation.North;
                break;
            case "ea":
                tempRotation = rotation.east;
                break;
            case "so":
                tempRotation = rotation.south;
                break;
            case "we":
                tempRotation = rotation.west;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + rawRotation);
        }

        switch (tempRotation) {
            case south:
                for (int i = 0; i < tempLength; i++) {
                    for (var obstacle: map.obstacles ) {

                        if(obstacle.x1 == obstacle.x2)
                            continue;

                        if(!(positionX >= obstacle.x1 && positionX <= obstacle.x2))
                            continue;

                        if(positionY == obstacle.y1)
                            return String.format("(%d,%d)",positionX,positionY);
                    }
                    positionY--;

                    if(positionY == 0)
                        return String.format("(%d,%d)",positionX,positionY);
                }
                break;
            case North:

                for (int i = 0; i < tempLength; i++) {
                    for (var obstacle: map.obstacles ) {

                        if(obstacle.x1 == obstacle.x2)
                            continue;

                        if(!(positionX >= obstacle.x1 && positionX <= obstacle.x2))
                            continue;

                        if(positionY +1 == obstacle.y1)
                            return String.format("(%d,%d)",positionX,positionY);
                    }
                    positionY++;

                    if(positionY == map.sizeY)
                        return String.format("(%d,%d)",positionX,positionY);
                }
                break;

            case west:

                for (int i = 0; i < tempLength; i++) {
                    for (var obstacle: map.obstacles ) {

                        if(obstacle.y1 == obstacle.y2)
                            continue;

                        if(!(positionY >= obstacle.y1 && positionY <= obstacle.y2))
                            continue;

                        if(positionX == obstacle.x1)
                            return String.format("(%d,%d)",positionX,positionY);
                    }
                    positionX--;

                    if(positionX == 0)
                        return String.format("(%d,%d)",positionX,positionY);
                }
                break;

            case east:

                for (int i = 0; i < tempLength; i++) {
                    for (var obstacle: map.obstacles ) {

                        if(obstacle.y1 == obstacle.y2)
                            continue;

                        if(!(positionY >= obstacle.y1 && positionY <= obstacle.y2))
                            continue;

                        if(positionX +1 == obstacle.x1)
                            return String.format("(%d,%d)",positionX,positionY);
                    }
                    positionX++;

                    if(positionX == map.sizeX)
                        return String.format("(%d,%d)",positionX,positionY);
                }
                break;
        }
        
        
        
        return String.format("(%d,%d)",positionX,positionY);
    }


}
