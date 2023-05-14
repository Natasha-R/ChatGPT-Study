package thkoeln.st.st2praktikum.exercise;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise0 implements GoAble {

    Map map = new Map(new Vector2(0,2),
            new Vector2(12,9),
            new Barrier(new Vector2(3,0), new Vector2(3,3)),
            new Barrier(new Vector2(5,0), new Vector2(5,4)),
            new Barrier(new Vector2(4,5), new Vector2(7,5)),
            new Barrier(new Vector2(7,5), new Vector2(7,9))
            );

    public Exercise0(){

    }
    @Override
    public String go(String goCommandString) {
        Vector2 dir = decypher(goCommandString);
        Vector2 newPos = map.getPlayerPosition().add(dir);
        for(Barrier b: map.getBarriers()){
            if(b.checkCross(map.getPlayerPosition(),dir)){
                Vector2 possibleNewPos = b.limit(map.getPlayerPosition());
                if(possibleNewPos.clone().add(map.getPlayerPosition().multiply(-1)).length() < newPos.clone().add(map.getPlayerPosition().multiply(-1)).length()){
                    newPos = possibleNewPos;
                }
            }
        }
        if(newPos.x >= map.getDimensions().x){
            newPos.x = map.getDimensions().x-1;
        }
        else if(newPos.x < 0){
            newPos.x = 0;
        }
        if(newPos.y >= map.getDimensions().y){
            newPos.y = map.getDimensions().y-1;
        }
        else if(newPos.y < 0){
            newPos.y = 0;
        }
        map.setPlayerPosition(newPos);
        return map.getPlayerPosition().toString();
    }

    public Vector2 decypher(String s){
        Vector2 dir = new Vector2(0,0);
        Pattern p = Pattern.compile("\\[(so|we|no|ea),\\d+]");
        Matcher m = p.matcher(s);
        if(m.matches()){
            p = Pattern.compile("(so|no|ea|we)");
            Matcher a = p.matcher(s);
            a.find();
            switch(a.group()){
                case "no":
                    dir.y += 1;
                    break;
                case "ea":
                    dir.x += 1;
                    break;
                case "so":
                    dir.y -= 1;
                    break;
                case "we":
                    dir.x -= 1;
                    break;
            }

            p = Pattern.compile("\\d+");
            a = p.matcher(s);
            a.find();
            dir.multiply(Integer.parseInt(a.group()));
        }
        return dir;
    }
}
