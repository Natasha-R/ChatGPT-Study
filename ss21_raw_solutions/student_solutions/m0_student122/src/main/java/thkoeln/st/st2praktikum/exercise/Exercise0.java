package thkoeln.st.st2praktikum.exercise;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Exercise0 implements Walkable {

    private String loc = "(1,6)";
    String[] horizontalObstacles = {
            "(0,6)-(2,6)",  // horizontal
            "(0,0)-(11,0)", //horizontal
            "(0,8)-(11,8)",  //horizontal
            "(1,5)-(9,5)"  // horizontal
    };
    String[] verticalObstacles = {
            "(3,6)-(3,8)",
            "(9,1)-(9,5)",
            "(0,0)-(0,8)",
            "(11,0)-(11,8)"
    };


    @Override
    public String walkTo(String walkCommandString) {
        System.out.println("COMMAND: "+walkCommandString);
        String[] commandArr = walkCommandString.replace("[","").replace("]","").split(",");
        String direction = commandArr[0];
        List<String> vObs = getObstacles(verticalObstacles);
        List<String> hObs = getObstacles(horizontalObstacles);

        switch (commandArr[0]){
            case "so":// y--
                for(int i =0; i<Integer.parseInt(commandArr[1]);i++){
                    int[] locArr = getLocAsArray(loc);
                    int[] firstPoint = {locArr[0],locArr[1]};
                    int[] secondPoint = {locArr[0]+1,locArr[1]};
                    int unchanged = locArr[1];
                    String boxEdge = stringOfTwoPoints(unchanged,firstPoint[0],secondPoint[0]);
                    System.out.println("BOXEDGE: "+boxEdge);

                    System.out.println("HOBS"+hObs);
                    if(hObs.contains(boxEdge)){
                        System.out.println("CONTAINS");
                        break;
                    }else {
                        System.out.println("NOT CONTAINS");
                        System.out.println("LOC BEFORE: "+loc);
                        loc=changeLoc(loc, "so");
                        System.out.println("LOC AFTER: "+loc);
                    }
                }
                break;
            case "no":// y--
                for(int i =0; i<Integer.parseInt(commandArr[1]);i++){
                    int[] locArr = getLocAsArray(loc);
                    int[] firstPoint = {locArr[0],locArr[1]+1};
                    int[] secondPoint = {locArr[0]+1,locArr[1]+1};
                    int unchanged = locArr[1]+1;

                    String boxEdge = stringOfTwoPoints(unchanged,firstPoint[0],secondPoint[0]);
                    System.out.println("BOXEDGE: "+boxEdge);

                    System.out.println("HOBS"+hObs);
                    if(hObs.contains(boxEdge)){
                        System.out.println("CONTAINS");
                        break;
                    }else {
                        System.out.println("NOT CONTAINS");
                        System.out.println("LOC BEFORE: "+loc);
                        loc=changeLoc(loc, "no");
                        System.out.println("LOC AFTER: "+loc);
                    }
                }
                break;
            case "ea":
                System.out.println("EA START");
                for(int i = 0; i<Integer.parseInt(commandArr[1]);i++){
                    int[] locArr = getLocAsArray(loc);
                    int[] firstPoint = {locArr[0]+1,locArr[1]};
                    int[] secondPoint = {locArr[0]+1,locArr[1]+1};
                    int unchanged = locArr[0]+1;
                    String boxEdge = stringOfTwoPoints(unchanged,firstPoint[1],secondPoint[1]);
                    System.out.println("BOXEDGE: "+boxEdge);

                    System.out.println("VOBS"+vObs);
                    if(vObs.contains(boxEdge)){
                        System.out.println("CONTAINS");
                        break;
                    }
                    else {
                        System.out.println("NOT CONTAINS");
                        System.out.println("LOC BEFORE: "+loc);
                        loc=changeLoc(loc, "ea");
                        System.out.println("LOC AFTER: "+loc);
                    }

                }
                System.out.println("EA END");
                break;
            case "we":
                for(int i = 0; i<Integer.parseInt(commandArr[1]);i++){
                    int[] locArr = getLocAsArray(loc);

                    int[] firstPoint = {locArr[0],locArr[1]};
                    int[] secondPoint = {locArr[0],locArr[1]+1};
                    System.out.println("FIRST: "+ Arrays.toString(firstPoint) +" SECOND: "+ Arrays.toString(secondPoint));
                    int unchanged = locArr[0];
                    System.out.println("UNCHANGED: "+unchanged);
                    String boxEdge = stringOfTwoPoints(unchanged,firstPoint[1],secondPoint[1]);
                    System.out.println("BOX EDGE: "+boxEdge);

                    System.out.println("VOBS"+vObs);
                    if(vObs.contains(boxEdge)){
                        System.out.println("CONTAINS");
                        break;
                    }
                    else {
                        System.out.println("NOT CONTAINS");
                        System.out.println("LOC BEFORE: "+loc);
                        loc=changeLoc(loc, "we");
                        System.out.println("LOC AFTER: "+loc);
                    }

                }
                break;
        }

        System.out.println("NEW LOC: "+loc);
        System.out.println("END OF WALK--------------------------------------------------");
        return loc;
    }

    private String changeLoc(String currentLoc,String direction){
        int[] currArr = getLocAsArray(currentLoc);
        switch (direction){
            case "so":
                currArr[1]--;
                break;
            case "no":
                currArr[1]++;
                break;
            case "we":
                currArr[0]--;

                break;
            case "ea":
                currArr[0]++;
                break;
        }

        return getLocAsString(currArr);

    }

    private String stringOfTwoPoints(int unchange, int p1, int p2){
        return unchange+"-("+p1+","+p2+")";
    }

    private List<String> getObstacles(String[] obsStr){
        List<String> listOfEdges = new ArrayList<>();
        for(String obs:obsStr){
            HashMap<String,HashMap<String,String>> a = getPoints(obs);
            String direction = (String) a.keySet().toArray()[0];
            String axis = (String) a.get(direction).keySet().toArray()[0];
            String[] arr = getEdges(a.get(direction).get(axis));
            for(String ar: arr){
                String obsWithAxis = (axis+"-"+ar);
                listOfEdges.add(obsWithAxis);
            }
        }

        return listOfEdges;
    }

    private String[] getEdges(String str){
        String[] edgStr = str.split(",");
        String[] edges = new String[edgStr.length-1];

        for(int i = 0; i< edges.length;i++){
            edges[i] = ("(" + edgStr[i] + ","+edgStr[i+1]+")");
        }

        return edges;
    }

    private HashMap<String,HashMap<String,String>> getPoints(String str){
        String direction;
        String nonChangingAxis;
        StringBuilder dots = new StringBuilder();
        String[] points = str.split("-");
        String[] leftPoint = points[0].replace("(","").replace(")","").split(",");
        String[] rightPoint = points[1].replace("(","").replace(")","").split(",");

        if(!leftPoint[0].equals(rightPoint[0])){
            direction = "horizontal";
            nonChangingAxis = leftPoint[1];
            for(int i = Integer.parseInt(leftPoint[0]); i<=Integer.parseInt(rightPoint[0]);i++){
                dots.append(i);
                dots.append(",");
            }
        }else{
            direction = "vertical";
            nonChangingAxis = leftPoint[0];
            for(int i = Integer.parseInt(leftPoint[1]); i<=Integer.parseInt(rightPoint[1]);i++){
                dots.append(i);
                dots.append(",");
            }
        }
        dots.deleteCharAt(dots.length()-1);
        HashMap<String,String> axisAndDots = new HashMap<>();
        HashMap<String,HashMap<String,String>> mainHash = new HashMap<>();
        axisAndDots.put(nonChangingAxis,dots.toString());
        mainHash.put(direction,axisAndDots);

        return mainHash;
    }

    private int[] getLocAsArray(String locString){
        String[] split = locString.replace("(", "").replace(")", "").split(",");

        return new int[]{
                Integer.parseInt(split[0]),
                Integer.parseInt(split[1])
        };
    }

    private String getLocAsString(int[] arr){
        return "("+(arr[0])+","+(arr[1])+")";
    }

}
