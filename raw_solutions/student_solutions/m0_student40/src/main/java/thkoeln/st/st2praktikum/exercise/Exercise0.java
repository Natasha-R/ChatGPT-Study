package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {

    private class Cell{
        Cell(int x, int y, int walltype){
            x_ = x;
            y_ = y;
            wt = walltype;
        }

        int x_;
        int y_;
        int wt;
    }

    private int x = 11;
    private int y = 7;
    private int max_x = 11;
    private int max_y = 7;
    private int min_x = 0;
    private int min_y = 0;

    // 0 = wall North
    // 1 = wall West
    // 2 = wall East
    // 3 = wall South
    private Cell[] walls = {
            new Cell(11, 6, 3),
            new Cell(10, 6, 3),
            new Cell( 9, 6, 3),
            new Cell( 8, 6, 3),
            new Cell( 7, 6, 3),
            new Cell( 6, 6, 3),
            new Cell( 5, 6, 3),

            new Cell(11, 5, 0),
            new Cell(10, 5, 0),
            new Cell( 9, 5, 0),
            new Cell( 8, 5, 0),
            new Cell( 7, 5, 0),
            new Cell( 6, 5, 0),
            new Cell( 5, 5, 0),

            new Cell(4, 5, 2),
            new Cell(5, 5, 1),
            new Cell(5, 5, 3),
            new Cell(5, 4, 0),

            new Cell(5, 4, 2),
            new Cell(5, 3, 2),
            new Cell(5, 2, 2),

            new Cell(6, 4, 1),
            new Cell(6, 3, 1),
            new Cell(6, 2, 1)

    };

    @Override
    public String goTo(String goCommandString) {
        // 0 = move North
        // 1 = move West
        // 2 = move East
        // 3 = move South
        int move = 99;
        int move_amount = 0;
        int temp_max_x = max_x;
        int temp_min_x = min_x;
        int temp_max_y = max_y;
        int temp_min_y = min_y;

        goCommandString = goCommandString.replace("[", "");
        goCommandString = goCommandString.replace("]", "");

        String[] exploded = goCommandString.split(",");

        move_amount = Integer.parseInt(exploded[1]);
        // the direction is either no (north), ea (east), so (south), or we (west).
        switch(exploded[0]){
            case "no":{
                move = 0;
                break;
            }
            case "ea":{
                move = 2;
                break;
            }
            case "so":{
                move = 3;
                break;
            }
            case "we":{
                move = 1;
                break;
            }
        }


        for (int i = 0; i < walls.length; i++) {
            Cell wall = walls[i];

            if( wall.wt != move ) // skip all walls that do not matter on the current move
                continue;

            switch(move){
                case 0:{
                    if( x == wall.x_ ){
                        if( y <= wall.y_ ) {
                            if (wall.y_ < temp_max_y)
                                temp_max_y = wall.y_;
                        }
                    }
                    break;
                }
                case 1:{
                    if( y == wall.y_ ) {
                        if (x >= wall.x_) {
                            if (wall.x_ > temp_min_x)
                                temp_min_x = wall.x_;
                        }
                    }
                    break;
                }
                case 2:{
                    if( y == wall.y_ ) {
                        if (x <= wall.x_) {
                            if (wall.x_ < temp_max_x)
                                temp_max_x = wall.x_;
                        }
                    }
                    break;
                }
                case 3:{
                    if( x == wall.x_ ){
                        if( y >= wall.y_ ) {
                            if (wall.y_ > temp_min_y) {
                                temp_min_y = wall.y_;
                            }
                        }
                    }
                    break;
                }
            }
        }

        switch(move){
            case 0:{
                y += move_amount;
                break;
            }
            case 1:{
                x -= move_amount;
                break;
            }
            case 2:{
                x += move_amount;
                break;
            }
            case 3:{
                y -= move_amount;
                break;
            }
        }

        x = clamp(temp_min_x, temp_max_x, x);
        y = clamp(temp_min_y, temp_max_y, y);
        return "(" + x + "," + y + ")";
    }
    private int clamp(int min, int max, int val){
        if( val > max )
            return max;

        if( val < min )
            return min;

        return val;
    }
}
