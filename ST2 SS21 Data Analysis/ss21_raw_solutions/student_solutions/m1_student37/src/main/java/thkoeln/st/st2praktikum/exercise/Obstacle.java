package thkoeln.st.st2praktikum.exercise;

public class Obstacle {
    private Point Startpoint;
    private Point Endpoint;
    private boolean vertical;
    // ich habe keine Ahnung was ich machen soll wenn jmd. eine Wand erzeugen will die auf dem selben point Startet und Endet
    public Obstacle(Point Cornerpoint1, Point Cornerpoint2){
        if(Cornerpoint1.getX()==Cornerpoint2.getX()){
            vertical=true;
            if(Cornerpoint1.getY()>Cornerpoint2.getY()){
                Startpoint=Cornerpoint2;
                Endpoint=Cornerpoint1;
            }else{
                Startpoint=Cornerpoint1;
                Endpoint=Cornerpoint2;
            }
        }else if(Cornerpoint1.getY()==Cornerpoint2.getY()){
            vertical=false;
            if(Cornerpoint1.getX()>Cornerpoint2.getX()){
                Startpoint=Cornerpoint2;
                Endpoint=Cornerpoint1;
            }else{
                Startpoint=Cornerpoint1;
                Endpoint=Cornerpoint2;
            }
        }else{
            throw new IllegalArgumentException();
        }
    }

    public boolean isvertical(){
        return vertical;
    }

    public boolean sameX(int x) {
        boolean result = false;
        if(vertical){
            if(x==Startpoint.getX()){
                result=true;
            }else{
                result=false;
            }
        }else{
            if(x>Startpoint.getX()&&x<Endpoint.getX()){
                result=true;
            }else{
                result=false;
            }
        }
        return result;
    }

    public boolean sameY(int y) {
        boolean result = false;
        if(!vertical){
            if(y==Startpoint.getY()){
                result=true;
            }else{
                result=false;
            }
        }else{
            if(y>Startpoint.getY()&&y<Endpoint.getY()){
                result=true;
            }else{
                result=false;
            }
        }
        return result;
    }
}