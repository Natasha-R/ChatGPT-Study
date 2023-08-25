package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {

    private final Cell[][] grid;

    private final int outerBorderx;
    private final int outerBordery;

    private int turobotx;
    private int turoboty;

    public Exercise0(){
        this.outerBorderx = 11;
        this.outerBordery = 8;
        this.grid = new Cell[outerBorderx][outerBordery];
        this.turobotx = 1;
        this.turoboty = 6;

        for(int i = 0; i < outerBorderx;i++){
            for (int j = 0; j < outerBordery;j++){
                this.grid[i][j] = new Cell();
            }
        }
        this.setBorder(0,6,1,6);
        this.setBorder(3,6,3,7);
        this.setBorder(1,5,8,5);
        this.setBorder(9,1,9,4);
    }

    private void setBorder(int startx, int starty, int endx, int endy){
        if((endx - startx) != 0){
            for( int i = startx; i<=endx;i++){
                this.grid[i][starty].setBorderSouth(true);
                this.grid[i][starty - 1].setBorderNorth(true);
            }
        }
        else if((endy - starty) != 0){
            for( int i = starty; i<=endy;i++){
                this.grid[startx][i].setBorderWest(true);
                this.grid[startx - 1][i].setBorderEast(true);
            }
        }
    }
    @Override
    public String go(String goCommandString) {
        String[] commandValue = goCommandString.split(",");
        String direction = commandValue[0].substring(1);
        int amount = Integer.parseInt(commandValue[1].substring(0,commandValue[1].length()-1));
        switch (direction){
            case "no":
                this.moveNorth(amount);
                break;
            case "so":
                this.moveSouth(amount);
                break;
            case "ea":
                this.moveEast(amount);
                break;
            case "we":
                this.moveWest(amount);
                break;
        }
        return "(" + this.turobotx +"," + this.turoboty + ")";
    }

    private void moveNorth(int amount){
        for(int i = amount; i > 0; i--){
            if(!this.grid[this.turobotx][this.turoboty].isBorderNorth() && this.turoboty + 1 < this.outerBordery) {
                this.turoboty++;
            }else return;
        }
    }
    private void moveSouth(int amount){
        for(int i = amount; i > 0; i--){
            if(!this.grid[this.turobotx][this.turoboty].isBorderSouth() && this.turoboty - 1 >=0) {
                this.turoboty--;
            } else return;
        }
    }
    private void moveEast(int amount){
        for(int i = amount; i > 0; i--){
            if(!this.grid[this.turobotx][this.turoboty].isBorderEast() && this.turobotx + 1 < this.outerBorderx) {
                this.turobotx++;
            }else return;
        }
    }
    private void moveWest(int amount){
        for(int i = amount; i > 0; i--){
            if(!this.grid[this.turobotx][this.turoboty].isBorderWest() && this.turobotx - 1 >= 0) {
                this.turobotx--;
            }else return;
        }
    }
}
