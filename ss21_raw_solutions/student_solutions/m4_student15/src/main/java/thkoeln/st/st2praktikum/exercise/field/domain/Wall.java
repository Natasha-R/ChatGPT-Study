package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Embeddable;


@Getter
@Embeddable
public class Wall {

    private Point start;
    private Point end;

    public Wall(){}


    public Wall(Point start, Point end) {

        orderWall2(start,end);
        
    }
    
    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    private static Wall checkSyntax(String wallString){
        if(wallString.matches("\\({1}\\d+\\,\\d+\\){1}\\-\\({1}\\d+\\,\\d+\\){1}")){
            String[] parts = wallString.split("-");
            Point first = Point.fromString(parts[0]);
            Point second = Point.fromString(parts[1]);
            return orderWall(first,second);
        }else{
            throw new RuntimeException("Wrong Syntax");
        }
    }

    private void orderWall2(Point first, Point second){
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

    private static Wall orderWall(Point first, Point second){
        if(first.getX() == second.getX() || first.getY() == second.getY()){
            if(first.getX() < second.getX() || first.getY() < second.getY()){
                return new Wall(first, second);
            }else{
                return new Wall(second, first);
            }
        }else{
            throw new RuntimeException("Walls should be vertical or horizontal");
        }
    }


    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public static Wall fromString(String wallString) {
        return checkSyntax(wallString);
    }
    
}
