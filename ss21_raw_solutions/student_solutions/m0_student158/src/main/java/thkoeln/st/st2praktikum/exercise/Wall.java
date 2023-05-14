package thkoeln.st.st2praktikum.exercise;

public class Wall {

    int startx;

    int starty;

    int endx;

    int endy;

    private boolean direction;

    public Wall(int startx, int starty, int endx, int endy) {
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
        direction = startx == endx;
    }

    public boolean checkBlock(int posx, int posy, int stepx, int stepy){
        if (direction && stepy != 0){
            return false;
        } else if (!direction && stepx != 0){
            return false;
        }
        if (stepx!=0){
            return checkx(posx, stepx, posy);
        } else {
            return checky(posy, stepy, posx);
        }
    }

    private boolean checkx(int posx, int step, int posy){
        int newpos = posx+step;
        for (int i = startx; i<=endx;i++){
            for(int j = starty; j<=endy; j++){
                if(step>0) {
                    if(posy<starty){
                        if (newpos == i && posy == j) {
                            return true;
                        }
                    } else if (newpos == i && posy == j-1) {
                        return true;
                    }
                } else if(newpos == i-1 && posy == j - 1){
                    return true;
                }
            }
        }
        return false;
    }


    private boolean checky(int posy, int step, int posx){
        int newpos = posy+step;
        for (int i = starty; i<=endy;i++){
            for(int j= startx; j<= endx;j++){
                if(step>0) {
                    if (posx < startx){
                        if (newpos == i && posx == j) {
                            return true;
                        }

                    } else if (newpos == i && posx == j-1) {
                        return true;
                    }
                } else if (newpos == i-1 && posx == j - 1) {
                    return true;
                }
            }
        }
        return false;
    }

}
