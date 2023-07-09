package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Wall {

    private Point start;
    private Point end;

    public Wall(Point pos1, Point pos2) {
        orderWall(pos1, pos2);
    }

    public Wall(){}
    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        checkSyntax(wallString);
    }

    private void checkSyntax(String wallString){
        if(wallString.matches("\\({1}\\d+\\,\\d+\\){1}\\-\\({1}\\d+\\,\\d+\\){1}")){
            String[] parts = wallString.split("-");
            Point first = new Point(parts[0]);
            Point second = new Point(parts[1]);
            orderWall(first,second);
        }else{
            throw new RuntimeException("Wrong Syntax");
        }
    }

    private void orderWall(Point first, Point second){
        if(first.getX() == second.getX() || first.getY() == second.getY()){
            if(first.getX() < second.getX() || first.getY() < second.getY()){
                this.start = first;
                this.end = second;
            }else{
                this.start = second;
                this.end = first;
            }
        }else{
            throw new RuntimeException("Walls should be vertical or horizontal");
        }
    }
}

