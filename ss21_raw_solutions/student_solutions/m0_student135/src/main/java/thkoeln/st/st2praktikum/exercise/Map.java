package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;

public class Map {
    private Vector2 player;
    private Vector2 dimensions;
    private ArrayList<Barrier> barriers = new ArrayList<Barrier>();

    public Map(Vector2 player, Vector2 dimensions, Barrier... barriers){
        this.player = player;
        this.dimensions = dimensions;
        for(Barrier b: barriers){
            this.barriers.add(b);
        }
    }

    public Vector2 getPlayerPosition(){
        return player.clone();
    }

    public void setPlayerPosition(Vector2 pos){
        player.x = pos.x;
        player.y = pos.y;
    }

    public Vector2 getDimensions(){
        return dimensions.clone();
    }

    public ArrayList<Barrier> getBarriers(){
        return (ArrayList<Barrier>) barriers.clone();
    }

    public void addBarrier(Barrier b){
        barriers.add(b);
    }

    public void addBarrier(Vector2 start, Vector2 end) throws Exception {
        barriers.add(new Barrier(start, end));
    }


}
