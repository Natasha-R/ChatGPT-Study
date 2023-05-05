package thkoeln.st.st2praktikum.exercise;

public class Barrier {
    private Vector2 start;
    private Vector2 end;
    private boolean isHorizontal;

    public Barrier(Vector2 _start, Vector2 _end) {
        if(_start.x == _end.x || _start.y == _end.y){
            start = _start;
            end = _end;
            isHorizontal = start.y == end.y;
        }
    }

    public boolean checkCross(Vector2 pos, Vector2 dir){
        if(isHorizontal && start.x <= pos.x && pos.x < end.x){
            if(pos.y < start.y && pos.add(dir).y >= start.y){
                return true;
            }
            else if(pos.y >= start.y && pos.add(dir).y <= start.y){
                return true;
            }
        }
        else if(!isHorizontal && start.y <= pos.y && pos.y < end.y){
            if(pos.x < start.x && pos.add(dir).x >= start.x){
                return true;
            }
            else if(pos.x >= start.x && pos.add(dir).x <= start.x){
                return true;
            }
        }
        return false;
    }

    public Vector2 limit(Vector2 pos){
        Vector2 res = pos.clone();
        if(isHorizontal){
            if(pos.y > start.y){
                res.y = start.y;
            }
            else{
                res.y = start.y-1;
            }
        }
        else{
            if(pos.x > start.x){
                res.x = start.x;
            }
            else{
                res.x = start.x-1;
            }
        }
        return res;
    }
}
