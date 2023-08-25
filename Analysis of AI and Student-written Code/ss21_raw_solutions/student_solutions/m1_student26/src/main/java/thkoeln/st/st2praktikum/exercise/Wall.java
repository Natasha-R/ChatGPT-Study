package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;


public class Wall extends AbstractEntity{

    @Getter @Setter
    private String wallstring;


    public Wall(String wallstring){
        this.wallstring = wallstring;

    }

}
