package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Wall {
    private Coordinate from;
    private Coordinate to;
    private Set<Coordinate> wholeWall = new HashSet<>();

    public Wall(Coordinate from, Coordinate to) {
        this.from = from;
        this.to = to;
        createWall();
    }

    private void createWall(){
        if(from.getX() > to.getX()){
            for(int i=0; i<=from.getX()-to.getX(); i++){
                wholeWall.add(new Coordinate(to.getX()+i, from.getY()));
            }
        }else
            if(from.getX() < to.getX()){
                for(int i=0; i<=to.getX()-from.getX(); i++){
                    wholeWall.add(new Coordinate(from.getX()+i, from.getY()));
                }
            }else
                if(from.getY() >= to.getY()){
                    for(int i=0; i<from.getY()-to.getY(); i++){
                        wholeWall.add(new Coordinate(to.getX(), to.getY()+i));
                    }
                }else
                    if(from.getY() < to.getY()) {
                        for (int i = 0; i <= to.getY() - from.getY(); i++) {
                            wholeWall.add(new Coordinate(from.getX(), from.getY()+i));
                        }
                    }
    }

    @Override
    public String toString(){
        return from.toString().concat("-".concat(to.toString()));
    }
}
