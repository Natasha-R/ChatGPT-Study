package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;

import java.util.HashSet;

public class Exercise0 implements GoAble {

    public Exercise0(){
        createObstacles();
    }

    Position position = new Position(7,7);
    HashSet<Double> horizontalObstacles = new HashSet<>();
    HashSet<Double> verticalObstacles = new HashSet<>();

    @Override
    public String goTo(String goCommandString) {
        String[] input = goCommandString.replace("[","").replace("]","").split(",");
        String direction = input[0];
        int distance = Integer.parseInt(input[1]);

        move(direction, distance);

        return position.toString();
    }

    public void move(String direction, int distance) {
        while(distance>0) {
            if (direction.equals("no") && !(isObstacle(position.x, position.y+1, direction) || position.y==7))
                position.y++;
            else if (direction.equals("ea") && !(isObstacle(position.x+1, position.y, direction) || position.x==10))
                position.x++;
            else if (direction.equals("so") && !(isObstacle(position.x, position.y, direction) || position.y==0))
                position.y--;
            else if (direction.equals("we") && !(isObstacle(position.x, position.y, direction) || position.x==0))
                position.x--;
            else
                break;
            distance--;
        }
    }

    @AllArgsConstructor
    public class Position{
        public Integer x;
        public Integer y;

        @Override
        public String toString() {
            return "(" + x.toString() + "," + y.toString() + ")";
        }
    }

    public Boolean isObstacle(int x, int y, String direction) {
        Double pos = Double.valueOf(Integer.toString(x).concat(".").concat(Integer.toString(y)));
        if (direction.equals("no") || direction.equals("so"))
            return horizontalObstacles.contains(pos);
        else
            return verticalObstacles.contains(pos);
    }

    public void createObstacles(){
        horizontalObstacles.add(2.6);
        horizontalObstacles.add(3.6);
        horizontalObstacles.add(4.6);
        horizontalObstacles.add(5.6);
        horizontalObstacles.add(6.6);

        horizontalObstacles.add(2.1);
        horizontalObstacles.add(3.1);
        horizontalObstacles.add(4.1);
        horizontalObstacles.add(5.1);
        horizontalObstacles.add(6.1);
        horizontalObstacles.add(7.1);
        horizontalObstacles.add(8.1);
        horizontalObstacles.add(9.1);

        verticalObstacles.add(2.5);
        verticalObstacles.add(2.4);
        verticalObstacles.add(2.3);
        verticalObstacles.add(2.2);
        verticalObstacles.add(2.1);

        verticalObstacles.add(10.1);
        verticalObstacles.add(10.2);
        verticalObstacles.add(10.3);
        verticalObstacles.add(10.4);
        verticalObstacles.add(10.5);
        verticalObstacles.add(10.6);
        verticalObstacles.add(10.7);
        verticalObstacles.add(10.8);
    }

}
