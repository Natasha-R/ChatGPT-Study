package thkoeln.st.st2praktikum.exercise;
import java.util.ArrayList;
public class Exercise0 implements GoAble {

    private static final String INITIAL = "(4,0)";
    private static final String[] BORDERS = {"(7,1)-(7,6)","(5,6)-(7,6)","(1,6)-(3,6)","(1,0)-(1,6)"}; //, "(0,0)-(0,8)","(0,8)-(11,8)","(11,0)-(11,8)","(0,0)-(11,0)"

    private boolean first = true;
    private String varNewPostion;
    ArrayList<String> orderedBorders = new ArrayList<String>();

    @Override
    public String go(String goCommandString) {

        if(this.first) {
            this.varNewPostion = INITIAL;
            this.first = false;

            for (String border: BORDERS) {
                //(0,1)
                String[] border_item = border.split("-");
                String border_item_start_raw = border_item[0].substring(1, border_item[0].length() - 1);
                String[] border_item_start = border_item_start_raw.split(",");
                String border_item_end_raw = border_item[1].substring(1, border_item[1].length() - 1);
                String[] border_item_end = border_item_end_raw.split(",");
                if(Integer.parseInt(border_item_start[0]) == Integer.parseInt(border_item_end[0])){
                    for (int i = Integer.parseInt(border_item_start[1]); i < Integer.parseInt(border_item_end[1])+1; i++) {
                        String border_item_item = "("+border_item_start[0]+","+i+")";
                        this.orderedBorders.add(border_item_item);
                    }
                }
                if(Integer.parseInt(border_item_start[1]) == Integer.parseInt(border_item_end[1])){
                    for (int i = Integer.parseInt(border_item_start[0]); i < Integer.parseInt(border_item_end[0])+1; i++) {
                        String border_item_item = "("+i+","+border_item_start[1]+")";
                        this.orderedBorders.add(border_item_item);
                    }
                }
                this.orderedBorders.add("|-|");
            }
//            System.out.println(this.orderedBorders);
        }

        //Convert new_postion String into Array [0]-> x direction
        //                                      [1]-> y direction
        String new_postion_raw = this.varNewPostion.substring(1, this.varNewPostion.length() - 1);
        String[] new_position = new_postion_raw.split(",");

        this.varNewPostion = "("+new_position[0]+","+new_position[1]+")";

        //Convert String into Array [0]-> movement direction
        //                          [1]-> movement power
        String movement_raw = goCommandString.substring(1, goCommandString.length() - 1);
        String[] movement = movement_raw.split(",");
        int power = Integer.parseInt(movement[1]);

        this.varNewPostion = this.move(movement[0], power, new_position);

        return this.varNewPostion;
    }

    /**
     *
     * @param direction no, ea, so, we
     * @param power anzahl der felder die sich bewegt werden soll
     * @param position aktuelle position
     * @return neue position
     */
    private String move(String direction, int power, String[] position) {

        for(int i=0;i<power;i++){
            System.out.println("----------------");
            System.out.print("Akutelle Position: ");
            System.out.println("("+position[0]+","+position[1]+")");
            System.out.print("direction: ");
            System.out.println(direction);
            System.out.print("power: ");
            System.out.println(power);
            //Check Border in Direction
            // -> Border yes no
            if(borderCheck(direction, position)) {
                return "("+position[0]+","+position[1]+")";
            }else{
                switch (direction) {
                    case "no":
                        position[1] = Integer.parseInt(position[1])+1+"";
                        break;
                    case "ea":
                        position[0] = Integer.parseInt(position[0])+1+"";
                        break;
                    case "so":
                        position[1] = Integer.parseInt(position[1])-1+"";
                        break;
                    case "we":
                        position[0] = Integer.parseInt(position[0])-1+"";
                        break;
                }
            }
        }
        return "("+position[0]+","+position[1]+")";
    }

    /**
     *
     * @param direction no, ea, so, we
     * @param position aktuelle position bevor man movt
     * @return boolean ob border im weg ist oder nicht
     */
    private boolean borderCheck(String direction, String[] position) {

        boolean result = false;
        String maybeBorderStart;
        String maybeBorderEnd;

        switch (direction) {
            case "no":
                if(Integer.parseInt(position[1])+1 == 8){
                    result = true;
                }
                break;
            case "ea":
                if(Integer.parseInt(position[0])+1 == 11){
                    result = true;
                }
                break;
            case "so":
                if(Integer.parseInt(position[1])-1 == -1){
                    result = true;
                }
                break;
            case "we":
                if(Integer.parseInt(position[0])-1 == -1){
                    result = true;
                }
                break;
        }

        if(result){
            return result;
        }


        switch (direction) {
            case "no":
                maybeBorderStart = "("+position[0]+","+(Integer.parseInt(position[1])+1)+")";
                maybeBorderEnd = "("+(Integer.parseInt(position[0])+1)+","+(Integer.parseInt(position[1])+1)+")";
                System.out.println(maybeBorderStart);
                System.out.println(maybeBorderEnd);
                result = borderSearch(maybeBorderStart, maybeBorderEnd);
                break;
            case "ea":
                maybeBorderStart = "("+(Integer.parseInt(position[0])+1)+","+Integer.parseInt(position[1])+")";
                maybeBorderEnd = "("+(Integer.parseInt(position[0])+1)+","+(Integer.parseInt(position[1])+1)+")";
                result = borderSearch(maybeBorderStart, maybeBorderEnd);
                System.out.println(maybeBorderStart);
                System.out.println(maybeBorderEnd);
                break;
            case "so":
                maybeBorderStart = "("+position[0]+","+Integer.parseInt(position[1])+")";
                maybeBorderEnd = "("+(Integer.parseInt(position[0])+1)+","+Integer.parseInt(position[1])+")";
                result = borderSearch(maybeBorderStart, maybeBorderEnd);
                System.out.println(maybeBorderStart);
                System.out.println(maybeBorderEnd);
                break;
            case "we":
                maybeBorderStart = "("+position[0]+","+Integer.parseInt(position[1])+")";
                maybeBorderEnd = "("+Integer.parseInt(position[0])+","+(Integer.parseInt(position[1])+1)+")";
                result = borderSearch(maybeBorderStart, maybeBorderEnd);
                System.out.println(maybeBorderStart);
                System.out.println(maybeBorderEnd);
                break;
        }
        return result;
    }

    /**
     *
     * @param start Start-Koordinate der Maybe-Border
     * @param ende End-Koordinate der Maybe-Border
     * @return  true oder false , ob border oder nicht
     */
    private boolean borderSearch(String start, String ende){

        if(this.orderedBorders.contains(start)){
            int startIndex = this.orderedBorders.indexOf(start);
            System.out.println(this.orderedBorders.get(startIndex));
            System.out.println(this.orderedBorders.get(startIndex+1));
            if(!this.orderedBorders.get(startIndex + 1).equals("|-|")){
                System.out.println(this.orderedBorders.get(startIndex+1).equals(ende));
                return this.orderedBorders.get(startIndex+1).equals(ende);
            }
        }
        return false;
    }

}
