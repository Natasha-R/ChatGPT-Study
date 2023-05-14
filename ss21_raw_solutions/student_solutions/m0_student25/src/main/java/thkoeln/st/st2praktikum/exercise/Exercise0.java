package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.util.Pair;


import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Iterator;



public class Exercise0 implements GoAble {

    private final int wide = 12;
    private final int lenght = 8;
    private boolean first = true;
    private int currX = 11;
    private int currY = 7;
    private ArrayList<Pair<Pair<Integer,Integer>,Pair<Integer,Integer>>> borders = new ArrayList<Pair<Pair<Integer,Integer>,Pair<Integer,Integer>>>();
    private ArrayList<Pair<Pair<Integer,Integer>,Pair<Integer,Integer>>> allBorders = new ArrayList<Pair<Pair<Integer,Integer>,Pair<Integer,Integer>>>();







    @Override
    public String goTo(String goCommandString) {
        if(first){
            this.first = false;
            borders.add(Pair.of(Pair.of(6,2),Pair.of(6,5)));
            borders.add(Pair.of(Pair.of(5,5),Pair.of(6,5)));
            borders.add(Pair.of(Pair.of(5,5),Pair.of(5,6)));
            borders.add(Pair.of(Pair.of(5,6),Pair.of(12,6)));

            for(Pair<Pair<Integer,Integer>,Pair<Integer,Integer>> border: borders){
                Pair<Integer,Integer> tmp1 = border.getFirst();
                Pair<Integer,Integer> tmp2 = border.getSecond();
                if(tmp1.getFirst() == tmp2.getFirst()){
                    for(int i = tmp1.getSecond(); i < tmp2.getSecond(); i++) {
                        allBorders.add(Pair.of(Pair.of(tmp1.getFirst(),i),Pair.of(tmp2.getFirst(),i+1)));
                    }
                }
                if(tmp1.getSecond() == tmp2.getSecond()){
                    for(int i = tmp1.getFirst(); i < tmp2.getFirst(); i++){
                        allBorders.add(Pair.of(Pair.of(i,tmp1.getSecond()),Pair.of(i+1,tmp2.getSecond())));
                    }
                }

            }
            for (Pair<Pair<Integer,Integer>,Pair<Integer,Integer>> border : allBorders) {
            }
        }
        goCommandString = goCommandString.substring(1,goCommandString.length()-1);
        String[] parts = goCommandString.split(",");
        String direction = parts[0];
        String steps = parts[1];
        for(int i = 0; i < Integer.parseInt(steps); i++){
            int tmpX = this.currX;
            int tmpY = this.currY;
            Pair<Pair<Integer,Integer>,Pair<Integer,Integer>> tmpBorder;
            switch (direction){
                case "no":
                    if(tmpY+1 == lenght) break;
                    tmpBorder = Pair.of(Pair.of(currX,currY+1),Pair.of(currX+1,currY+1));
                    if(!allBorders.contains(tmpBorder)){
                        currY++;
                    } break;
                case "ea":
                    if(tmpX+1 == wide) break;
                    tmpBorder = Pair.of(Pair.of(currX+1,currY),Pair.of(currX+1,currY+1));
                    if(!allBorders.contains(tmpBorder)){
                        currX++;
                    } break;
                case "so":
                    if(tmpY-1 == -1) break;
                    tmpBorder = Pair.of(Pair.of(currX,currY),Pair.of(currX+1,currY));
                    if(!allBorders.contains(tmpBorder)){
                        currY--;
                    } break;
                case "we":
                    if(tmpX-1 == -1) break;
                    tmpBorder = Pair.of(Pair.of(currX,currY),Pair.of(currX,currY+1));
                    if(!allBorders.contains(tmpBorder)){
                        currX--;
                    } break;
            }
        }
        return "("+currX+","+currY+")";
    }
}
