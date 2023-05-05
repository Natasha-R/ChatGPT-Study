package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@Embeddable
public class Wall {

    @Embedded
    private Vector2D start;
    @Embedded
    private Vector2D end;


    public Wall(Vector2D pos1, Vector2D pos2) {
        this.start = pos1;
        this.end = pos2;
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString){
        if(this.isValidStringWall(wallString)){
            var subString = wallString.split("-");
            this.start = new Vector2D(subString[0]);
            this.end = new Vector2D(subString[1]);
        } else {
            throw new UnsupportedOperationException("Worse Wall string format! \n Please make sure your coordinate has this form: (x1,y1)-(x2,y2)! ");
        }

    };

    @Override
    public String toString() {
        return start.toString() + "-" + end.toString();
    }

    public Boolean isValidStringWall(String stringWall){
        Pattern pattern = Pattern.compile("\\(\\d+,\\d+\\)-\\(\\d+,\\d+\\)");
        Matcher matcher = pattern.matcher(stringWall);
        return matcher.matches();
    }
}
